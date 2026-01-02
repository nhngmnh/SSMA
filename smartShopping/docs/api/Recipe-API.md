# Recipe API Documentation

Base URL: `/api/recipe`

## Business Logic

### Structure:
```
Recipe (Công thức nấu ăn) - 1 recipe có nhiều nguyên liệu
  ├── Food 1: Thịt heo (Food ID: 10)
  ├── Food 2: Rau cải (Food ID: 20)
  ├── Food 3: Hành (Food ID: 30)
  └── Food 4: Tỏi (Food ID: 40)
```

### Data Storage:
- **Recipe.foodIds**: JSON array `[10, 20, 30, 40]` - Danh sách food IDs (nguyên liệu)
- Không dùng quan hệ JPA, xử lý trong logic service
- Frontend cần gọi Food API để lấy thông tin chi tiết từng food

### Recipe Types:
1. **Public Recipe (isPublic = true)**:
   - Được tạo bởi admin
   - Visible cho tất cả users
   - Chỉ admin mới có thể modify/delete

2. **Group Recipe (isPublic = false)**:
   - Thuộc về 1 group cụ thể
   - Chỉ members trong group mới thấy
   - Group owner hoặc admin có thể modify/delete

---

## Table of Contents
1. [Get Recipes by Food ID](#1-get-recipes-by-food-id)
2. [Create Recipe](#2-create-recipe)
3. [Update Recipe](#3-update-recipe)
4. [Delete Recipe](#4-delete-recipe)

---

## 1. Get Recipes by Food ID

Lấy danh sách công thức nấu ăn theo thực phẩm.

**Endpoint:** `GET /api/recipe`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Query Parameters:**
- `foodId` (required): ID của thực phẩm

**Request:**
```
GET /api/recipe?foodId=1
```

**Response Success (200):**
```json
{
  "resultCode": "00360",
  "resultMessage": {
    "en": "Get recipes successful",
    "vn": "Lấy danh sách công thức thành công"
  },
  "recipes": [
    {
      "id": 1,
      "name": "Canh thịt heo nấu rau cải",
      "description": "Món canh đơn giản, bổ dưỡng",
      "htmlContent": "<h1>Cách nấu</h1><p>Bước 1: Luộc thịt...</p>",
      "foodIds": [10, 20, 30, 40],
      "createdAt": "2026-01-01 10:00:00",
      "updatedAt": "2026-01-01 10:00:00"
    },
    {
      "id": 2,
      "name": "Canh cá nấu chua",
      "description": "Món canh chua miền Nam",
      "htmlContent": "<h1>Cách nấu</h1><p>Bước 1: Làm sạch cá...</p>",
      "foodIds": [15, 25, 35],
      "createdAt": "2026-01-01 11:00:00",
      "updatedAt": "2026-01-01 11:00:00"
    }
  ]
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

## 2. Create Recipe

Tạo công thức nấu ăn mới.

**Endpoint:** `POST /api/recipe`

**Headers:**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "Canh thịt heo nấu rau cải",
  "description": "Món canh đơn giản, bổ dưỡng",
  "htmlContent": "<h1>Cách nấu</h1><p>Bước 1: Luộc thịt...</p>",
  "foodIds": [10, 20, 30, 40]
}
```

**Field Descriptions:**
- `name`: Tên món ăn
- `description`: Mô tả ngắn
- `htmlContent`: Nội dung chi tiết (HTML format)
- `foodIds`: Danh sách food IDs (nguyên liệu)

**Response Success (200):**
```json
{
  "resultCode": "00357",
  "resultMessage": {
    "en": "Add recipe successfull",
    "vn": "Thêm công thức nấu ăn thành công"
  },
  "newRecipe": {
    "id": 15,
    "name": "Canh thịt heo nấu rau cải",
    "description": "Món canh đơn giản, bổ dưỡng",
    "htmlContent": "<h1>Cách nấu</h1><p>Bước 1: Luộc thịt...</p>",
    "foodIds": [10, 20, 30, 40],
    "createdAt": "2026-01-02 14:30:00",
    "updatedAt": "2026-01-02 14:30:00"
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

## 3. Update Recipe

Cập nhật thông tin công thức.

**Endpoint:** `PUT /api/recipe`

**Headers:**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Request Body:**
```json
{
  "id": 15,
  "name": "Canh thịt heo nấu rau cải - Updated",
  "description": "Món canh đơn giản, bổ dưỡng - mô tả mới",
  "htmlContent": "<h1>Cách nấu mới</h1><p>Bước 1: Luộc thịt kỹ hơn...</p>",
  "foodIds": [10, 20, 30, 40, 50]
}
```

**Response Success (200):**
```json
{
  "resultCode": "00358",
  "resultMessage": {
    "en": "Update recipe successful",
    "vn": "Cập nhật công thức nấu ăn thành công"
  },
  "updatedRecipe": {
    "id": 15,
    "name": "Canh thịt heo nấu rau cải - Updated",
    "description": "Món canh đơn giản, bổ dưỡng - mô tả mới",
    "htmlContent": "<h1>Cách nấu mới</h1><p>Bước 1: Luộc thịt kỹ hơn...</p>",
    "foodIds": [10, 20, 30, 40, 50],
    "createdAt": "2026-01-02 14:30:00",
    "updatedAt": "2026-01-02 15:45:00"
  }
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "Recipe not found",
    "vn": "Không tìm thấy công thức"
  }
}
```

---

## 4. Delete Recipe

Xóa công thức nấu ăn.

**Endpoint:** `DELETE /api/recipe`

**Headers:**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Request Body:**
```json
{
  "recipeId": 15
}
```

**Response Success (200):**
```json
{
  "resultCode": "00359",
  "resultMessage": {
    "en": "Delete recipe successful",
    "vn": "Xóa công thức nấu ăn thành công"
  }
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "Recipe not found",
    "vn": "Không tìm thấy công thức"
  }
}
```

---

## Error Codes Summary

| Code | Description (EN) | Description (VN) |
|------|------------------|------------------|
| 00357 | Add recipe successful | Thêm công thức nấu ăn thành công |
| 00358 | Update recipe successful | Cập nhật công thức nấu ăn thành công |
| 00359 | Delete recipe successful | Xóa công thức nấu ăn thành công |
| 00360 | Get recipes successful | Lấy danh sách công thức thành công |
| 1999 | System error | Lỗi hệ thống |

---

## Notes

1. **Authentication**: Tất cả endpoints yêu cầu `Authorization: Bearer {accessToken}`
2. **Food IDs**: Danh sách `foodIds` chứa IDs của các nguyên liệu. Frontend cần gọi Food API riêng để lấy thông tin chi tiết (tên, đơn vị, hình ảnh...)
3. **HTML Content**: Field `htmlContent` chứa hướng dẫn nấu ăn ở định dạng HTML
4. **Recipe Search**: Có thể tìm recipes theo foodId - sẽ trả về tất cả recipes có chứa food đó trong danh sách nguyên liệu
5. **Group Recipes**: Recipes có thể thuộc về group (có `groupId`) hoặc là public recipes
6. **Timestamps**: `createdAt` và `updatedAt` theo format "yyyy-MM-dd HH:mm:ss"
