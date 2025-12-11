package com.example.smartShopping.service.impl;

import com.example.smartShopping.service.ImageStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;
import java.net.URL;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {

    @Override
    public String uploadImage(MultipartFile file, Long userId) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File ảnh không được để trống");
        }
// Lưu vào Minio docker thưa đại ca ạ !
        return "Uploaded file!";
    }

    @Override
    public String uploadImageFromUrl(String imageUrl, Long userId) throws Exception {
        if (imageUrl == null || imageUrl.isEmpty()) {
            throw new RuntimeException("URL ảnh không được để trống");
        }

        // Tải ảnh từ URL và tạo MultipartFile giả
        URL url = new URL(imageUrl);
        try (InputStream in = url.openStream()) {
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            MultipartFile file = new MockMultipartFile("file", fileName, "image/jpeg", in);
            return uploadImage(file, userId);
        }
    }
}
