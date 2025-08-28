package com.example.demo.application.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadVideoRequest {
    private MultipartFile videoFile;
    private String caption;
    private String accessToken;
}
