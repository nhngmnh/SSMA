# Recipe API Documentation

Base URL: `/api/recipe`

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
  "resultCode": "00150",
  "resultMessage": {
    "en": "Recipes retrieved successfully",
    "vn": "Lấy công thức thành công"
  },
  "recipes": [
    {
      "id": 1,
      "name": "Scrambled Eggs",
      "description": "Simple and delicious scrambled eggs",
      "mainFoodId": 1,
      "mainFoodName": "Egg",
      "servings": 2,
      "prepTime": 5,
      "cookTime": 10,
      "totalTime": 15,
      "difficulty": "EASY",
      "cuisine": "Western",
      "createdAt": "2026-01-01T10:00:00"
    },
    {
      "id": 2,
      "name": "Boiled Eggs",
      "description": "Perfect hard-boiled eggs",
      "mainFoodId": 1,
      "mainFoodName": "Egg",
      "servings": 4,
      "prepTime": 2,
      "cookTime": 12,
      "totalTime": 14,
      "difficulty": "EASY",
      "cuisine": "Universal",
      "createdAt": "2026-01-01T11:00:00"
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
  "name": "Scrambled Eggs",
  "description": "Simple and delicious scrambled eggs",
  "mainFoodId": 1,
  "servings": 2,
  "prepTime": 5,
  "cookTime": 10,
  "difficulty": "EASY",
  "cuisine": "Western",
  "ingredients": [
    {
      "foodId": 1,
      "foodName": "Egg",
      "quantity": 4,
      "unitName": "piece",
      "note": "Large eggs"
    },
    {
      "foodId": 2,
      "foodName": "Milk",
      "quantity": 50,
      "unitName": "ml",
      "note": "Whole milk"
    },
    {
      "foodId": 3,
      "foodName": "Butter",
      "quantity": 20,
      "unitName": "g",
      "note": "Unsalted butter"
    }
  ],
  "instructions": [
    {
      "stepNumber": 1,
      "description": "Crack eggs into a bowl and add milk",
      "duration": 2
    },
    {
      "stepNumber": 2,
      "description": "Beat eggs and milk together until well combined",
      "duration": 3
    },
    {
      "stepNumber": 3,
      "description": "Heat butter in a non-stick pan over medium heat",
      "duration": 2
    },
    {
      "stepNumber": 4,
      "description": "Pour egg mixture into pan and cook, stirring gently",
      "duration": 8
    }
  ],
  "tags": ["breakfast", "quick", "easy", "protein"],
  "note": "For creamier eggs, remove from heat while still slightly runny"
}
```

**Response Success (200):**
```json
{
  "resultCode": "00151",
  "resultMessage": {
    "en": "Recipe created successfully",
    "vn": "Tạo công thức thành công"
  },
  "recipe": {
    "id": 1,
    "name": "Scrambled Eggs",
    "description": "Simple and delicious scrambled eggs",
    "mainFoodId": 1,
    "mainFoodName": "Egg",
    "servings": 2,
    "prepTime": 5,
    "cookTime": 10,
    "totalTime": 15,
    "difficulty": "EASY",
    "cuisine": "Western",
    "ingredients": [...],
    "instructions": [...],
    "tags": ["breakfast", "quick", "easy", "protein"],
    "note": "For creamier eggs, remove from heat while still slightly runny",
    "createdBy": 1,
    "createdAt": "2026-01-02T10:00:00"
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
  "recipeId": 1,
  "name": "Perfect Scrambled Eggs",
  "description": "Updated description",
  "servings": 3,
  "prepTime": 5,
  "cookTime": 12,
  "difficulty": "EASY",
  "cuisine": "Western",
  "ingredients": [
    {
      "foodId": 1,
      "foodName": "Egg",
      "quantity": 6,
      "unitName": "piece",
      "note": "Large eggs"
    },
    {
      "foodId": 2,
      "foodName": "Milk",
      "quantity": 75,
      "unitName": "ml",
      "note": "Whole milk"
    }
  ],
  "instructions": [...],
  "tags": ["breakfast", "easy"],
  "note": "Updated note"
}
```

**Response Success (200):**
```json
{
  "resultCode": "00152",
  "resultMessage": {
    "en": "Recipe updated successfully",
    "vn": "Cập nhật công thức thành công"
  },
  "recipe": {
    "id": 1,
    "name": "Perfect Scrambled Eggs",
    "description": "Updated description",
    "servings": 3,
    "prepTime": 5,
    "cookTime": 12,
    "totalTime": 17,
    "updatedAt": "2026-01-02T11:00:00"
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
  "recipeId": 1
}
```

**Response Success (200):**
```json
{
  "resultCode": "00153",
  "resultMessage": {
    "en": "Recipe deleted successfully",
    "vn": "Xóa công thức thành công"
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
| 00150 | Recipes retrieved | Lấy công thức thành công |
| 00151 | Recipe created | Tạo công thức thành công |
| 00152 | Recipe updated | Cập nhật công thức thành công |
| 00153 | Recipe deleted | Xóa công thức thành công |
| 1404 | Recipe not found | Không tìm thấy công thức |
| 1999 | System error | Lỗi hệ thống |

---

## Notes

1. **Authentication**: Tất cả endpoints yêu cầu `Authorization: Bearer {accessToken}`
2. **Difficulty Levels**:
   - `EASY`: Dễ
   - `MEDIUM`: Trung bình
   - `HARD`: Khó
3. **Time Units**: Tất cả thời gian tính bằng phút (minutes)
4. **Total Time**: Tự động tính = prepTime + cookTime
5. **Ingredients**: Danh sách nguyên liệu với số lượng và đơn vị cụ thể
6. **Instructions**: Các bước thực hiện được đánh số thứ tự
7. **Tags**: Hỗ trợ tìm kiếm và phân loại công thức
8. **Main Food**: Thực phẩm chính của công thức (dùng để search recipes by food)
