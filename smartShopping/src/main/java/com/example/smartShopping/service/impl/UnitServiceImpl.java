package com.example.smartShopping.service.impl;

import com.example.smartShopping.dto.request.UnitRequest;
import com.example.smartShopping.dto.response.*;
import com.example.smartShopping.entity.Unit;
import com.example.smartShopping.repository.UnitRepository;
import com.example.smartShopping.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    @Override
    public UnitCreateResponse createUnit(UnitRequest request) {
        try {
            Unit unit = Unit.builder()
                    .unitName(request.getUnitName())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            unitRepository.save(unit);

            UnitCreateResponse.UnitDto dto = UnitCreateResponse.UnitDto.builder()
                    .id(unit.getId())
                    .unitName(unit.getUnitName())
                    .createdAt(unit.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .updatedAt(unit.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .build();

            return UnitCreateResponse.builder()
                    .resultCode("00116")
                    .resultMessage(UnitCreateResponse.ResultMessage.builder()
                            .en("Unit created successfully")
                            .vn("Tạo đơn vị thành công")
                            .build())
                    .newUnit(dto)
                    .build();

        } catch (RuntimeException e) {
            return UnitCreateResponse.builder()
                    .resultCode("1999")
                    .resultMessage(UnitCreateResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    public UnitResponse getAllUnits(String unitName) {
        try {
            List<Unit> units;

            if (unitName != null && !unitName.isBlank()) {
                units = unitRepository.findByUnitNameContainingIgnoreCase(unitName)
                        .stream().toList();
            } else {
                units = unitRepository.findAll();
            }

            List<UnitResponse.UnitDto> unitDtos = units.stream()
                    .map(unit -> UnitResponse.UnitDto.builder()
                            .id(unit.getId())
                            .unitName(unit.getUnitName())
                            .createdAt(unit.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .updatedAt(unit.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .build())
                    .collect(Collectors.toList());

            return UnitResponse.builder()
                    .resultCode("00110")
                    .resultMessage(UnitResponse.ResultMessage.builder()
                            .en("Get units successfully")
                            .vn("Lấy các unit thành công")
                            .build())
                    .units(unitDtos)
                    .build();

        } catch (RuntimeException e) {
            return UnitResponse.builder()
                    .resultCode("1999")
                    .resultMessage(UnitResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    public UnitUpdateResponse updateUnitName(String oldName, String newName) {
        try {
            Unit unit = unitRepository.findByUnitName(oldName)
                    .orElseThrow(() -> new RuntimeException("Unit not found"));

            unit.setUnitName(newName);
            unit.setUpdatedAt(LocalDateTime.now());
            unitRepository.save(unit);

            return UnitUpdateResponse.builder()
                    .resultCode("00122")
                    .resultMessage(UnitUpdateResponse.ResultMessage.builder()
                            .en("Unit updated successfully")
                            .vn("Sửa đổi đơn vị thành công")
                            .build())
                    .build();

        } catch (RuntimeException e) {
            return UnitUpdateResponse.builder()
                    .resultCode("1999")
                    .resultMessage(UnitUpdateResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    public UnitDeleteResponse deleteUnitByName(String unitName) {
        try {
            Unit unit = unitRepository.findByUnitName(unitName)
                    .orElseThrow(() -> new RuntimeException("Unit not found"));

            unitRepository.delete(unit);

            return UnitDeleteResponse.builder()
                    .resultCode("00128")
                    .resultMessage(UnitDeleteResponse.ResultMessage.builder()
                            .en("Unit deleted successfully")
                            .vn("Xóa đơn vị thành công")
                            .build())
                    .build();

        } catch (RuntimeException e) {
            return UnitDeleteResponse.builder()
                    .resultCode("1999")
                    .resultMessage(UnitDeleteResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    public UnitResponse getAllUnitsFood(String unitName) {
        try {
            List<UnitResponse.UnitDto> unitDtos = unitRepository.findAll()
                    .stream()
                    .filter(u -> unitName == null || u.getUnitName().contains(unitName))
                    .map(unit -> UnitResponse.UnitDto.builder()
                            .id(unit.getId())
                            .unitName(unit.getUnitName())
                            .createdAt(unit.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .updatedAt(unit.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .build())
                    .collect(Collectors.toList());

            return UnitResponse.builder()
                    .resultCode("00110")
                    .resultMessage(UnitResponse.ResultMessage.builder()
                            .en("Successfully retrieved units")
                            .vn("Lấy các unit thành công")
                            .build())
                    .units(unitDtos)
                    .build();

        } catch (RuntimeException e) {
            return UnitResponse.builder()
                    .resultCode("1999")
                    .resultMessage(UnitResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }
}
