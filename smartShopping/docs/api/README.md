# SmartShopping API Documentation

Tài liệu API cho ứng dụng SmartShopping Mobile Backend.

## 📋 Mục Lục

### User APIs
- [User API](User-API.md) - Quản lý user, authentication, profile, groups
  - Đăng ký, đăng nhập, logout
  - Quản lý profile (avatar, thông tin cá nhân)
  - Đổi mật khẩu, quên mật khẩu
  - Xác thực email
  - Quản lý FCM tokens
  - Quản lý groups (tạo, thêm, xóa thành viên)

### Food Management APIs
- [Food API](Food-API.md) - Quản lý thực phẩm
  - CRUD operations cho foods
  - Tìm kiếm thực phẩm
  - Lấy foods theo group
  - Quản lý units và categories
  
- [Fridge API](Fridge-API.md) - Quản lý tủ lạnh
  - Thêm/sửa/xóa items trong tủ lạnh
  - Theo dõi hạn sử dụng
  - Lấy danh sách items theo food name

- [Shopping List API](ShoppingList-API.md) - Quản lý danh sách mua sắm
  - Tạo và quản lý shopping lists
  - Thêm/sửa/xóa items
  - Đánh dấu hoàn thành
  - Theo dõi tiến độ

### Meal Planning APIs
- [Meal API](Meal-API.md) - Kế hoạch bữa ăn
  - Tạo meal plans theo ngày
  - Quản lý recipes trong meals
  - Lấy chi tiết meal với ingredients

- [Recipe API](Recipe-API.md) - Công thức nấu ăn
  - CRUD operations cho recipes
  - Tìm recipes theo food
  - Quản lý ingredients và instructions
  - Tags và difficulty levels

### Admin APIs
- [Category API](Category-API.md) - Quản lý categories (ADMIN only)
  - CRUD operations cho food categories
  
- [Unit API](Unit-API.md) - Quản lý đơn vị (ADMIN only)
  - CRUD operations cho measurement units
  
- [Log API](Log-API.md) - System logs (ADMIN only)
  - Xem logs của hệ thống
  - Monitoring và audit trail

### Utility APIs
- [Image API](Image-API.md) - Upload ảnh
  - Upload từ file
  - Upload từ URL
  
- [Test API](Test-API.md) - Testing endpoints (Development only)
  - Test FCM notifications
  - Firebase health check

---

## 🔑 Authentication

Hầu hết các endpoints yêu cầu authentication token trong header:

```
Authorization: Bearer {accessToken}
```

### Cách lấy access token:

1. **Đăng ký**: `POST /api/user` - Tạo tài khoản mới
2. **Xác thực email**: `POST /api/user/verify-email` - Nhập mã xác thực
3. **Đăng nhập**: `POST /api/user/login` - Lấy accessToken và refreshToken
4. **Làm mới token**: `POST /api/user/refresh-token` - Khi accessToken hết hạn

### Token Lifecycle:
- **Access Token**: Expires sau 1 giờ
- **Refresh Token**: Expires sau 7 ngày
- Khi access token hết hạn, dùng refresh token để lấy token mới

---

## 📱 Base URLs

### Development
```
http://localhost:8080
```

### Production
```
https://api.smartshopping.com
```

---

## 🌐 Response Format

Tất cả API responses đều theo format:

### Success Response
```json
{
  "resultCode": "00XXX",
  "resultMessage": {
    "en": "Success message in English",
    "vn": "Thông báo thành công bằng tiếng Việt"
  },
  "data": {
    // Response data here
  }
}
```

### Error Response
```json
{
  "resultCode": "4XXXX or 5XXXX",
  "resultMessage": {
    "en": "Error message in English",
    "vn": "Thông báo lỗi bằng tiếng Việt"
  },
  "error": "Detailed error information (optional)"
}
```

---

## 📊 Common Error Codes

| Code Range | Category | Examples |
|------------|----------|----------|
| 00XXX | Success | 00001-00999 |
| 40XXX | Client Errors | 40001 (Bad Request), 40401 (Not Found), 40901 (Conflict) |
| 50XXX | Server Errors | 50000 (Internal Error), 50001 (Service Unavailable) |
| 1999 | System Error | Generic system error |
| 99999 | Unknown Error | Unknown error |

### Specific Error Codes

| Code | Description (EN) | Description (VN) |
|------|------------------|------------------|
| 40001 | Missing required field | Thiếu trường bắt buộc |
| 40002 | Invalid email format | Email không hợp lệ |
| 40003 | Invalid password format | Mật khẩu không hợp lệ |
| 40004 | Email already exists | Email đã tồn tại |
| 40005 | Invalid credentials | Thông tin đăng nhập không đúng |
| 40006 | Email not verified | Email chưa được xác thực |
| 40007 | Invalid token | Token không hợp lệ |
| 40008 | Token expired | Token đã hết hạn |
| 40009 | Verification code invalid | Mã xác thực không đúng |
| 40010 | Verification code expired | Mã xác thực đã hết hạn |

---

## 🚀 Quick Start Guide

### 1. Đăng ký tài khoản mới

```bash
curl -X POST http://localhost:8080/api/user \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "Password123",
    "fullName": "John Doe"
  }'
```

### 2. Xác thực email

```bash
curl -X POST http://localhost:8080/api/user/verify-email \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "code": "123456"
  }'
```

### 3. Đăng nhập

```bash
curl -X POST http://localhost:8080/api/user/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "Password123",
    "deviceId": "device-123",
    "fcmToken": "fcm-token-here"
  }'
```

Response sẽ chứa `accessToken` và `refreshToken`.

### 4. Sử dụng API với token

```bash
curl -X GET http://localhost:8080/api/user/ \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

---

## 📦 Features by Module

### 👤 User Management
- ✅ Registration with email verification
- ✅ Login with multi-device support
- ✅ JWT-based authentication
- ✅ Password management (change, reset)
- ✅ Profile management with avatar upload
- ✅ Group creation and member management
- ✅ FCM token management for push notifications

### 🍎 Food Management
- ✅ Create custom foods with images
- ✅ Categorize foods
- ✅ Unit management
- ✅ Search functionality
- ✅ User and group-based food organization

### 🧊 Fridge Management
- ✅ Track food items in fridge
- ✅ Expiration date monitoring
- ✅ Automatic status calculation (FRESH, EXPIRING_SOON, EXPIRED)
- ✅ Search by food name

### 🛒 Shopping Lists
- ✅ Create multiple shopping lists
- ✅ Add/update/delete items
- ✅ Track completion status
- ✅ Progress percentage
- ✅ Date-based organization

### 🍽️ Meal Planning
- ✅ Plan meals by date and time
- ✅ Meal types (BREAKFAST, LUNCH, DINNER, SNACK)
- ✅ Link recipes to meals
- ✅ Calculate total prep and cook time

### 📖 Recipes
- ✅ Create custom recipes
- ✅ Ingredients with quantities
- ✅ Step-by-step instructions
- ✅ Difficulty levels
- ✅ Prep and cook time tracking
- ✅ Tags for easy search
- ✅ Find recipes by main ingredient

### 🔔 Notifications
- ✅ Firebase Cloud Messaging integration
- ✅ Multi-device notification support
- ✅ Test endpoints for FCM

### 📸 Media Management
- ✅ Image upload from file
- ✅ Image upload from URL
- ✅ Cloud storage integration

---

## 🔒 Security Features

1. **JWT Authentication**: Secure token-based authentication
2. **Password Hashing**: Bcrypt password encryption
3. **Email Verification**: Mandatory email verification before login
4. **Multi-device Support**: Track and manage multiple device logins
5. **Token Refresh**: Secure token refresh mechanism
6. **Admin Role**: Role-based access control for admin endpoints
7. **Audit Logging**: System logs for security monitoring

---

## 🛠️ Development Tools

### Testing FCM Notifications

1. Check Firebase health:
```bash
curl http://localhost:8080/test/fcm/health
```

2. Send test notification:
```bash
curl -X POST http://localhost:8080/test/fcm \
  -H "Content-Type: application/json" \
  -d '{
    "fcmToken": "YOUR_DEVICE_TOKEN",
    "title": "Test",
    "body": "Hello"
  }'
```

---

## 📞 Support

Nếu có vấn đề hoặc câu hỏi:
- Email: support@smartshopping.com
- GitHub Issues: [Create an issue](https://github.com/smartshopping/backend/issues)

---

## 📝 Version History

- **v1.0.0** (2026-01-02): Initial release
  - User management
  - Food management
  - Fridge tracking
  - Shopping lists
  - Meal planning
  - Recipes
  - FCM notifications

---

## 📄 License

Copyright © 2026 SmartShopping. All rights reserved.

---

## 🔗 API Documentation Links

- [User API Documentation](User-API.md)
- [Food API Documentation](Food-API.md)
- [Fridge API Documentation](Fridge-API.md)
- [Shopping List API Documentation](ShoppingList-API.md)
- [Meal API Documentation](Meal-API.md)
- [Recipe API Documentation](Recipe-API.md)
- [Category API Documentation](Category-API.md)
- [Unit API Documentation](Unit-API.md)
- [Image API Documentation](Image-API.md)
- [Log API Documentation](Log-API.md)
- [Test API Documentation](Test-API.md)
