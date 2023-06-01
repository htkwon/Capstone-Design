package com.hansung.hansungcommunity.controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseException;
import com.hansung.hansungcommunity.ImageUtils;
import com.hansung.hansungcommunity.service.FileService;
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
    private final FileService fileService;
    @Value("${app.firebase-bucket}")
    private String bucketName;


    public FirebaseController(FireBaseService fireBaseService, FileService fileService) {
        this.fireBaseService = fireBaseService;
        this.fileService = fileService;
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
        String contentType = getType(bucketName, imageName);
        String extension = contentType.replace("image/", "");
        byte[] image = get(bucketName, imageName).getBody();
        byte[] compressImage = ImageUtils.compressAndResizeImage(image, 350, 170, 0.8f, extension);
        InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(compressImage));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(contentType)); // 이미지 타입에 맞게 설정
        return ResponseEntity.ok().headers(headers).body(inputStreamResource);
    }


    /**
     * 이미지 다운로드 url 제공
     */
    @GetMapping("/api/files/download/{imageName}")
    public ResponseEntity<byte[]> download(@PathVariable String imageName) throws IOException {
        String createdName = fileService.getCreatedName(imageName);
        return getFileDownload(bucketName, createdName, imageName);
    }

    /**
     * 이미지 삭제
     */
    @DeleteMapping("/api/files/delete/{imageName}")
    public ResponseEntity<Void> deleteFile(@PathVariable String imageName) {
        String createdName = fileService.getCreatedName(imageName);
        fileService.delete(createdName);
        return delete(bucketName, createdName);

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

    public String getType(String bucketName, String imageName) throws IOException {
        String serviceAccountKeyFile = "src/main/resources/serviceAccountKey.json";
        InputStream serviceAccount = new FileInputStream(serviceAccountKeyFile);
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

        // Storage 인스턴스를 생성 후 연결
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Blob blob = storage.get(bucketName, imageName);

        return blob.getContentType();
    }

    /**
     * 해당 다운로드 링크 제공
     */
    private ResponseEntity<byte[]> getFileDownload(String bucketName, String fileName, String imageName) throws IOException {
        String serviceAccountKeyFile = "src/main/resources/serviceAccountKey.json";

        InputStream serviceAccount = new FileInputStream(serviceAccountKeyFile);
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

        // Storage 인스턴스를 생성 후 연결
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Blob blob = storage.get(bucketName, fileName);
        byte[] fileContent = blob.getContent(); // 파일의 내용 가져오기

        HttpHeaders headers = new HttpHeaders();
        if (imageName.contains(".hwp")) {
            MediaType mediaType = MediaType.parseMediaType("application/x-hwp");
            headers.setContentType(mediaType);
        } else {
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        }

        headers.add("Content-Disposition", "attachment; filename=" + imageName); // 다운로드될 파일 이름 설정
        return ResponseEntity.ok().headers(headers).body(fileContent);
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
