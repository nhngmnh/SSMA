# User API Documentation

Base URL: `/api/user`

## Table of Contents
1. [Register](#1-register)
2. [Login](#2-login)
3. [Logout](#3-logout)
4. [Refresh Token](#4-refresh-token)
5. [Get User Info](#5-get-user-info)
6. [Delete User](#6-delete-user)
7. [Edit User Profile](#7-edit-user-profile)
8. [Change Password](#8-change-password)
9. [Send Verification Code](#9-send-verification-code)
10. [Verify Email](#10-verify-email)
11. [Forgot Password](#11-forgot-password)
12. [Reset Password](#12-reset-password)
13. [Save Notification Token](#13-save-notification-token)
14. [Create Group](#14-create-group)
15. [Get Group Members](#15-get-group-members)
16. [Add Member to Group](#16-add-member-to-group)
17. [Remove Member from Group](#17-remove-member-from-group)

---

## 1. Register

Đăng ký tài khoản mới.

**Endpoint:** `POST /api/user`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123",
  "username": "john_doe",
  "name": "John Doe"
}
```

**Response Success (200):**
```json
{
  "resultCode": "00010",
  "resultMessage": {
    "en": "Register successfully",
    "vn": "Đăng ký thành công"
  }
}
```

**Error Responses:**
- **40011** - Email đã tồn tại

---

## 2. Login

Đăng nhập vào hệ thống.

**Endpoint:** `POST /api/user/login`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123",
  "deviceId": "device_unique_id",
  "fcmToken": "firebase_cloud_messaging_token"
}
```

**Response Success (200):**
```json
{
  "resultCode": "00047",
  "resultMessage": {
    "en": "You logged in successfully.",
    "vn": "Bạn đã đăng nhập thành công."
  },
  "user": {
    "userId": 1,
    "email": "user@example.com",
    "username": "john_doe",
    "name": "John Doe",
    "avatarUrl": "https://...",
    "isVerified": true,
    "isActive": true,
    "createdAt": "2026-01-01T00:00:00",
    "updatedAt": "2026-01-01T00:00:00"
  },
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Error Responses:**
- **40001** - Email không tồn tại
- **40002** - Mật khẩu không đúng
- **40003** - Email chưa được xác thực
- **40004** - Tài khoản bị khóa
- **1999** - Lỗi hệ thống

---

## 3. Logout

Đăng xuất khỏi thiết bị cụ thể.

**Endpoint:** `POST /api/user/logout`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Query Parameters:**
- `deviceId` (required): ID thiết bị cần đăng xuất

**Request:**
```
POST /api/user/logout?deviceId=device_unique_id
```

**Response Success (200):**
```json
{
  "resultCode": "00049",
  "resultMessage": {
    "en": "Logged out successfully",
    "vn": "Đăng xuất thành công"
  }
}
```

**Error Response (500):**
```json
{
  "resultCode": "1999",
  "resultMessage": {
    "en": "Logout failed",
    "vn": "Đăng xuất thất bại"
  }
}
```

---

## 4. Refresh Token

Làm mới access token và refresh token.

**Endpoint:** `POST /api/user/refresh-token`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "deviceId": "device_unique_id"
}
```

**Response Success (200):**
```json
{
  "resultCode": "00065",
  "resultMessage": {
    "en": "The token is refreshed successfully.",
    "vn": "Token đã được làm mới thành công."
  },
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Error Response (401):**
```json
{
  "resultCode": "40009",
  "resultMessage": {
    "en": "Invalid or expired refresh token",
    "vn": "Refresh token không hợp lệ hoặc đã hết hạn"
  },
  "accessToken": null,
  "refreshToken": null
}
```

---

## 5. Get User Info

Lấy thông tin người dùng hiện tại.

**Endpoint:** `GET /api/user/`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Response Success (200):**
```json
{
  "resultCode": "00089",
  "resultMessage": {
    "en": "The user information has gotten successfully.",
    "vn": "Thông tin người dùng đã được lấy thành công."
  },
  "user": {
    "id": 1,
    "email": "user@example.com",
    "password": "",
    "username": "john_doe",
    "name": "John Doe",
    "type": "user",
    "language": "en",
    "gender": "male",
    "countryCode": "US",
    "timezone": 7,
    "birthDate": "1990-01-01",
    "photoUrl": "https://...",
    "isActivated": true,
    "isVerified": true,
    "deviceId": "device_unique_id",
    "createdAt": "2026-01-01T00:00:00",
    "updatedAt": "2026-01-01T00:00:00"
  }
}
```

**Error Response (500):**
```json
{
  "resultCode": "99999",
  "resultMessage": {
    "en": "Failed",
    "vn": "Thất bại"
  }
}
```

---

## 6. Delete User

Xóa tài khoản người dùng hiện tại.

**Endpoint:** `DELETE /api/user/`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Response Success (200):**
```json
{
  "resultCode": "00092",
  "resultMessage": {
    "en": "Your account was deleted successfully.",
    "vn": "Tài khoản của bạn đã bị xóa thành công."
  }
}
```

**Error Response (500):**
```json
{
  "resultCode": "99999",
  "resultMessage": {
    "en": "Failed",
    "vn": "Thất bại"
  }
}
```

---

## 7. Edit User Profile

Cập nhật thông tin profile và avatar.

**Endpoint:** `PUT /api/user`

**Headers:**
```
Authorization: Bearer {accessToken}
Content-Type: multipart/form-data
```

**Form Data:**
- `username` (optional): Tên người dùng mới
- `image` (optional): File ảnh avatar

**Response Success (200):**
```json
{
  "resultCode": "00086",
  "resultMessage": {
    "en": "Your profile information was changed successfully.",
    "vn": "Thông tin hồ sơ của bạn đã được thay đổi thành công."
  },
  "photoUrl": "https://storage.example.com/avatars/user123.jpg"
}
```

**Error Response (400):**
```json
{
  "error": "Error message"
}
```

---

## 8. Change Password

Đổi mật khẩu người dùng.

**Endpoint:** `POST /api/user/change-password`

**Headers:**
```
Authorization: Bearer {accessToken}
Content-Type: application/x-www-form-urlencoded
```

**Form Data:**
- `oldPassword`: Mật khẩu cũ
- `newPassword`: Mật khẩu mới

**Response Success (200):**
```json
{
  "resultCode": "00076",
  "resultMessage": {
    "en": "Your password was changed successfully.",
    "vn": "Mật khẩu của bạn đã được thay đổi thành công."
  }
}
```

**Error Response (400):**
```json
{
  "resultCode": "00077",
  "resultMessage": {
    "en": "The old password is incorrect.",
    "vn": "Mật khẩu cũ không chính xác."
  }
}
```

---

## 9. Send Verification Code

Gửi mã xác thực đến email.

**Endpoint:** `POST /api/user/send-verification-code`

**Query Parameters:**
- `email`: Email cần gửi mã xác thực

**Request:**
```
POST /api/user/send-verification-code?email=user@example.com
```

**Response Success (200):**
```json
{
  "resultCode": "00048",
  "resultMessage": {
    "en": "The code is sent to your email successfully.",
    "vn": "Mã đã được gửi đến email của bạn thành công."
  },
  "confirmToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

## 10. Verify Email

Xác thực email với mã code.

**Endpoint:** `POST /api/user/verify-email`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "code": "123456",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Response Success (200):**
```json
{
  "resultCode": "00012",
  "resultMessage": {
    "en": "Email verified successfully. You can now login",
    "vn": "Xác thực email thành công. Bạn có thể đăng nhập ngay"
  },
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Error Responses:**
- **40013** - Mã xác thực không đúng
- **40014** - Mã xác thực đã hết hạn
- **40015** - Không tìm thấy người dùng
- **40016** - Xác thực thất bại (lỗi khác)
- **1999** - Lỗi hệ thống

---

## 11. Forgot Password

Yêu cầu đặt lại mật khẩu qua email.

**Endpoint:** `POST /api/user/forgot-password`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "email": "user@example.com"
}
```

**Response Success (200):**
```json
{
  "resultCode": "00050",
  "resultMessage": {
    "en": "Password reset code sent to your email",
    "vn": "Mã đặt lại mật khẩu đã được gửi đến email của bạn"
  }
}
```

**Error Responses:**
- **40005** - Email không tồn tại
- **40006** - Tài khoản chưa được xác thực
- **1999** - Lỗi hệ thống

---

## 12. Reset Password

Đặt lại mật khẩu với mã reset code.

**Endpoint:** `POST /api/user/reset-password`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "resetCode": "123456",
  "newPassword": "newpassword123"
}
```

**Response Success (200):**
```json
{
  "resultCode": "00051",
  "resultMessage": {
    "en": "Password reset successfully",
    "vn": "Đặt lại mật khẩu thành công"
  }
}
```

**Error Responses:**
- **40005** - Email không tồn tại
- **40007** - Mã đặt lại không đúng
- **40008** - Mã đặt lại đã hết hạn
- **1999** - Lỗi hệ thống

---

## 13. Save Notification Token

Lưu FCM token cho push notification.

**Endpoint:** `PUT /api/user/token`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Query Parameters:**
- `token`: FCM notification token

**Request:**
```
PUT /api/user/token?token=firebase_fcm_token_here
```

**Response Success (200):**
```json
{
  "resultCode": "00092",
  "resultMessage": {
    "en": "Your notification token was saved successfully.",
    "vn": "Token thông báo của bạn đã được lưu thành công."
  }
}
```

---

## 14. Create Group

Tạo nhóm mới với người dùng hiện tại là admin.

**Endpoint:** `POST /api/user/group`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Response Success (200):**
```json
{
  "resultCode": "00095",
  "resultMessage": {
    "en": "Your group has been created successfully",
    "vn": "Tạo nhóm thành công"
  },
  "adminId": 1
}
```

---

## 15. Get Group Members

Lấy danh sách thành viên trong nhóm.

**Endpoint:** `GET /api/user/group`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Response Success (200):**
```json
{
  "resultCode": "00098",
  "resultMessage": {
    "en": "Successfully",
    "vn": "Thành công"
  },
  "groupAdmin": 1,
  "members": [
    {
      "id": 1,
      "email": "user1@example.com",
      "username": "user1",
      "name": "User One",
      "type": "user",
      "language": "en",
      "gender": "male",
      "countryCode": "US",
      "timezone": 7,
      "birthDate": "1990-01-01",
      "photoUrl": "https://...",
      "isActivated": true,
      "isVerified": true,
      "deviceId": "device123",
      "createdAt": "2026-01-01T00:00:00",
      "updatedAt": "2026-01-01T00:00:00"
    }
  ]
}
```

**Error Response (500):**
```json
{
  "resultCode": "99999",
  "resultMessage": {
    "en": "Failed",
    "vn": "Thất bại"
  }
}
```

---

## 16. Add Member to Group

Thêm thành viên vào nhóm.

**Endpoint:** `POST /api/user/group/add`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Query Parameters:**
- `username`: Username của người cần thêm

**Request:**
```
POST /api/user/group/add?username=john_doe
```

**Response Success (200):**
```json
{
  "resultCode": "00102",
  "resultMessage": {
    "en": "User added to the group successfully",
    "vn": "Người dùng thêm vào nhóm thành công"
  }
}
```

**Error Response (500):**
```json
{
  "success": false,
  "message": "Error message",
  "code": 1999
}
```

---

## 17. Remove Member from Group

Xóa thành viên khỏi nhóm.

**Endpoint:** `DELETE /api/user/group`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Query Parameters:**
- `username`: Username của người cần xóa

**Request:**
```
DELETE /api/user/group?username=john_doe
```

**Response Success (200):**
```json
{
  "resultCode": "00106",
  "resultMessage": {
    "en": "User removed from the group successfully",
    "vn": "Xóa thành công"
  }
}
```

**Error Response (400):**
```json
{
  "resultCode": "00107",
  "resultMessage": {
    "en": "Error message",
    "vn": "Xóa người dùng thất bại"
  }
}
```

---

## Error Codes Summary

| Code | Description (EN) | Description (VN) |
|------|------------------|------------------|
| 00010 | Register successfully | Đăng ký thành công |
| 00012 | Email verified successfully | Xác thực email thành công |
| 00047 | Logged in successfully | Đăng nhập thành công |
| 00048 | Verification code sent | Mã xác thực đã gửi |
| 00049 | Logged out successfully | Đăng xuất thành công |
| 00050 | Password reset code sent | Mã đặt lại mật khẩu đã gửi |
| 00051 | Password reset successfully | Đặt lại mật khẩu thành công |
| 00065 | Token refreshed successfully | Làm mới token thành công |
| 00076 | Password changed successfully | Đổi mật khẩu thành công |
| 00077 | Old password incorrect | Mật khẩu cũ không đúng |
| 00086 | Profile updated successfully | Cập nhật profile thành công |
| 00089 | User info retrieved | Lấy thông tin user thành công |
| 00092 | Account deleted/Token saved | Xóa tài khoản/Lưu token thành công |
| 00095 | Group created successfully | Tạo nhóm thành công |
| 00098 | Get group members success | Lấy thành viên nhóm thành công |
| 00102 | Member added to group | Thêm thành viên thành công |
| 00106 | Member removed from group | Xóa thành viên thành công |
| 00107 | Failed to remove member | Xóa thành viên thất bại |
| 40001 | Email does not exist | Email không tồn tại |
| 40002 | Incorrect password | Mật khẩu không đúng |
| 40003 | Email not verified | Email chưa xác thực |
| 40004 | Account locked | Tài khoản bị khóa |
| 40005 | Email not found | Email không tồn tại |
| 40006 | Account not verified | Tài khoản chưa xác thực |
| 40007 | Invalid reset code | Mã đặt lại không đúng |
| 40008 | Reset code expired | Mã đặt lại hết hạn |
| 40009 | Invalid refresh token | Refresh token không hợp lệ |
| 40011 | Email already exists | Email đã tồn tại |
| 40013 | Invalid verification code | Mã xác thực không đúng |
| 40014 | Verification code expired | Mã xác thực hết hạn |
| 40015 | User not found | Không tìm thấy người dùng |
| 40016 | Verification failed | Xác thực thất bại |
| 1999 | System error | Lỗi hệ thống |
| 99999 | Failed | Thất bại |

---

## Notes

1. **Authentication**: Hầu hết các endpoints yêu cầu `Authorization: Bearer {accessToken}` trong header
2. **Token Management**: Hệ thống hỗ trợ multi-device với deviceId và FCM token
3. **Response Format**: Tất cả response đều có `resultCode` và `resultMessage` với 2 ngôn ngữ (en, vn)
4. **Error Handling**: Mỗi lỗi đều có code và message cụ thể để frontend xử lý
