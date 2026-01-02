package com.example.smartShopping.service;

import com.example.smartShopping.dto.request.UnitRequest;
import com.example.smartShopping.dto.response.*;

public interface UnitService {
    UnitCreateResponse createUnit(UnitRequest request);
    UnitResponse getAllUnits(String unitName);
    UnitUpdateResponse updateUnitName(String oldName, String newName);
    UnitDeleteResponse deleteUnitByName(String unitName);
    UnitResponse getAllUnitsFood(String unitName);
}
