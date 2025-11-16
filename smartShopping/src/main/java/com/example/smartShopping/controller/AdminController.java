package com.example.smartShopping.controller;


import com.example.smartShopping.dto.request.*;
import com.example.smartShopping.dto.response.*;

import com.example.smartShopping.configuration.*;
import com.example.smartShopping.entity.GroupEntity;
import com.example.smartShopping.entity.User;
import com.example.smartShopping.service.AuthService;
import com.example.smartShopping.service.UserService;
import com.example.smartShopping.service.GroupService;

import com.example.smartShopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static java.util.Map.entry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


