package com.example.smartShopping.service.impl;

import com.example.smartShopping.dto.request.UnitRequest;
import com.example.smartShopping.dto.response.ApiResponse;
import com.example.smartShopping.dto.response.UnitResponse;
import com.example.smartShopping.entity.Unit;
import com.example.smartShopping.repository.UnitRepository;
import com.example.smartShopping.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    @Override
    public ApiResponse createUnit(UnitRequest request) {

        if (unitRepository.findByUnitName(request.getUnitName()).isPresent()) {
            return ApiResponse.builder()
                    .success(false)
                    .code(409)
                    .message("Unit already exists")
                    .data(null)
                    .build();
        }

        Unit unit = Unit.builder()
                .unitName(request.getUnitName())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        unitRepository.save(unit);

        UnitResponse response = new UnitResponse();
        response.setId(unit.getId());
        response.setUnitName(unit.getUnitName());
        response.setCreatedAt(unit.getCreatedAt().toString());
        response.setUpdatedAt(unit.getUpdatedAt().toString());

        return ApiResponse.builder()
                .success(true)
                .code(116)  // resultCode: 00116
                .message("Tạo đơn vị thành công")
                .data(response)
                .build();
    }
    @Override
    public ApiResponse getAllUnits(String unitName) {
        List<Unit> units;

        if (unitName != null && !unitName.isBlank()) {
            units = unitRepository.findByUnitNameContainingIgnoreCase(unitName)
                    .stream().toList();
        } else {
            units = unitRepository.findAll();
        }

        List<UnitResponse> unitResponses = units.stream().map(unit -> {
            UnitResponse response = new UnitResponse();
            response.setId(unit.getId());
            response.setUnitName(unit.getUnitName());
            response.setCreatedAt(unit.getCreatedAt().toString());
            response.setUpdatedAt(unit.getUpdatedAt().toString());
            return response;
        }).collect(Collectors.toList());

        return ApiResponse.builder()
                .success(true)
                .code(110)  // resultCode: 00110
                .message("Lấy các unit thành công")
                .data(unitResponses)
                .build();
    }
    @Override
    public ApiResponse updateUnitName(String oldName, String newName) {
        Unit unit = unitRepository.findByUnitName(oldName)
                .orElse(null);

        if (unit == null) {
            return ApiResponse.builder()
                    .success(false)
                    .code(404)
                    .message("Unit not found")
                    .data(null)
                    .build();
        }

        // Kiểm tra trùng newName
        if (unitRepository.findByUnitName(newName).isPresent()) {
            return ApiResponse.builder()
                    .success(false)
                    .code(409)
                    .message("Unit name already exists")
                    .data(null)
                    .build();
        }

        unit.setUnitName(newName);
        unit.setUpdatedAt(java.time.LocalDateTime.now());
        unitRepository.save(unit);

        return ApiResponse.builder()
                .success(true)
                .code(122)  // resultCode: 00122
                .message("Sửa đổi đơn vị thành công")
                .data(null)
                .build();
    }
    @Override
    public ApiResponse deleteUnitByName(String unitName) {
        Unit unit = unitRepository.findByUnitName(unitName)
                .orElse(null);

        if (unit == null) {
            return ApiResponse.builder()
                    .success(false)
                    .code(404)
                    .message("Unit not found")
                    .data(null)
                    .build();
        }

        unitRepository.delete(unit);

        return ApiResponse.builder()
                .success(true)
                .code(128)  // resultCode: 00128
                .message("Xóa đơn vị thành công")
                .data(null)
                .build();
    }
}
