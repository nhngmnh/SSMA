package com.example.smartShopping.service;

import com.example.smartShopping.dto.request.UnitRequest;
import com.example.smartShopping.dto.response.ApiResponse;

public interface UnitService {
    ApiResponse createUnit(UnitRequest request);
    ApiResponse getAllUnits(String unitName); // unitName có thể null
    ApiResponse updateUnitName(String oldName, String newName);
    ApiResponse deleteUnitByName(String unitName);
}
