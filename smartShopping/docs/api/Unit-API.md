# Unit API Documentation (Admin)

Base URL: `/api/admin/unit`

## Table of Contents
1. [Create Unit](#1-create-unit)
2. [Get All Units](#2-get-all-units)
3. [Update Unit](#3-update-unit)
4. [Delete Unit](#4-delete-unit)

---

## 1. Create Unit

Tạo đơn vị đo lường mới (ADMIN only).

**Endpoint:** `POST /api/admin/unit`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Query Parameters:**
- `unitName` (required): Tên đơn vị

**Request:**
```
POST /api/admin/unit?unitName=kg
```

**Response Success (200):**
```json
{
  "resultCode": "00160",
  "resultMessage": {
    "en": "Unit created successfully",
    "vn": "Tạo đơn vị thành công"
  },
  "unit": {
    "id": 1,
    "name": "kg",
    "description": "Kilogram",
    "createdAt": "2026-01-02T10:00:00"
  }
}
```

**Error Response (409):**
```json
{
  "resultCode": "40901",
  "resultMessage": {
    "en": "Unit already exists",
    "vn": "Đơn vị đã tồn tại"
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

## 2. Get All Units

Lấy tất cả đơn vị đo lường (ADMIN only).

**Endpoint:** `GET /api/admin/unit`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Query Parameters:**
- `unitName` (optional): Tên đơn vị cụ thể để tìm kiếm

**Request:**
```
GET /api/admin/unit
GET /api/admin/unit?unitName=kg
```

**Response Success (200):**
```json
{
  "resultCode": "00161",
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
      "name": "ml",
      "description": "Milliliter"
    },
    {
      "id": 5,
      "name": "piece",
      "description": "Piece/Count"
    },
    {
      "id": 6,
      "name": "tbsp",
      "description": "Tablespoon"
    },
    {
      "id": 7,
      "name": "tsp",
      "description": "Teaspoon"
    },
    {
      "id": 8,
      "name": "cup",
      "description": "Cup"
    }
  ]
}
```

**Response Success (200) - With unitName:**
```json
{
  "resultCode": "00161",
  "resultMessage": {
    "en": "Unit retrieved successfully",
    "vn": "Lấy thông tin đơn vị thành công"
  },
  "units": [
    {
      "id": 1,
      "name": "kg",
      "description": "Kilogram"
    }
  ]
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "Unit not found",
    "vn": "Không tìm thấy đơn vị"
  }
}
```

---

## 3. Update Unit

Cập nhật tên đơn vị (ADMIN only).

**Endpoint:** `PUT /api/admin/unit`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Query Parameters:**
- `oldName` (required): Tên cũ của đơn vị
- `newName` (required): Tên mới của đơn vị

**Request:**
```
PUT /api/admin/unit?oldName=kilogram&newName=kg
```

**Response Success (200):**
```json
{
  "resultCode": "00162",
  "resultMessage": {
    "en": "Unit updated successfully",
    "vn": "Cập nhật đơn vị thành công"
  },
  "unit": {
    "id": 1,
    "name": "kg",
    "description": "Kilogram",
    "updatedAt": "2026-01-02T11:00:00"
  }
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "Unit not found",
    "vn": "Không tìm thấy đơn vị"
  }
}
```

**Error Response (409):**
```json
{
  "resultCode": "40901",
  "resultMessage": {
    "en": "New unit name already exists",
    "vn": "Tên đơn vị mới đã tồn tại"
  }
}
```

---

## 4. Delete Unit

Xóa đơn vị đo lường (ADMIN only).

**Endpoint:** `DELETE /api/admin/unit`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Query Parameters:**
- `unitName` (required): Tên đơn vị cần xóa

**Request:**
```
DELETE /api/admin/unit?unitName=kg
```

**Response Success (200):**
```json
{
  "resultCode": "00163",
  "resultMessage": {
    "en": "Unit deleted successfully",
    "vn": "Xóa đơn vị thành công"
  }
}
```

**Error Response (404):**
```json
{
  "resultCode": "1404",
  "resultMessage": {
    "en": "Unit not found",
    "vn": "Không tìm thấy đơn vị"
  }
}
```

**Error Response (409):**
```json
{
  "resultCode": "40902",
  "resultMessage": {
    "en": "Cannot delete unit - it is being used by foods or recipes",
    "vn": "Không thể xóa đơn vị - đang được sử dụng bởi thực phẩm hoặc công thức"
  }
}
```

---

## Error Codes Summary

| Code | Description (EN) | Description (VN) |
|------|------------------|------------------|
| 00160 | Unit created | Tạo đơn vị thành công |
| 00161 | Units retrieved | Lấy danh sách đơn vị thành công |
| 00162 | Unit updated | Cập nhật đơn vị thành công |
| 00163 | Unit deleted | Xóa đơn vị thành công |
| 1404 | Unit not found | Không tìm thấy đơn vị |
| 40901 | Unit already exists | Đơn vị đã tồn tại |
| 40902 | Cannot delete unit in use | Không thể xóa đơn vị đang sử dụng |
| 1999 | System error | Lỗi hệ thống |

---

## Notes

1. **Authentication**: Tất cả endpoints yêu cầu `Authorization: Bearer {accessToken}` với quyền ADMIN
2. **Access Control**: Chỉ ADMIN mới có quyền truy cập các endpoints này
3. **Common Units**: 
   - Weight: kg, g, mg, oz, lb
   - Volume: liter, ml, cup, tbsp, tsp, gallon
   - Count: piece, slice, whole
4. **Unit Name**: Nên sử dụng tên viết tắt chuẩn quốc tế
5. **Delete Restriction**: Không thể xóa đơn vị đang được sử dụng bởi foods hoặc recipes
6. **Case Sensitivity**: Unit names được xử lý case-insensitive khi tìm kiếm
