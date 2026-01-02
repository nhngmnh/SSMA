# Firebase Cloud Messaging (FCM) Setup Guide

## 1. Tạo Firebase Project

1. Vào [Firebase Console](https://console.firebase.google.com/)
2. Click "Add project" hoặc chọn project hiện có
3. Nhập tên project: `smart-shopping-app`
4. Bật/Tắt Google Analytics (tùy chọn)
5. Click "Create project"

## 2. Tạo Firebase Service Account Key

### Bước 1: Vào Project Settings
1. Click vào icon ⚙️ (Settings) bên trái
2. Chọn "Project settings"

### Bước 2: Vào Service Accounts Tab
1. Click tab "Service accounts"
2. Chọn "Java" ở phần "Admin SDK configuration snippet"

### Bước 3: Generate Private Key
1. Click button "Generate new private key"
2. Confirm → Download file JSON
3. File JSON này sẽ có tên dạng: `smart-shopping-app-firebase-adminsdk-xxxxx.json`

### Bước 4: Đặt File vào Project
**Cách 1: Đặt trong resources folder (Recommended)**
```
src/main/resources/firebase-service-account.json
```

**Cách 2: Đặt ở ngoài project**
```
D:/firebase-credentials/firebase-service-account.json
```

Sau đó update application.properties:
```properties
firebase.config.path=D:/firebase-credentials/firebase-service-account.json
```

### ⚠️ QUAN TRỌNG: Bảo mật
- **KHÔNG** commit file JSON này lên Git
- Thêm vào `.gitignore`:
```gitignore
# Firebase credentials
firebase-service-account.json
**/firebase-adminsdk-*.json
```

## 3. Lấy Server Key (Legacy) - Nếu cần

1. Vào Firebase Console → Project Settings
2. Tab "Cloud Messaging"
3. Copy "Server key" (dùng cho HTTP v1 API)

## 4. Test FCM

### Gửi notification test từ Firebase Console:
1. Vào Firebase Console → Messaging
2. Click "New campaign" → "Firebase Notification messages"
3. Nhập title, body
4. Click "Send test message"
5. Nhập FCM token từ mobile app
6. Click "Test"

## 5. Mobile App Configuration

### Android (Flutter/React Native)
```json
// google-services.json
Download từ Firebase Console → Project Settings → Your apps → Android app
```

### iOS (Flutter/React Native)
```
// GoogleService-Info.plist
Download từ Firebase Console → Project Settings → Your apps → iOS app
```

## 6. Verify Setup

Sau khi setup xong, restart Spring Boot app:
```bash
mvn spring-boot:run
```

Check logs để xác nhận FCM đã khởi tạo:
```
INFO  c.e.s.configuration.FirebaseConfig - Firebase Cloud Messaging initialized successfully
```

## 7. Test API

### Gửi notification test:
```bash
POST http://localhost:8080/api/notification/send
Content-Type: application/json

{
  "fcmToken": "YOUR_DEVICE_FCM_TOKEN",
  "title": "Test Notification",
  "body": "This is a test message"
}
```

## Troubleshooting

### Lỗi: "Failed to initialize Firebase"
- Kiểm tra file `firebase-service-account.json` có đúng path không
- Kiểm tra format JSON có hợp lệ không
- Kiểm tra quyền đọc file

### Lỗi: "The caller does not have permission"
- Service account chưa có quyền gửi FCM
- Vào Firebase Console → IAM & Admin → Enable "Cloud Messaging API"

### Lỗi: "Registration token not registered"
- FCM token đã hết hạn hoặc không hợp lệ
- App cần refresh FCM token
