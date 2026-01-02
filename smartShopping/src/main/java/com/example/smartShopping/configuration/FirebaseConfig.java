package com.example.smartShopping.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import jakarta.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.path:firebase-service-account.json}")
    private String firebaseConfigPath;

    @PostConstruct
    public void initialize() {
        try {
            // Kiểm tra xem FirebaseApp đã được khởi tạo chưa
            if (FirebaseApp.getApps().isEmpty()) {
                InputStream serviceAccount = null;
                
                try {
                    // Thử đọc từ classpath trước (resources folder)
                    serviceAccount = new ClassPathResource(firebaseConfigPath).getInputStream();
                    log.info("Loading Firebase config from classpath: {}", firebaseConfigPath);
                } catch (IOException e) {
                    // Nếu không có trong classpath, thử đọc từ file system
                    try {
                        serviceAccount = new FileInputStream(firebaseConfigPath);
                        log.info("Loading Firebase config from file system: {}", firebaseConfigPath);
                    } catch (IOException ex) {
                        log.warn("Firebase service account file not found. FCM will not work.");
                        log.warn("Please place firebase-service-account.json in src/main/resources/");
                        return;
                    }
                }

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
                log.info("Firebase Cloud Messaging initialized successfully");
                
                serviceAccount.close();
            } else {
                log.info("FirebaseApp already initialized");
            }
        } catch (Exception e) {
            log.error("Failed to initialize Firebase", e);
        }
    }
}
