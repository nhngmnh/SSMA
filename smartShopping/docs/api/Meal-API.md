# Meal API Documentation

Base URL: `/api/meal`

## Business Logic

### Structure Overview:
```
Meal (Bữa ăn) - 1 meal chứa nhiều food với quantity
  ├── Food 1: Thịt heo (500g)
  ├── Food 2: Rau cải (200g)
  ├── Food 3: Hành (50g)
  ├── Food 4: Cá (300g)
  └── Food 5: Dầu ăn (2 muỗng)
```

**Note:** Meal plan là danh sách mua sắm (shopping list) chứa các food với số lượng, KHÔNG phải danh sách công thức nấu ăn (recipes).

### Data Storage:
- **Meal.foodItems**: JSON array chứa objects:
```json
[
  {"foodId": 10, "quantity": 500, "unitName": "g"},
  {"foodId": 20, "quantity": 200, "unitName": "g"},
  {"foodId": 30, "quantity": 50, "unitName": "g"}
]
```
- **Recipe.foodIds**: JSON array `[10, 20, 30]` - Danh sách food IDs (công thức nấu ăn)
- Meal plan dùng để mua sắm, Recipe dùng để nấu ăn

### Group Assignment Rules:
1. **Thành viên thường (Regular Users)**:
   - Khi tạo meal, hệ thống tự động gán `groupId` từ group mà user đang thuộc
   - Mỗi user chỉ được thuộc **1 group duy nhất**
   - **KHÔNG CẦN** truyền `groupId` trong request
   - Nếu user không thuộc group nào → lỗi

2. **Admin**:
   - Có thể tạo meal cho **bất kỳ group nào**
   - **CÓ THỂ** truyền `groupId` trong request để chọn group cụ thể
   - Nếu không truyền `groupId` → meal sẽ thuộc group của admin (nếu có)

### Permissions:
- **View meals**: Tất cả members trong group có thể xem
- **Modify/Delete meals**: Chỉ group owner hoặc admin

---

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

**Request Body (Thành viên thường):**
```json
{
  "name": "Breakfast Plan",
  "timestamp": "2026-01-10 08:00:00",
  "foodItems": [
    {"foodId": 1, "quantity": 500, "unitName": "g"},
    {"foodId": 5, "quantity": 200, "unitName": "g"},
    {"foodId": 12, "quantity": 3, "unitName": "quả"}
  ]
}
```
*Note: 
- `foodItems`: Danh sách foods với quantity (shopping list)
- `groupId` sẽ tự động được gán từ group của user*

**Request Body (Admin - Optional groupId):**
```json
{
  "name": "Breakfast Plan",
  "timestamp": "2026-01-10 08:00:00",
  "foodItems": [
    {"foodId": 1, "quantity": 500, "unitName": "g"},
    {"foodId": 5, "quantity": 200, "unitName": "g"},
    {"foodId": 12, "quantity": 3, "unitName": "quả"}
  ],
  "groupId": 5
}
```
*Note: Admin có thể chọn groupId để tạo meal cho group cụ thể*

**Response Success (200):**
```json
{
  "resultCode": "00322",
  "resultMessage": {
    "foodItems": [
      {"foodId": 1, "quantity": 500, "unitName": "g"},
      {"foodId": 5, "quantity": 200, "unitName": "g"},
      {"foodId": 12, "quantity": 3, "unitName": "quả"}
    uccessfull",
    "vn": "Thêm kế hoạch bữa ăn thành công"
  },
  "newPlan": {
    "id": 1,
    "name": "Breakfast Plan",
    "timestamp": "2026-01-10 08:00:00",
    "status": "NOT_PASS_YET",
    "recipeIds": [1, 5, 12],
    "UserId": 1,
    "groupId": 5,
    "createdAt": "2026-01-02T10:00:00",
    "updatedAt": "2026-01-02T10:00:00"
  }
}
```

**Error Response - User không thuộc group (403):**
```json
{
  "resultCode": "1999",
  "resultMessage": {
    "en": "System error: User does not belong to any group",
    "vn": "Lỗi hệ thống: Người dùng không thuộc nhóm nào"
  }
}
```

**Error Response - Không có quyền (403):**
```json
{
  "resultCode": "1999",
  "resultMessage": {
    "en": "System error: Access denied - Only group owner or admin can modify this data",
    "vn": "Lỗi hệ thống: Truy cập bị từ chối - Chỉ chủ nhóm hoặc admin mới có thể sửa dữ liệu này"
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
  "foodItems": [
    {"foodId": 2, "quantity": 300, "unitName": "g"},
    {"foodId": 3, "quantity": 150, "unitName": "g"},
    {"foodId": 5, "quantity": 200, "unitName": "g"}
  
  "name": "Updated Breakfast Plan",
  "timestamp": "2026-01-10 08:30:00",
  "status": "COMPLETED",
  "recipeIds": [2, 3, 5]
}
```

**Response Success (200):**
```json
{
  "resultCode": "00323",
  "resultMessage": {
    "en": "Update meal plan successful",
    "vn": "Cập nhật kế hoạch bữa ăn thành công"
  },
  "updatedPlan": {
    "foodItems": [
      {"foodId": 2, "quantity": 300, "unitName": "g"},
      {"foodId": 3, "quantity": 150, "unitName": "g"},
      {"foodId": 5, "quantity": 200, "unitName": "g"}
    
    "name": "Updated Breakfast Plan",
    "timestamp": "2026-01-10 08:30:00",
    "status": "COMPLETED",
    "recipeIds": [2, 3, 5],
    "UserId": 1,
    "groupId": 5,
    "createdAt": "2026-01-02T10:00:00",
    "updatedAt": "2026-01-02T11:00:00"
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
  "resultCode": "00324",
  "resultMessage": {
    "en": "Delete meal plan successful",
    "vn": "Xóa kế hoạch bữa ăn thành công"
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
  "resultCode": "00325",
  "resultMessage": {
    "en": "Get all meal plans successful",
    "vn": "Lấy tất cả kế hoạch bữa ăn thành công"
  },
  "meals": [
    {
      "id": 1,
      "name": "Breakfast Plan",
      "timestamp": "2026-01-10 08:00:00",
      "status": "NOT_PASS_YET",
      "foodItems": [
        {"foodId": 1, "quantity": 500, "unitName": "g"},
        {"foodId": 5, "quantity": 200, "unitName": "g"},
        {"foodId": 12, "quantity": 3, "unitName": "quả"}
      ],
      "userId": 1,
      "createdAt": "2026-01-02T10:00:00",
      "updatedAt": null
    },
    {
      "id": 2,
      "name": "Lunch Plan",
      "timestamp": "2026-01-10 12:00:00",
      "status": "COMPLETED",
      "foodItems": [
        {"foodId": 2, "quantity": 300, "unitName": "g"},
        {"foodId": 3, "quantity": 150, "unitName": "g"}
      ],
      "userId": 1,
      "createdAt": "2026-01-02T10:15:00",
      "updatedAt": "2026-01-02T12:30:00"
    },
    {
      "id": 3,
      "name": "Dinner Plan",
      "timestamp": "2026-01-10 19:00:00",
      "status": "NOT_PASS_YET",
      "foodItems": [
        {"foodId": 8, "quantity": 400, "unitName": "g"},
        {"foodId": 9, "quantity": 100, "unitName": "g"},
        {"foodId": 10, "quantity": 50, "unitName": "g"}
      ],
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
  "resultCode": "00326",
  "resultMessage": {
    "en": "Get meal plan details successful",
    "vn": "Lấy chi tiết kế hoạch bữa ăn thành công"
  },
  "meal": {
    "id": 1,
    "name": "Breakfast Plan",
    "timestamp": "2026-01-10 08:00:00",
    "status": "NOT_PASS_YET",
    "foodItems": [
      {"foodId": 1, "quantity": 500, "unitName": "g"},
      {"foodId": 5, "quantity": 200, "unitName": "g"},
      {"foodId": 12, "quantity": 3, "unitName": "quả"}
    ],
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
| 00322 | Add meal plan successfull | Thêm kế hoạch bữa ăn thành công |
| 00323 | Update meal plan successful | Cập nhật kế hoạch bữa ăn thành công |
| 00324 | Delete meal plan successful | Xóa kế hoạch bữa ăn thành công |
| 00325 | Get all meal plans successful | Lấy tất cả kế hoạch bữa ăn thành công |
| 00326 | Get meal plan details successful | Lấy chi tiết kế hoạch bữa ăn thành công |
| 1999 | System error | Lỗi hệ thống |

---

## Notes

1. **Authentication**: Tất cả endpoints yêu cầu `Authorization: Bearer {accessToken}`
2. **Status Values**: 
   - `NOT_PASS_YET`: Bữa ăn chưa hoàn thành
   - `COMPLETED`: Đã hoàn thành
   - `PASSED`: Đã qua thời gian
3. **Recipe IDs**: Danh sách `recipeIds` chứa IDs của các món ăn (recipes). Mỗi meal có nhiều recipes, mỗi recipe lại có nhiều foods (nguyên liệu)
4. **Timestamp Format**: "YYYY-MM-DD HH:mm:ss" (ví dụ: "2026-01-10 08:00:00")
5. **Group Context**: Meals thuộc về group cụ thể (groupId), user thường sẽ tự động gán group của mình, admin có thể chọn group bất kỳ
6. **Response Format**: Response fields bao gồm `UserId` (camelCase U hoa) và `groupId` (lowercase g) - đúng theo implementation
