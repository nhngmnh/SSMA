# Meal API Documentation

Base URL: `/api/meal`

## Table of Contents
1. [Create Meal](#1-create-meal)
2. [Update Meal](#2-update-meal)
3. [Delete Meal](#3-delete-meal)
4. [Get All Meals](#4-get-all-meals)
5. [Get Meal by ID](#5-get-meal-by-id)

---

## 1. Create Meal

Tạo kế hoạch bữa ăn mới.

**Endpoint:** `POST /api/meal`

**Headers:**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "Breakfast Plan",
  "timestamp": "2026-01-10 08:00:00",
  "foodId": 1
}
```

**Response Success (200):**
```json
{
  "resultCode": "00140",
  "resultMessage": {
    "en": "Meal created successfully",
    "vn": "Tạo bữa ăn thành công"
  },
  "newPlan": {
    "id": 1,
    "name": "Breakfast Plan",
    "timestamp": "2026-01-10 08:00:00",
    "status": "PENDING",
    "FoodId": 1,
    "UserId": 1,
    "createdAt": "2026-01-02T10:00:00",
    "updatedAt": null
  }
}
```

**Error Response (500):**
```json
{
  "resultCode": "1999",
  "resultMessage": {
    "en": "System error",
    "vn": "Lỗi hệ thống"
  }
}
```

---

## 2. Update Meal

Cập nhật thông tin bữa ăn.

**Endpoint:** `PUT /api/meal`

**Headers:**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Request Body:**
```json
{
  "planId": 1,
  "name": "Updated Breakfast Plan",
  "timestamp": "2026-01-10 08:30:00",
  "status": "COMPLETED",
  "foodId": 2
}
```

**Response Success (200):**
```json
{
  "resultCode": "00141",
  "resultMessage": {
    "en": "Meal updated successfully",
    "vn": "Cập nhật bữa ăn thành công"
  },
  "meal": {
    "id": 1,
    "name": "Updated Breakfast Plan",
    "timestamp": "2026-01-10 08:30:00",
    "status": "COMPLETED",
    "foodId": 2,
    "userId": 1,
    "updatedAt": "2026-01-02T11:00:00",
    "createdAt": "2026-01-02T10:00:00"
  }
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "Meal not found",
    "vn": "Không tìm thấy bữa ăn"
  }
}
```

---

## 3. Delete Meal

Xóa kế hoạch bữa ăn.

**Endpoint:** `DELETE /api/meal`

**Headers:**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Request Body:**
```json
{
  "mealId": 1
}
```

**Response Success (200):**
```json
{
  "resultCode": "00142",
  "resultMessage": {
    "en": "Meal deleted successfully",
    "vn": "Xóa bữa ăn thành công"
  }
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "Meal not found",
    "vn": "Không tìm thấy bữa ăn"
  }
}
```

---

## 4. Get All Meals

Lấy tất cả kế hoạch bữa ăn.

**Endpoint:** `GET /api/meal`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Response Success (200):**
```json
{
  "resultCode": "00143",
  "resultMessage": {
    "en": "Meals retrieved successfully",
    "vn": "Lấy danh sách bữa ăn thành công"
  },
  "meals": [
    {
      "id": 1,
      "name": "Breakfast Plan",
      "timestamp": "2026-01-10 08:00:00",
      "status": "PENDING",
      "foodId": 1,
      "userId": 1,
      "createdAt": "2026-01-02T10:00:00",
      "updatedAt": null
    },
    {
      "id": 2,
      "name": "Lunch Plan",
      "timestamp": "2026-01-10 12:00:00",
      "status": "COMPLETED",
      "foodId": 5,
      "userId": 1,
      "createdAt": "2026-01-02T10:15:00",
      "updatedAt": "2026-01-02T12:30:00"
    },
    {
      "id": 3,
      "name": "Dinner Plan",
      "timestamp": "2026-01-10 19:00:00",
      "status": "PENDING",
      "foodId": 8,
      "userId": 1,
      "createdAt": "2026-01-02T10:30:00",
      "updatedAt": null
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

## 5. Get Meal by ID

Lấy chi tiết kế hoạch bữa ăn theo ID.

**Endpoint:** `GET /api/meal/{id}`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Path Parameters:**
- `id`: ID của meal

**Request:**
```
GET /api/meal/1
```

**Response Success (200):**
```json
{
  "resultCode": "00144",
  "resultMessage": {
    "en": "Meal retrieved successfully",
    "vn": "Lấy thông tin bữa ăn thành công"
  },
  "meal": {
    "id": 1,
    "name": "Breakfast Plan",
    "timestamp": "2026-01-10 08:00:00",
    "status": "PENDING",
    "foodId": 1,
    "userId": 1,
    "createdAt": "2026-01-02T10:00:00",
    "updatedAt": null
  }
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "Meal not found",
    "vn": "Không tìm thấy bữa ăn"
  }
}
```

---

## Error Codes Summary

| Code | Description (EN) | Description (VN) |
|------|------------------|------------------|
| 00140 | Meal created | Tạo bữa ăn thành công |
| 00141 | Meal updated | Cập nhật bữa ăn thành công |
| 00142 | Meal deleted | Xóa bữa ăn thành công |
| 00143 | Meals retrieved | Lấy danh sách bữa ăn thành công |
| 00144 | Meal retrieved | Lấy thông tin bữa ăn thành công |
| 1404 | Meal not found | Không tìm thấy bữa ăn |
| 1999 | System error | Lỗi hệ thống |

---

## Notes

1. **Authentication**: Tất cả endpoints yêu cầu `Authorization: Bearer {accessToken}`
2. **Status Values**: 
   - `PENDING`: Bữa ăn chưa hoàn thành
   - `COMPLETED`: Đã hoàn thành
3. **Food Reference**: Meal chỉ liên kết với 1 food item duy nhất qua `foodId`
4. **Timestamp Format**: "YYYY-MM-DD HH:mm:ss" (ví dụ: "2026-01-10 08:00:00")
5. **User Context**: Meals thuộc về user cụ thể (userId)
6. **planId vs mealId**: Update endpoint dùng `planId`, Delete endpoint dùng `mealId`
