package com.example.smartShopping.entity;

public enum FoodType {
    MEAT_SEAFOOD("Thịt & Hải sản"),
    VEGETABLES("Rau củ"),
    FRUITS("Trái cây"),
    DAIRY_EGGS("Sữa & Trứng"),
    OTHER("Khác");

    private final String displayName;

    FoodType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Tìm FoodType từ display name (Vietnamese)
     */
    public static FoodType fromDisplayName(String displayName) {
        for (FoodType type : FoodType.values()) {
            if (type.displayName.equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        return OTHER; // Default nếu không tìm thấy
    }

    /**
     * Kiểm tra string có phải là valid food type không
     */
    public static boolean isValid(String displayName) {
        for (FoodType type : FoodType.values()) {
            if (type.displayName.equalsIgnoreCase(displayName)) {
                return true;
            }
        }
        return false;
    }
}
