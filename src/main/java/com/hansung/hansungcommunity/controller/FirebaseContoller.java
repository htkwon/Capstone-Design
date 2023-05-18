package com.hansung.hansungcommunity.controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseException;
import com.hansung.hansungcommunity.service.FireBaseService;
import io.github.classgraph.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class FirebaseContoller {

    private final FireBaseService fireBaseService;
    @Value("${app.firebase-bucket}")
    private String bucketName;

    public FirebaseContoller(FireBaseService fireBaseService) {
        this.fireBaseService = fireBaseService;
    }

    /**
     * 이미지 저장
     */
    @PostMapping("/api/files")
    public ResponseEntity<String> uploadFile (@RequestParam("file")MultipartFile file, String nameFile)
        throws IOException, FirebaseException{
        if(file.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        fireBaseService.uploadFiles(file,nameFile);

        return ResponseEntity.status(HttpStatus.OK).body("/api/files/"+nameFile);
    }


    /**
     * 이미지 조회
     */
    @GetMapping("/api/files/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        return get(bucketName,imageName);
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

}
