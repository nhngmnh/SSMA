package com.example.smartShopping.service;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMService {

    /**
     * Gửi notification đến 1 device
     */
    public void sendNotification(String fcmToken, String title, String body, Map<String, String> data) {
        try {
            // Build notification
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            // Build message
            Message.Builder messageBuilder = Message.builder()
                    .setToken(fcmToken)
                    .setNotification(notification);

            // Thêm data nếu có
            if (data != null && !data.isEmpty()) {
                messageBuilder.putAllData(data);
            }

            Message message = messageBuilder.build();

            // Gửi message
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Successfully sent FCM message to token: {} | Response: {}", fcmToken, response);
            
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send FCM notification to token: {}", fcmToken, e);
            throw new RuntimeException("Failed to send push notification", e);
        }
    }

    /**
     * Gửi notification đến nhiều devices
     */
    public int sendMulticastNotification(List<String> fcmTokens, String title, String body, Map<String, String> data) {
        try {
            // Build notification
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            // Build multicast message
            MulticastMessage.Builder messageBuilder = MulticastMessage.builder()
                    .addAllTokens(fcmTokens)
                    .setNotification(notification);

            // Thêm data nếu có
            if (data != null && !data.isEmpty()) {
                messageBuilder.putAllData(data);
            }

            MulticastMessage message = messageBuilder.build();

            // Gửi message
            BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
            log.info("Successfully sent FCM messages. Success: {}, Failure: {}", 
                    response.getSuccessCount(), response.getFailureCount());
            
            // Log các token bị fail
            if (response.getFailureCount() > 0) {
                List<SendResponse> responses = response.getResponses();
                for (int i = 0; i < responses.size(); i++) {
                    if (!responses.get(i).isSuccessful()) {
                        log.error("Failed to send to token {}: {}", 
                                fcmTokens.get(i), 
                                responses.get(i).getException().getMessage());
                    }
                }
            }
            
            return response.getSuccessCount();
            
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send multicast FCM notification", e);
            throw new RuntimeException("Failed to send push notifications", e);
        }
    }

    /**
     * Gửi data-only message (không hiển thị notification)
     */
    public void sendDataMessage(String fcmToken, Map<String, String> data) {
        try {
            Message message = Message.builder()
                    .setToken(fcmToken)
                    .putAllData(data)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Successfully sent FCM data message to token: {} | Response: {}", fcmToken, response);
            
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send FCM data message to token: {}", fcmToken, e);
            throw new RuntimeException("Failed to send data message", e);
        }
    }

    /**
     * Gửi notification với custom Android/iOS config
     */
    public void sendAdvancedNotification(String fcmToken, String title, String body, 
                                         Map<String, String> data, 
                                         String androidChannelId,
                                         String sound) {
        try {
            // Android config
            AndroidConfig androidConfig = AndroidConfig.builder()
                    .setNotification(AndroidNotification.builder()
                            .setChannelId(androidChannelId != null ? androidChannelId : "default")
                            .setSound(sound != null ? sound : "default")
                            .build())
                    .build();

            // iOS config
            ApnsConfig apnsConfig = ApnsConfig.builder()
                    .setAps(Aps.builder()
                            .setSound(sound != null ? sound : "default")
                            .build())
                    .build();

            // Build message
            Message.Builder messageBuilder = Message.builder()
                    .setToken(fcmToken)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .setAndroidConfig(androidConfig)
                    .setApnsConfig(apnsConfig);

            if (data != null && !data.isEmpty()) {
                messageBuilder.putAllData(data);
            }

            Message message = messageBuilder.build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Successfully sent advanced FCM notification to token: {}", fcmToken);
            
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send advanced FCM notification to token: {}", fcmToken, e);
            throw new RuntimeException("Failed to send push notification", e);
        }
    }

    /**
     * Subscribe token vào topic
     */
    public void subscribeToTopic(List<String> fcmTokens, String topic) {
        try {
            TopicManagementResponse response = FirebaseMessaging.getInstance()
                    .subscribeToTopic(fcmTokens, topic);
            log.info("Successfully subscribed {} tokens to topic: {}", response.getSuccessCount(), topic);
        } catch (FirebaseMessagingException e) {
            log.error("Failed to subscribe tokens to topic: {}", topic, e);
            throw new RuntimeException("Failed to subscribe to topic", e);
        }
    }

    /**
     * Unsubscribe token khỏi topic
     */
    public void unsubscribeFromTopic(List<String> fcmTokens, String topic) {
        try {
            TopicManagementResponse response = FirebaseMessaging.getInstance()
                    .unsubscribeFromTopic(fcmTokens, topic);
            log.info("Successfully unsubscribed {} tokens from topic: {}", response.getSuccessCount(), topic);
        } catch (FirebaseMessagingException e) {
            log.error("Failed to unsubscribe tokens from topic: {}", topic, e);
            throw new RuntimeException("Failed to unsubscribe from topic", e);
        }
    }
}
