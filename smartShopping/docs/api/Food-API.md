# Food API Documentation

Base URL: `/api/food`

## Table of Contents
1. [Create Food](#1-create-food)
2. [Update Food](#2-update-food)
3. [Delete Food](#3-delete-food)
4. [Get All Foods](#4-get-all-foods)
5. [Get Food by ID](#5-get-food-by-id)
6. [Search Foods](#6-search-foods)
7. [Get Foods by Group ID](#7-get-foods-by-group-id)
8. [Get All Units](#8-get-all-units)
9. [Get All Categories](#9-get-all-categories)

---

## 1. Create Food

Tạo thực phẩm mới.

**Endpoint:** `POST /api/food`

**Headers:**
```
Authorization: Bearer {accessToken}
Content-Type: multipart/form-data
```

**Form Data:**
- `name` (required): Tên thực phẩm
- `categoryName` (required): Tên category
- `unitName` (required): Tên đơn vị
- `useWithin` (optional): Thời gian sử dụng (phút)
- `image` (optional): File ảnh thực phẩm

**Response Success (200):**
```json
{
  "resultCode": "00124",
  "resultMessage": {
    "en": "Food created successfully",
    "vn": "Tạo thực phẩm thành công"
  },
  "food": {
    "id": 1,
    "name": "Carrot",
    "categoryName": "Vegetables",
    "unitName": "kg",
    "useWithin": 1440,
    "imageUrl": "https://storage.example.com/foods/carrot.jpg",
    "userId": 1,
    "groupId": null,
    "createdAt": "2026-01-02T00:00:00"
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

## 2. Update Food

Cập nhật thông tin thực phẩm.

**Endpoint:** `PUT /api/food`

**Headers:**
```
Authorization: Bearer {accessToken}
Content-Type: multipart/form-data
```

**Form Data:**
- `oldName` (required): Tên cũ của thực phẩm
- `newName` (optional): Tên mới
- `newCategoryName` (optional): Category mới
- `newUnitName` (optional): Đơn vị mới
- `newUseWithin` (optional): Thời gian sử dụng mới
- `newImage` (optional): Ảnh mới

**Response Success (200):**
```json
{
  "resultCode": "00125",
  "resultMessage": {
    "en": "Food updated successfully",
    "vn": "Cập nhật thực phẩm thành công"
  },
  "food": {
    "id": 1,
    "name": "Fresh Carrot",
    "categoryName": "Vegetables",
    "unitName": "kg",
    "useWithin": 1440,
    "imageUrl": "https://storage.example.com/foods/carrot-new.jpg",
    "updatedAt": "2026-01-02T00:00:00"
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

## 3. Delete Food

Xóa thực phẩm.

**Endpoint:** `DELETE /api/food`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Query Parameters:**
- `name` (required): Tên thực phẩm cần xóa

**Request:**
```
DELETE /api/food?name=Carrot
```

**Response Success (200):**
```json
{
  "resultCode": "00126",
  "resultMessage": {
    "en": "Food deleted successfully",
    "vn": "Xóa thực phẩm thành công"
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

## 4. Get All Foods

Lấy danh sách tất cả thực phẩm.

**Endpoint:** `GET /api/food`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Response Success (200):**
```json
{
  "resultCode": "00127",
  "resultMessage": {
    "en": "Foods retrieved successfully",
    "vn": "Lấy danh sách thực phẩm thành công"
  },
  "foods": [
    {
      "id": 1,
      "name": "Carrot",
      "categoryName": "Vegetables",
      "unitName": "kg",
      "useWithin": 1440,
      "imageUrl": "https://...",
      "userId": 1,
      "groupId": null,
      "createdAt": "2026-01-01T00:00:00"
    },
    {
      "id": 2,
      "name": "Apple",
      "categoryName": "Fruits",
      "unitName": "piece",
      "useWithin": 10080,
      "imageUrl": "https://...",
      "userId": 1,
      "groupId": null,
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
    "en": "System error",
    "vn": "Lỗi hệ thống"
  }
}
```

---

## 5. Get Food by ID

Lấy thông tin chi tiết 1 thực phẩm.

**Endpoint:** `GET /api/food/{id}`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Path Parameters:**
- `id`: ID của thực phẩm

**Request:**
```
GET /api/food/1
```

**Response Success (200):**
```json
{
  "resultCode": "00128",
  "resultMessage": {
    "en": "Food retrieved successfully",
    "vn": "Lấy thông tin thực phẩm thành công"
  },
  "food": {
    "id": 1,
    "name": "Carrot",
    "categoryName": "Vegetables",
    "unitName": "kg",
    "useWithin": 1440,
    "imageUrl": "https://...",
    "userId": 1,
    "groupId": null,
    "createdAt": "2026-01-01T00:00:00"
  }
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "Food not found",
    "vn": "Không tìm thấy thực phẩm"
  }
}
```

---

## 6. Search Foods

Tìm kiếm thực phẩm theo từ khóa.

**Endpoint:** `GET /api/food/search`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Query Parameters:**
- `keyword` (required): Từ khóa tìm kiếm

**Request:**
```
GET /api/food/search?keyword=car
```

**Response Success (200):**
```json
{
  "resultCode": "00127",
  "resultMessage": {
    "en": "Search results retrieved successfully",
    "vn": "Tìm kiếm thành công"
  },
  "foods": [
    {
      "id": 1,
      "name": "Carrot",
      "categoryName": "Vegetables",
      "unitName": "kg",
      "useWithin": 1440,
      "imageUrl": "https://...",
      "userId": 1,
      "groupId": null
    }
  ]
}
```

---

## 7. Get Foods by Group ID

Lấy danh sách thực phẩm theo group.

**Endpoint:** `GET /api/food/group/{groupId}`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Path Parameters:**
- `groupId`: ID của nhóm

**Request:**
```
GET /api/food/group/1
```

**Response Success (200):**
```json
{
  "resultCode": "00127",
  "resultMessage": {
    "en": "Group foods retrieved successfully",
    "vn": "Lấy thực phẩm của nhóm thành công"
  },
  "foods": [
    {
      "id": 3,
      "name": "Milk",
      "categoryName": "Dairy",
      "unitName": "liter",
      "useWithin": 10080,
      "imageUrl": "https://...",
      "userId": null,
      "groupId": 1
    }
  ]
}
```

---

## 8. Get All Units

Lấy danh sách đơn vị đo lường.

**Endpoint:** `GET /api/food/unit`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Query Parameters:**
- `unitName` (optional): Tên đơn vị cụ thể

**Request:**
```
GET /api/food/unit
GET /api/food/unit?unitName=kg
```

**Response Success (200):**
```json
{
  "resultCode": "00130",
  "resultMessage": {
    "en": "Units retrieved successfully",
    "vn": "Lấy danh sách đơn vị thành công"
  },
  "units": [
    {
      "id": 1,
      "name": "kg",
      "description": "Kilogram"
    },
    {
      "id": 2,
      "name": "g",
      "description": "Gram"
    },
    {
      "id": 3,
      "name": "liter",
      "description": "Liter"
    },
    {
      "id": 4,
      "name": "piece",
      "description": "Piece"
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

## 9. Get All Categories

Lấy danh sách tất cả categories (dành cho user).

**Endpoint:** `GET /api/food/category`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Response Success (200):**
```json
{
  "resultCode": "00129",
  "resultMessage": {
    "en": "Categories retrieved successfully",
    "vn": "Lấy danh sách category thành công"
  },
  "categories": [
    {
      "id": 1,
      "name": "Vegetables"
    },
    {
      "id": 2,
      "name": "Fruits"
    },
    {
      "id": 3,
      "name": "Dairy"
    },
    {
      "id": 4,
      "name": "Meat"
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

## Error Codes Summary

| Code | Description (EN) | Description (VN) |
|------|------------------|------------------|
| 00124 | Food created | Tạo thực phẩm thành công |
| 00125 | Food updated | Cập nhật thực phẩm thành công |
| 00126 | Food deleted | Xóa thực phẩm thành công |
| 00127 | Foods retrieved | Lấy danh sách thực phẩm thành công |
| 00128 | Food retrieved | Lấy thông tin thực phẩm thành công |
| 00129 | Categories retrieved | Lấy categories thành công |
| 00130 | Units retrieved | Lấy đơn vị thành công |
| 1404 | Food not found | Không tìm thấy thực phẩm |
| 1999 | System error | Lỗi hệ thống |

---

## Notes

1. **Authentication**: Tất cả endpoints yêu cầu `Authorization: Bearer {accessToken}`
2. **File Upload**: Các endpoint create/update hỗ trợ upload ảnh qua multipart/form-data
3. **User Context**: Thực phẩm được gắn với userId hoặc groupId
4. **Search**: Tìm kiếm theo tên thực phẩm (case-insensitive)
5. **Units**: Danh sách các đơn vị đo lường chuẩn (kg, g, liter, piece, ...)
