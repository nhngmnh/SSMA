# Fridge API Documentation

Base URL: `/api/fridge`

## Table of Contents
1. [Create Fridge Item](#1-create-fridge-item)
2. [Update Fridge Item](#2-update-fridge-item)
3. [Delete Fridge Item](#3-delete-fridge-item)
4. [Get All Fridge Items](#4-get-all-fridge-items)
5. [Get Fridge Items by Food Name](#5-get-fridge-items-by-food-name)

---

## 1. Create Fridge Item

Tạo mục thực phẩm mới trong tủ lạnh.

**Endpoint:** `POST /api/fridge`

**Headers:**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Request Body:**
```json
{
  "foodName": "Carrot",
  "quantity": 2.5,
  "unitName": "kg",
  "expirationDate": "2026-01-15",
  "note": "Fresh from market"
}
```

**Response Success (200):**
```json
{
  "resultCode": "00100",
  "resultMessage": {
    "en": "Fridge item created successfully",
    "vn": "Tạo mục tủ lạnh thành công"
  },
  "fridgeItem": {
    "id": 1,
    "foodName": "Carrot",
    "quantity": 2.5,
    "unitName": "kg",
    "expirationDate": "2026-01-15",
    "note": "Fresh from market",
    "userId": 1,
    "groupId": null,
    "createdAt": "2026-01-02T10:00:00"
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

## 2. Update Fridge Item

Cập nhật thông tin mục trong tủ lạnh.

**Endpoint:** `PUT /api/fridge/{id}`

**Headers:**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Path Parameters:**
- `id`: ID của fridge item

**Request Body:**
```json
{
  "foodName": "Fresh Carrot",
  "quantity": 3.0,
  "unitName": "kg",
  "expirationDate": "2026-01-20",
  "note": "Updated quantity"
}
```

**Response Success (200):**
```json
{
  "resultCode": "00101",
  "resultMessage": {
    "en": "Fridge item updated successfully",
    "vn": "Cập nhật mục tủ lạnh thành công"
  },
  "fridgeItem": {
    "id": 1,
    "foodName": "Fresh Carrot",
    "quantity": 3.0,
    "unitName": "kg",
    "expirationDate": "2026-01-20",
    "note": "Updated quantity",
    "updatedAt": "2026-01-02T11:00:00"
  }
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "Fridge item not found",
    "vn": "Không tìm thấy mục tủ lạnh"
  }
}
```

---

## 3. Delete Fridge Item

Xóa mục khỏi tủ lạnh.

**Endpoint:** `DELETE /api/fridge/{id}`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Path Parameters:**
- `id`: ID của fridge item

**Request:**
```
DELETE /api/fridge/1
```

**Response Success (200):**
```json
{
  "resultCode": "00102",
  "resultMessage": {
    "en": "Fridge item deleted successfully",
    "vn": "Xóa mục tủ lạnh thành công"
  }
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "Fridge item not found",
    "vn": "Không tìm thấy mục tủ lạnh"
  }
}
```

---

## 4. Get All Fridge Items

Lấy tất cả mục trong tủ lạnh của user hoặc group.

**Endpoint:** `GET /api/fridge`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Query Parameters:**
- None

**Response Success (200):**
```json
{
  "resultCode": "00103",
  "resultMessage": {
    "en": "Fridge items retrieved successfully",
    "vn": "Lấy danh sách tủ lạnh thành công"
  },
  "fridgeItems": [
    {
      "id": 1,
      "foodName": "Carrot",
      "quantity": 2.5,
      "unitName": "kg",
      "expirationDate": "2026-01-15",
      "note": "Fresh from market",
      "userId": 1,
      "groupId": null,
      "daysUntilExpiration": 13,
      "status": "FRESH",
      "createdAt": "2026-01-02T10:00:00"
    },
    {
      "id": 2,
      "foodName": "Milk",
      "quantity": 2.0,
      "unitName": "liter",
      "expirationDate": "2026-01-05",
      "note": "",
      "userId": 1,
      "groupId": null,
      "daysUntilExpiration": 3,
      "status": "EXPIRING_SOON",
      "createdAt": "2026-01-01T10:00:00"
    },
    {
      "id": 3,
      "foodName": "Bread",
      "quantity": 1.0,
      "unitName": "piece",
      "expirationDate": "2026-01-01",
      "note": "",
      "userId": 1,
      "groupId": null,
      "daysUntilExpiration": -1,
      "status": "EXPIRED",
      "createdAt": "2025-12-28T10:00:00"
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

## 5. Get Fridge Items by Food Name

Lấy các mục trong tủ lạnh theo tên thực phẩm.

**Endpoint:** `GET /api/fridge/search`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Query Parameters:**
- `foodName` (required): Tên thực phẩm cần tìm

**Request:**
```
GET /api/fridge/search?foodName=Carrot
```

**Response Success (200):**
```json
{
  "resultCode": "00103",
  "resultMessage": {
    "en": "Fridge items found",
    "vn": "Tìm thấy mục tủ lạnh"
  },
  "fridgeItems": [
    {
      "id": 1,
      "foodName": "Carrot",
      "quantity": 2.5,
      "unitName": "kg",
      "expirationDate": "2026-01-15",
      "note": "Fresh from market",
      "userId": 1,
      "groupId": null,
      "daysUntilExpiration": 13,
      "status": "FRESH"
    }
  ]
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "No fridge items found with this food name",
    "vn": "Không tìm thấy mục nào với tên thực phẩm này"
  }
}
```

---

## Error Codes Summary

| Code | Description (EN) | Description (VN) |
|------|------------------|------------------|
| 00100 | Fridge item created | Tạo mục tủ lạnh thành công |
| 00101 | Fridge item updated | Cập nhật mục tủ lạnh thành công |
| 00102 | Fridge item deleted | Xóa mục tủ lạnh thành công |
| 00103 | Fridge items retrieved | Lấy danh sách tủ lạnh thành công |
| 1404 | Item not found | Không tìm thấy mục |
| 1999 | System error | Lỗi hệ thống |

---

## Notes

1. **Authentication**: Tất cả endpoints yêu cầu `Authorization: Bearer {accessToken}`
2. **Expiration Status**: 
   - `FRESH`: Còn hạn (> 3 ngày)
   - `EXPIRING_SOON`: Sắp hết hạn (≤ 3 ngày)
   - `EXPIRED`: Đã hết hạn (< 0 ngày)
3. **Days Until Expiration**: Tính tự động dựa trên expirationDate
4. **User/Group Context**: Fridge items thuộc về user hoặc group
5. **Date Format**: ISO 8601 format (YYYY-MM-DD)
6. **Quantity**: Số thực (decimal) để cho phép đo lường chính xác
