# Shopping List API Documentation

Base URL: `/api/shopping-lists`

## Table of Contents
1. [Create Shopping List](#1-create-shopping-list)
2. [Get All Shopping Lists](#2-get-all-shopping-lists)
3. [Get Shopping List by ID](#3-get-shopping-list-by-id)
4. [Update Shopping List](#4-update-shopping-list)
5. [Delete Shopping List](#5-delete-shopping-list)
6. [Add Item to Shopping List](#6-add-item-to-shopping-list)
7. [Update Item in Shopping List](#7-update-item-in-shopping-list)
8. [Delete Item from Shopping List](#8-delete-item-from-shopping-list)
9. [Complete Shopping List](#9-complete-shopping-list)

---

## 1. Create Shopping List

Tạo danh sách mua sắm mới.

**Endpoint:** `POST /api/shopping-lists`

**Headers:**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "Weekly Shopping",
  "date": "2026-01-10",
  "note": "Shopping for the week"
}
```

**Response Success (200):**
```json
{
  "resultCode": "00110",
  "resultMessage": {
    "en": "Shopping list created successfully",
    "vn": "Tạo danh sách mua sắm thành công"
  },
  "shoppingList": {
    "id": 1,
    "name": "Weekly Shopping",
    "date": "2026-01-10",
    "note": "Shopping for the week",
    "status": "PENDING",
    "userId": 1,
    "groupId": null,
    "items": [],
    "totalItems": 0,
    "completedItems": 0,
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

## 2. Get All Shopping Lists

Lấy tất cả danh sách mua sắm của user hoặc group.

**Endpoint:** `GET /api/shopping-lists`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Response Success (200):**
```json
{
  "resultCode": "00111",
  "resultMessage": {
    "en": "Shopping lists retrieved successfully",
    "vn": "Lấy danh sách mua sắm thành công"
  },
  "shoppingLists": [
    {
      "id": 1,
      "name": "Weekly Shopping",
      "date": "2026-01-10",
      "note": "Shopping for the week",
      "status": "PENDING",
      "userId": 1,
      "groupId": null,
      "totalItems": 5,
      "completedItems": 2,
      "completionPercentage": 40,
      "createdAt": "2026-01-02T10:00:00"
    },
    {
      "id": 2,
      "name": "Party Supplies",
      "date": "2026-01-15",
      "note": "",
      "status": "COMPLETED",
      "userId": 1,
      "groupId": null,
      "totalItems": 10,
      "completedItems": 10,
      "completionPercentage": 100,
      "createdAt": "2026-01-01T10:00:00",
      "completedAt": "2026-01-01T15:00:00"
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

## 3. Get Shopping List by ID

Lấy chi tiết danh sách mua sắm theo ID.

**Endpoint:** `GET /api/shopping-lists/{id}`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Path Parameters:**
- `id`: ID của shopping list

**Request:**
```
GET /api/shopping-lists/1
```

**Response Success (200):**
```json
{
  "resultCode": "00112",
  "resultMessage": {
    "en": "Shopping list retrieved successfully",
    "vn": "Lấy thông tin danh sách mua sắm thành công"
  },
  "shoppingList": {
    "id": 1,
    "name": "Weekly Shopping",
    "date": "2026-01-10",
    "note": "Shopping for the week",
    "status": "PENDING",
    "userId": 1,
    "groupId": null,
    "items": [
      {
        "id": 1,
        "foodName": "Carrot",
        "quantity": 2.0,
        "unitName": "kg",
        "isCompleted": true,
        "note": "Fresh only",
        "addedAt": "2026-01-02T10:30:00"
      },
      {
        "id": 2,
        "foodName": "Milk",
        "quantity": 3.0,
        "unitName": "liter",
        "isCompleted": false,
        "note": "",
        "addedAt": "2026-01-02T10:31:00"
      },
      {
        "id": 3,
        "foodName": "Bread",
        "quantity": 2.0,
        "unitName": "piece",
        "isCompleted": true,
        "note": "",
        "addedAt": "2026-01-02T10:32:00"
      }
    ],
    "totalItems": 3,
    "completedItems": 2,
    "completionPercentage": 66.67,
    "createdAt": "2026-01-02T10:00:00"
  }
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "Shopping list not found",
    "vn": "Không tìm thấy danh sách mua sắm"
  }
}
```

---

## 4. Update Shopping List

Cập nhật thông tin danh sách mua sắm.

**Endpoint:** `PUT /api/shopping-lists/{id}`

**Headers:**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Path Parameters:**
- `id`: ID của shopping list

**Request Body:**
```json
{
  "name": "Updated Weekly Shopping",
  "date": "2026-01-12",
  "note": "Changed shopping date"
}
```

**Response Success (200):**
```json
{
  "resultCode": "00113",
  "resultMessage": {
    "en": "Shopping list updated successfully",
    "vn": "Cập nhật danh sách mua sắm thành công"
  },
  "shoppingList": {
    "id": 1,
    "name": "Updated Weekly Shopping",
    "date": "2026-01-12",
    "note": "Changed shopping date",
    "status": "PENDING",
    "userId": 1,
    "items": [...],
    "totalItems": 3,
    "completedItems": 2,
    "updatedAt": "2026-01-02T11:00:00"
  }
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "Shopping list not found",
    "vn": "Không tìm thấy danh sách mua sắm"
  }
}
```

---

## 5. Delete Shopping List

Xóa danh sách mua sắm.

**Endpoint:** `DELETE /api/shopping-lists/{id}`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Path Parameters:**
- `id`: ID của shopping list

**Request:**
```
DELETE /api/shopping-lists/1
```

**Response Success (200):**
```json
{
  "resultCode": "00114",
  "resultMessage": {
    "en": "Shopping list deleted successfully",
    "vn": "Xóa danh sách mua sắm thành công"
  }
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "Shopping list not found",
    "vn": "Không tìm thấy danh sách mua sắm"
  }
}
```

---

## 6. Add Item to Shopping List

Thêm mục mới vào danh sách mua sắm.

**Endpoint:** `POST /api/shopping-lists/{id}/items`

**Headers:**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Path Parameters:**
- `id`: ID của shopping list

**Request Body:**
```json
{
  "foodName": "Apple",
  "quantity": 5.0,
  "unitName": "piece",
  "note": "Red apples"
}
```

**Response Success (200):**
```json
{
  "resultCode": "00115",
  "resultMessage": {
    "en": "Item added to shopping list successfully",
    "vn": "Thêm mục vào danh sách mua sắm thành công"
  },
  "shoppingList": {
    "id": 1,
    "name": "Weekly Shopping",
    "items": [
      {
        "id": 4,
        "foodName": "Apple",
        "quantity": 5.0,
        "unitName": "piece",
        "isCompleted": false,
        "note": "Red apples",
        "addedAt": "2026-01-02T12:00:00"
      },
      ...
    ],
    "totalItems": 4,
    "completedItems": 2
  }
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "Shopping list not found",
    "vn": "Không tìm thấy danh sách mua sắm"
  }
}
```

---

## 7. Update Item in Shopping List

Cập nhật mục trong danh sách mua sắm.

**Endpoint:** `PUT /api/shopping-lists/{id}/items/{itemId}`

**Headers:**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Path Parameters:**
- `id`: ID của shopping list
- `itemId`: ID của item

**Request Body:**
```json
{
  "foodName": "Green Apple",
  "quantity": 6.0,
  "unitName": "piece",
  "isCompleted": true,
  "note": "Changed to green apples"
}
```

**Response Success (200):**
```json
{
  "resultCode": "00116",
  "resultMessage": {
    "en": "Item updated successfully",
    "vn": "Cập nhật mục thành công"
  },
  "shoppingList": {
    "id": 1,
    "name": "Weekly Shopping",
    "items": [
      {
        "id": 4,
        "foodName": "Green Apple",
        "quantity": 6.0,
        "unitName": "piece",
        "isCompleted": true,
        "note": "Changed to green apples",
        "updatedAt": "2026-01-02T12:30:00"
      },
      ...
    ],
    "totalItems": 4,
    "completedItems": 3
  }
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "Item not found",
    "vn": "Không tìm thấy mục"
  }
}
```

---

## 8. Delete Item from Shopping List

Xóa mục khỏi danh sách mua sắm.

**Endpoint:** `DELETE /api/shopping-lists/{id}/items/{itemId}`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Path Parameters:**
- `id`: ID của shopping list
- `itemId`: ID của item

**Request:**
```
DELETE /api/shopping-lists/1/items/4
```

**Response Success (200):**
```json
{
  "resultCode": "00117",
  "resultMessage": {
    "en": "Item deleted successfully",
    "vn": "Xóa mục thành công"
  },
  "shoppingList": {
    "id": 1,
    "name": "Weekly Shopping",
    "items": [...],
    "totalItems": 3,
    "completedItems": 2
  }
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "Item not found",
    "vn": "Không tìm thấy mục"
  }
}
```

---

## 9. Complete Shopping List

Đánh dấu danh sách mua sắm là hoàn thành.

**Endpoint:** `POST /api/shopping-lists/{id}/complete`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Path Parameters:**
- `id`: ID của shopping list

**Request:**
```
POST /api/shopping-lists/1/complete
```

**Response Success (200):**
```json
{
  "resultCode": "00118",
  "resultMessage": {
    "en": "Shopping list completed successfully",
    "vn": "Hoàn thành danh sách mua sắm thành công"
  },
  "shoppingList": {
    "id": 1,
    "name": "Weekly Shopping",
    "status": "COMPLETED",
    "completedAt": "2026-01-02T13:00:00",
    "totalItems": 3,
    "completedItems": 3,
    "completionPercentage": 100
  }
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "Shopping list not found",
    "vn": "Không tìm thấy danh sách mua sắm"
  }
}
```

---

## Error Codes Summary

| Code | Description (EN) | Description (VN) |
|------|------------------|------------------|
| 00110 | Shopping list created | Tạo danh sách mua sắm thành công |
| 00111 | Shopping lists retrieved | Lấy danh sách mua sắm thành công |
| 00112 | Shopping list retrieved | Lấy chi tiết danh sách mua sắm thành công |
| 00113 | Shopping list updated | Cập nhật danh sách mua sắm thành công |
| 00114 | Shopping list deleted | Xóa danh sách mua sắm thành công |
| 00115 | Item added | Thêm mục thành công |
| 00116 | Item updated | Cập nhật mục thành công |
| 00117 | Item deleted | Xóa mục thành công |
| 00118 | Shopping list completed | Hoàn thành danh sách mua sắm |
| 1404 | Not found | Không tìm thấy |
| 1999 | System error | Lỗi hệ thống |

---

## Notes

1. **Authentication**: Tất cả endpoints yêu cầu `Authorization: Bearer {accessToken}`
2. **Status Types**:
   - `PENDING`: Danh sách đang chờ mua sắm
   - `COMPLETED`: Đã hoàn thành
3. **Completion Tracking**: Tự động tính completionPercentage dựa trên số items completed
4. **User/Group Context**: Shopping lists thuộc về user hoặc group
5. **Items Management**: Có thể thêm/sửa/xóa items trong danh sách
6. **Item Status**: Mỗi item có trạng thái `isCompleted` để track việc mua sắm
7. **Date Format**: ISO 8601 format (YYYY-MM-DD)
