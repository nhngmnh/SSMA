# Test API Documentation

Base URL: `/test`

> ⚠️ **Note**: Đây là endpoints dành cho testing và development. Không nên sử dụng trong production environment.

## Table of Contents
1. [Send Test FCM Notification](#1-send-test-fcm-notification)
2. [Send Multicast FCM Notification](#2-send-multicast-fcm-notification)
3. [Check Firebase Health](#3-check-firebase-health)

---

## 1. Send Test FCM Notification

Gửi FCM notification test đến một device.

**Endpoint:** `POST /test/fcm`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "fcmToken": "device-fcm-token-here",
  "title": "Test Notification",
  "body": "This is a test message"
}
```

**Response Success (200):**
```json
{
  "resultCode": "00000",
  "resultMessage": {
    "en": "FCM notification sent successfully",
    "vn": "Đã gửi thông báo FCM thành công"
  },
  "data": {
    "fcmToken": "device-fcm-token-here",
    "title": "Test Notification",
    "body": "This is a test message"
  }
}
```

**Error Response (400) - Missing Token:**
```json
{
  "resultCode": "40001",
  "resultMessage": {
    "en": "FCM token is required",
    "vn": "FCM token là bắt buộc"
  }
}
```

**Error Response (500) - Send Failed:**
```json
{
  "resultCode": "50000",
  "resultMessage": {
    "en": "Failed to send FCM notification: error details",
    "vn": "Không thể gửi thông báo FCM: error details"
  }
}
```

---

## 2. Send Multicast FCM Notification

Gửi FCM notification đến nhiều devices cùng lúc.

**Endpoint:** `POST /test/fcm/multicast`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "fcmTokens": [
    "device-fcm-token-1",
    "device-fcm-token-2",
    "device-fcm-token-3"
  ],
  "title": "Test Multicast",
  "body": "This is a multicast test notification"
}
```

**Response Success (200):**
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

**Error Response (400) - Missing Tokens:**
```json
{
  "resultCode": "40001",
  "resultMessage": {
    "en": "FCM tokens are required",
    "vn": "FCM tokens là bắt buộc"
  }
}
```

**Error Response (500) - Send Failed:**
```json
{
  "resultCode": "50000",
  "resultMessage": {
    "en": "Failed to send multicast FCM: error details",
    "vn": "Không thể gửi thông báo FCM: error details"
  }
}
```

---

## 3. Check Firebase Health

Kiểm tra trạng thái khởi tạo Firebase.

**Endpoint:** `GET /test/fcm/health`

**Headers:**
```
None required
```

**Response Success (200):**
```json
{
  "resultCode": "00000",
  "resultMessage": {
    "en": "Firebase is initialized",
    "vn": "Firebase đã được khởi tạo"
  },
  "data": {
    "firebaseAppName": "[DEFAULT]",
    "projectId": "smartshopping-12345"
  }
}
```

**Error Response (500) - Not Initialized:**
```json
{
  "resultCode": "50001",
  "resultMessage": {
    "en": "Firebase not initialized",
    "vn": "Firebase chưa được khởi tạo"
  },
  "error": "FirebaseApp with name [DEFAULT] doesn't exist."
}
```

---

## Error Codes Summary

| Code | Description (EN) | Description (VN) |
|------|------------------|------------------|
| 00000 | Success | Thành công |
| 40001 | FCM token(s) required | FCM token là bắt buộc |
| 50000 | Failed to send FCM | Không thể gửi thông báo FCM |
| 50001 | Firebase not initialized | Firebase chưa được khởi tạo |

---

## Notes

1. **No Authentication**: Các endpoints này không yêu cầu authentication (testing only)
2. **Development Only**: Chỉ sử dụng trong môi trường development/testing
3. **FCM Token**: Lấy FCM token từ mobile app (Flutter, React Native, etc.)
4. **Data Payload**: Notification tự động kèm theo data:
   - `type`: "test" hoặc "test_multicast"
   - `timestamp`: Unix timestamp
5. **Default Values**: Nếu không cung cấp title/body, sẽ dùng giá trị mặc định
6. **Multicast Limit**: Firebase FCM multicast hỗ trợ tối đa 500 tokens mỗi request
7. **Health Check**: Sử dụng để verify Firebase credentials và initialization
8. **Production**: Disable hoặc xóa các endpoints này trước khi deploy production

---

## Testing Examples

### Using cURL

**Test Single Device:**
```bash
curl -X POST http://localhost:8080/test/fcm \
  -H "Content-Type: application/json" \
  -d '{
    "fcmToken": "YOUR_DEVICE_TOKEN",
    "title": "Test",
    "body": "Hello from API"
  }'
```

**Test Multiple Devices:**
```bash
curl -X POST http://localhost:8080/test/fcm/multicast \
  -H "Content-Type: application/json" \
  -d '{
    "fcmTokens": ["TOKEN1", "TOKEN2"],
    "title": "Multicast Test",
    "body": "Hello everyone"
  }'
```

**Check Health:**
```bash
curl http://localhost:8080/test/fcm/health
```

### Using Postman

1. Import collection với các endpoints trên
2. Thay YOUR_DEVICE_TOKEN bằng FCM token thật từ mobile app
3. Send request và kiểm tra notification trên device
