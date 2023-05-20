package com.hansung.hansungcommunity.controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseException;
import com.hansung.hansungcommunity.ImageUtils;
import com.hansung.hansungcommunity.service.FireBaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
public class FirebaseController {

    private final FireBaseService fireBaseService;
    @Value("${app.firebase-bucket}")
    private String bucketName;

    public FirebaseController(FireBaseService fireBaseService) {
        this.fireBaseService = fireBaseService;
    }

    /**
     * 이미지 저장
     */
    @PostMapping("/api/files")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, String nameFile)
            throws IOException, FirebaseException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        fireBaseService.uploadFiles(file, nameFile);

        return ResponseEntity.status(HttpStatus.OK).body("/api/files/" + nameFile);
    }


    /**
     * Quill 이미지 조회
     */
    @GetMapping("/api/files/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        return get(bucketName, imageName);
    }

    /**
     * 이미지 resize(압축) 후 보내주기
     */
    @GetMapping("/api/files/{imageName}/resize")
    public ResponseEntity<InputStreamResource> resizeImage(@PathVariable String imageName) throws IOException {
        String formatName = imageName.substring(imageName.lastIndexOf(".") + 1);
        byte[] image = get(bucketName,imageName).getBody();
        byte[] compressImage = ImageUtils.compressAndResizeImage(image,800,600,0.8f,"jpg");
        InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(compressImage));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // 이미지 타입에 맞게 설정
        return ResponseEntity.ok().headers(headers).body(inputStreamResource);
    }


    /**
     * 이미지 다운로드 url 제공
     */
    @GetMapping("/api/files/download/{imageName}")
    public ResponseEntity<byte[]> download(@PathVariable String imageName) throws IOException {
        return getFileDownload(bucketName, imageName);
    }

    /**
     * 이미지 삭제
     */
    @DeleteMapping("api/files/delete/{imageName}")
    public ResponseEntity<Void> deleteFile(@PathVariable String imageName) {
        return delete(bucketName, imageName);
    }


    /**
     * firebase로 부터 이미지 받아오기
     */
    public ResponseEntity<byte[]> get(String bucketName, String imageName) throws IOException {
        String serviceAccountKeyFile = "src/main/resources/serviceAccountKey.json";
        // 서비스 계정 키 파일을 이용하여 인증 정보를 생성
        InputStream serviceAccount = new FileInputStream(serviceAccountKeyFile);
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

        // Storage 인스턴스를 생성 후 연결
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Blob blob = storage.get(bucketName, imageName);
        byte[] content = blob.getContent();
        String contentType = blob.getContentType();

        // 응답 헤더 설정(MIME)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        return ResponseEntity.ok().headers(headers).body(content);
    }

    /**
     * 해당 다운로드 링크 제공
     */
    public ResponseEntity<byte[]> getFileDownload(String bucketName, String fileName) throws IOException {
        String serviceAccountKeyFile = "src/main/resources/serviceAccountKey.json";

        InputStream serviceAccount = new FileInputStream(serviceAccountKeyFile);
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

        // Storage 인스턴스를 생성 후 연결
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Blob blob = storage.get(bucketName, fileName);
        String downloadUrl = blob.getMediaLink(); // 파일의 다운로드 URL 가져오기

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.add("Content-Disposition", "attachment; filename=" + fileName); // 다운로드될 파일 이름 설정
        return ResponseEntity.ok().headers(headers).body(downloadUrl.getBytes());
    }

    /**
     * 파이어베이스 이미지 삭제
     */
    public ResponseEntity<Void> delete(String bucketName, String imageName) {
        String serviceAccountKeyFile = "src/main/resources/serviceAccountKey.json";

        InputStream serviceAccount;
        try {
            serviceAccount = new FileInputStream(serviceAccountKeyFile);
        } catch (FileNotFoundException e) {
            // 파일 존재 x 예외
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        GoogleCredentials credentials;
        try {
            credentials = GoogleCredentials.fromStream(serviceAccount);
        } catch (IOException e) {
            // 인증 실패 예외
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        boolean deleted;
        try {
            deleted = storage.delete(bucketName, imageName);
        } catch (StorageException e) {
            // 파일 삭제 예외
            return ResponseEntity.badRequest().build();
        }
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
