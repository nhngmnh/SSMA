package com.example.smartShopping.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
    String uploadImage(MultipartFile file, Long userId);
    String uploadImageFromUrl(String imageUrl, Long userId) throws Exception;
}
