# Hướng Dẫn Test FCM Notification

## Bước 1: Chuẩn bị Firebase Service Account

1. **Download Firebase credentials:**
   - Vào [Firebase Console](https://console.firebase.google.com/)
   - Chọn project của bạn
   - Settings (⚙️) → Project settings → Service accounts
   - Click "Generate new private key"
   - Download file JSON

2. **Đặt file vào project:**
   - Đổi tên file thành `firebase-service-account.json`
   - Copy vào thư mục: `src/main/resources/`

## Bước 2: Start Backend

```bash
mvn spring-boot:run
```

Kiểm tra log khi app start, phải thấy:
```
Firebase initialized successfully with project: your-project-id
```

## Bước 3: Lấy FCM Token từ Mobile App

### Với Flutter:
```dart
import 'package:firebase_messaging/firebase_messaging.dart';

// Trong initState hoặc main()
final fcmToken = await FirebaseMessaging.instance.getToken();
print('FCM Token: $fcmToken');
```

### Với React Native:
```javascript
import messaging from '@react-native-firebase/messaging';

const fcmToken = await messaging().getToken();
console.log('FCM Token:', fcmToken);
```

### Với Android Native:
```kotlin
FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
    if (task.isSuccessful) {
        val token = task.result
        Log.d("FCM", "Token: $token")
    }
}
```

## Bước 4: Test Firebase Initialization

**Endpoint:** `GET /test/fcm/health`

```bash
curl http://localhost:8080/test/fcm/health
```

**Response thành công:**
```json
{
  "resultCode": "00000",
  "resultMessage": {
    "en": "Firebase is initialized",
    "vn": "Firebase đã được khởi tạo"
  },
  "data": {
    "firebaseAppName": "[DEFAULT]",
    "projectId": "your-project-id"
  }
}
```

## Bước 5: Test Gửi Notification đến 1 Device

**Endpoint:** `POST /test/fcm`

```bash
curl -X POST http://localhost:8080/test/fcm \
  -H "Content-Type: application/json" \
  -d '{
    "fcmToken": "YOUR_DEVICE_FCM_TOKEN_HERE",
    "title": "Test Notification",
    "body": "This is a test message from SmartShopping backend"
  }'
```

**Response thành công:**
```json
{
  "resultCode": "00000",
  "resultMessage": {
    "en": "FCM notification sent successfully",
    "vn": "Đã gửi thông báo FCM thành công"
  },
  "data": {
    "fcmToken": "YOUR_TOKEN",
    "title": "Test Notification",
    "body": "This is a test message from SmartShopping backend"
  }
}
```

## Bước 6: Test Gửi Notification đến Nhiều Device

**Endpoint:** `POST /test/fcm/multicast`

```bash
curl -X POST http://localhost:8080/test/fcm/multicast \
  -H "Content-Type: application/json" \
  -d '{
    "fcmTokens": [
      "DEVICE_1_TOKEN",
      "DEVICE_2_TOKEN",
      "DEVICE_3_TOKEN"
    ],
    "title": "Multicast Test",
    "body": "Message sent to multiple devices"
  }'
```

**Response:**
```json
{
  "resultCode": "00000",
  "resultMessage": {
    "en": "Multicast FCM notification sent",
    "vn": "Đã gửi thông báo FCM đến nhiều thiết bị"
  },
  "data": {
    "totalDevices": 3,
    "successCount": 2,
    "failureCount": 1
  }
}
```

## Bước 7: Test với Postman

1. **Import vào Postman:**
   - Method: `POST`
   - URL: `http://localhost:8080/test/fcm`
   - Headers: `Content-Type: application/json`
   - Body (raw JSON):
   ```json
   {
     "fcmToken": "paste-your-token-here",
     "title": "Hello from Postman",
     "body": "Testing FCM notification"
   }
   ```

2. **Click Send** và check notification trên mobile device

## Troubleshooting

### Lỗi: "Firebase not initialized"
- Kiểm tra file `firebase-service-account.json` đã đúng vị trí chưa
- Kiểm tra config trong `application.properties`:
  ```properties
  firebase.config.path=firebase-service-account.json
  ```
- Restart backend

### Lỗi: "Invalid token"
- Token FCM có thể expire hoặc không hợp lệ
- Lấy token mới từ mobile app
- Đảm bảo app mobile đã setup Firebase đúng cách

### Lỗi: "Requested entity was not found"
- Kiểm tra Project ID trong Firebase Console
- Đảm bảo mobile app dùng đúng google-services.json (Android) hoặc GoogleService-Info.plist (iOS)

### Không nhận được notification trên device
- Kiểm tra notification permissions trên device
- Kiểm tra app có đang chạy foreground/background
- Check device logs:
  - Android: `adb logcat | grep FCM`
  - iOS: Xcode Console

## Use Cases Thực Tế

### 1. Gửi thông báo khi thêm item vào shopping list
```java
fcmService.sendNotification(
    user.getFcmToken(),
    "Shopping List Updated",
    "A new item has been added to your shopping list",
    Map.of("type", "shopping_list_update", "listId", "123")
);
```

### 2. Gửi thông báo khi food sắp hết hạn
```java
fcmService.sendNotification(
    user.getFcmToken(),
    "Food Expiring Soon",
    "Milk will expire in 2 days",
    Map.of("type", "expiry_warning", "foodId", "456")
);
```

### 3. Gửi thông báo đến tất cả member trong group
```java
List<String> tokens = groupMembers.stream()
    .map(User::getFcmToken)
    .filter(Objects::nonNull)
    .collect(Collectors.toList());

fcmService.sendMulticastNotification(
    tokens,
    "Group Update",
    "New meal plan created for this week",
    Map.of("type", "group_update", "groupId", "789")
);
```

## Next Steps

Sau khi test thành công, tích hợp FCM vào các API thực tế:
1. **Login:** Save FCM token khi user login
2. **ShoppingList:** Gửi notification khi có thay đổi
3. **Fridge:** Cảnh báo food sắp hết hạn
4. **Meal Plan:** Nhắc nhở meal plans
5. **Group:** Thông báo group activities

## Tài Liệu Tham Khảo

- [Firebase Cloud Messaging Documentation](https://firebase.google.com/docs/cloud-messaging)
- [Firebase Admin SDK for Java](https://firebase.google.com/docs/admin/setup)
- [FCM HTTP v1 API](https://firebase.google.com/docs/cloud-messaging/migrate-v1)
