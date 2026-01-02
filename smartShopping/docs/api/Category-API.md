# Category API Documentation

Base URL: `/api/admin/category`

**Note:** Tất cả endpoints yêu cầu quyền ADMIN và Authorization header.

## Table of Contents
1. [Create Category](#1-create-category)
2. [Get All Categories](#2-get-all-categories)
3. [Update Category](#3-update-category)
4. [Delete Category](#4-delete-category)

---

## 1. Create Category

Tạo category mới (chỉ ADMIN).

**Endpoint:** `POST /api/admin/category`

**Headers:**
```
Authorization: Bearer {accessToken}
Content-Type: application/x-www-form-urlencoded
```

**Query/Form Parameters:**
- `name` (required): Tên category

**Request:**
```
POST /api/admin/category?name=Vegetables
```

**Response Success (200):**
```json
{
  "resultCode": "00120",
  "resultMessage": {
    "en": "Category created successfully",
    "vn": "Tạo category thành công"
  },
  "category": {
    "id": 1,
    "name": "Vegetables",
    "createdAt": "2026-01-02T00:00:00"
  }
}
```

**Error Response (500):**
```json
{
  "resultCode": "1999",
  "resultMessage": {
    "en": "System error: error details",
    "vn": "Lỗi hệ thống: error details"
  }
}
```

---

## 2. Get All Categories

Lấy danh sách tất cả categories (chỉ ADMIN).

**Endpoint:** `GET /api/admin/category`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Response Success (200):**
```json
{
  "resultCode": "00121",
  "resultMessage": {
    "en": "Categories retrieved successfully",
    "vn": "Lấy danh sách categories thành công"
  },
  "categories": [
    {
      "id": 1,
      "name": "Vegetables",
      "createdAt": "2026-01-01T00:00:00"
    },
    {
      "id": 2,
      "name": "Fruits",
      "createdAt": "2026-01-01T00:00:00"
    }
  ]
}
```

**Error Response (500):**
```json
{
  "resultCode": "1999",
  "resultMessage": {
    "en": "System error: error details",
    "vn": "Lỗi hệ thống: error details"
  }
}
```

---

## 3. Update Category

Cập nhật tên category (chỉ ADMIN).

**Endpoint:** `PUT /api/admin/category`

**Headers:**
```
Authorization: Bearer {accessToken}
Content-Type: application/x-www-form-urlencoded
```

**Query/Form Parameters:**
- `oldName` (required): Tên cũ của category
- `newName` (required): Tên mới của category

**Request:**
```
PUT /api/admin/category?oldName=Vegetables&newName=Fresh Vegetables
```

**Response Success (200):**
```json
{
  "resultCode": "00122",
  "resultMessage": {
    "en": "Category updated successfully",
    "vn": "Cập nhật category thành công"
  },
  "category": {
    "id": 1,
    "name": "Fresh Vegetables",
    "updatedAt": "2026-01-02T00:00:00"
  }
}
```

**Error Response (500):**
```json
{
  "resultCode": "1999",
  "resultMessage": {
    "en": "System error: error details",
    "vn": "Lỗi hệ thống: error details"
  }
}
```

---

## 4. Delete Category

Xóa category (chỉ ADMIN).

**Endpoint:** `DELETE /api/admin/category`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Query Parameters:**
- `name` (required): Tên category cần xóa

**Request:**
```
DELETE /api/admin/category?name=Vegetables
```

**Response Success (200):**
```json
{
  "resultCode": "00123",
  "resultMessage": {
    "en": "Category deleted successfully",
    "vn": "Xóa category thành công"
  }
}
```

**Error Response (500):**
```json
{
  "resultCode": "1999",
  "resultMessage": {
    "en": "System error: error details",
    "vn": "Lỗi hệ thống: error details"
  }
}
```

---

## Error Codes Summary

| Code | Description (EN) | Description (VN) |
|------|------------------|------------------|
| 00120 | Category created | Tạo category thành công |
| 00121 | Categories retrieved | Lấy categories thành công |
| 00122 | Category updated | Cập nhật category thành công |
| 00123 | Category deleted | Xóa category thành công |
| 1999 | System error | Lỗi hệ thống |

---

## Notes

1. **Authorization**: Tất cả endpoints yêu cầu role ADMIN
2. **Authentication**: Phải có `Authorization: Bearer {accessToken}` trong header
3. **Access Control**: Chỉ ADMIN mới có thể thực hiện các thao tác này
