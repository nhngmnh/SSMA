package com.example.smartShopping.service.impl;

import com.example.smartShopping.dto.response.ApiResponse;
import com.example.smartShopping.dto.response.LogResponse;
import com.example.smartShopping.entity.Log;
import com.example.smartShopping.repository.LogRepository;
import com.example.smartShopping.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    @Override
    public ApiResponse getAllLogs() {
        List<LogResponse> logs = logRepository.findAll()
                .stream()
                .map(log -> {
                    LogResponse dto = new LogResponse();
                    dto.setId(log.getId());
                    dto.setUserId(log.getUserId());
                    dto.setResultCode(log.getResultCode());
                    dto.setLevel(log.getLevel());
                    dto.setErrorMessage(log.getErrorMessage());
                    dto.setIp(log.getIp());
                    dto.setCreatedAt(log.getCreatedAt().toString());
                    dto.setUpdatedAt(log.getUpdatedAt().toString());
                    return dto;
                })
                .collect(Collectors.toList());

        return ApiResponse.builder()
                .success(true)
                .code(109) // resultCode: 00109
                .message("Lấy log hệ thống thành công")
                .data(logs)
                .build();
    }
}
