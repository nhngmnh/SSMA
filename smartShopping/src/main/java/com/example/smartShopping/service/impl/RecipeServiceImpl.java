package com.example.smartShopping.service.impl;

import com.example.smartShopping.dto.request.DeleteRecipeRequest;
import com.example.smartShopping.dto.request.RecipeRequest;
import com.example.smartShopping.dto.request.UpdateRecipeRequest;
import com.example.smartShopping.dto.response.GetRecipesResponse;
import com.example.smartShopping.dto.response.RecipeDeleteResponse;
import com.example.smartShopping.dto.response.RecipeResponse;
import com.example.smartShopping.dto.response.RecipeUpdateResponse;
import com.example.smartShopping.entity.Food;
import com.example.smartShopping.entity.Recipe;
import com.example.smartShopping.repository.FoodRepository;
import com.example.smartShopping.repository.RecipeRepository;
import com.example.smartShopping.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final FoodRepository foodRepository;

    @Override
    public RecipeResponse createRecipe(RecipeRequest request) {
        try {
            // Validate food exists
            Food food = foodRepository.findById(request.getFoodId())
                    .orElseThrow(() -> new RuntimeException("Food not found with id: " + request.getFoodId()));

            // Create timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Create recipe entity
            Recipe recipe = Recipe.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .htmlContent(request.getHtmlContent())
                    .foodId(request.getFoodId())
                    .createdAt(timestamp)
                    .updatedAt(timestamp)
                    .build();

            // Save recipe
            Recipe savedRecipe = recipeRepository.save(recipe);

            // Build NewRecipe response with flattened Food fields
            RecipeResponse.NewRecipe newRecipe = RecipeResponse.NewRecipe.builder()
                    .id(savedRecipe.getId())
                    .name(savedRecipe.getName())
                    .description(savedRecipe.getDescription())
                    .htmlContent(savedRecipe.getHtmlContent())
                    .createdAt(savedRecipe.getCreatedAt())
                    .updatedAt(savedRecipe.getUpdatedAt())
                    .FoodId(savedRecipe.getFoodId())
                    // Flattened Food fields with dot notation
                    .foodDotId(food.getId())
                    .foodDotName(food.getName())
                    .foodDotImageUrl(food.getImageUrl())
                    .foodDotType(food.getType())
                    .foodDotCreatedAt(food.getCreatedAt())
                    .foodDotUpdatedAt(food.getUpdatedAt())
                    .foodDotFoodCategoryId(food.getFoodCategoryId())
                    .foodDotUserId(food.getUserId())
                    .foodDotUnitOfMeasurementId(food.getUnitOfMeasurementId())
                    .build();

            // Build and return response
            return RecipeResponse.builder()
                    .resultCode("00357")
                    .resultMessage(RecipeResponse.ResultMessage.builder()
                            .en("Add recipe successfull")
                            .vn("Thêm công thức nấu ăn thành công")
                            .build())
                    .newRecipe(newRecipe)
                    .build();

        } catch (RuntimeException e) {
            return RecipeResponse.builder()
                    .resultCode("1999")
                    .resultMessage(RecipeResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    public RecipeUpdateResponse updateRecipe(UpdateRecipeRequest request) {
        try {
            // Find recipe
            Recipe recipe = recipeRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + request.getId()));

            // Update fields if provided
            if (request.getName() != null && !request.getName().isBlank()) {
                recipe.setName(request.getName());
            }
            if (request.getDescription() != null && !request.getDescription().isBlank()) {
                recipe.setDescription(request.getDescription());
            }
            if (request.getHtmlContent() != null && !request.getHtmlContent().isBlank()) {
                recipe.setHtmlContent(request.getHtmlContent());
            }
            if (request.getFoodId() != null) {
                // Validate food exists
                Food food = foodRepository.findById(request.getFoodId())
                        .orElseThrow(() -> new RuntimeException("Food not found with id: " + request.getFoodId()));
                recipe.setFoodId(request.getFoodId());
            }

            // Update timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            recipe.setUpdatedAt(timestamp);

            // Save recipe
            Recipe updatedRecipe = recipeRepository.save(recipe);

            // Get food details
            Food food = foodRepository.findById(updatedRecipe.getFoodId())
                    .orElse(null);

            // Build response
            RecipeUpdateResponse.UpdatedRecipe.UpdatedRecipeBuilder recipeBuilder = RecipeUpdateResponse.UpdatedRecipe.builder()
                    .id(updatedRecipe.getId())
                    .name(updatedRecipe.getName())
                    .description(updatedRecipe.getDescription())
                    .htmlContent(updatedRecipe.getHtmlContent())
                    .createdAt(updatedRecipe.getCreatedAt())
                    .updatedAt(updatedRecipe.getUpdatedAt())
                    .FoodId(updatedRecipe.getFoodId());

            if (food != null) {
                recipeBuilder
                        .foodDotId(food.getId())
                        .foodDotName(food.getName())
                        .foodDotImageUrl(food.getImageUrl())
                        .foodDotType(food.getType())
                        .foodDotCreatedAt(food.getCreatedAt())
                        .foodDotUpdatedAt(food.getUpdatedAt())
                        .foodDotFoodCategoryId(food.getFoodCategoryId())
                        .foodDotUserId(food.getUserId())
                        .foodDotUnitOfMeasurementId(food.getUnitOfMeasurementId());
            }

            return RecipeUpdateResponse.builder()
                    .resultCode("00358")
                    .resultMessage(RecipeUpdateResponse.ResultMessage.builder()
                            .en("Update recipe successful")
                            .vn("Cập nhật công thức nấu ăn thành công")
                            .build())
                    .updatedRecipe(recipeBuilder.build())
                    .build();

        } catch (RuntimeException e) {
            return RecipeUpdateResponse.builder()
                    .resultCode("1999")
                    .resultMessage(RecipeUpdateResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    public RecipeDeleteResponse deleteRecipe(DeleteRecipeRequest request) {
        try {
            Recipe recipe = recipeRepository.findById(request.getRecipeId())
                    .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + request.getRecipeId()));

            recipeRepository.delete(recipe);

            return RecipeDeleteResponse.builder()
                    .resultCode("00359")
                    .resultMessage(RecipeDeleteResponse.ResultMessage.builder()
                            .en("Delete recipe successful")
                            .vn("Xóa công thức nấu ăn thành công")
                            .build())
                    .build();

        } catch (RuntimeException e) {
            return RecipeDeleteResponse.builder()
                    .resultCode("1999")
                    .resultMessage(RecipeDeleteResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }
    @Override
    public GetRecipesResponse getRecipesByFoodId(Long foodId) {
        try {
            List<Recipe> recipes = recipeRepository.findByFoodId(foodId);
            
            List<GetRecipesResponse.RecipeDto> recipeDtos = recipes.stream()
                    .map(recipe -> GetRecipesResponse.RecipeDto.builder()
                            .id(recipe.getId())
                            .name(recipe.getName())
                            .description(recipe.getDescription())
                            .htmlContent(recipe.getHtmlContent())
                            .createdAt(recipe.getCreatedAt())
                            .updatedAt(recipe.getUpdatedAt())
                            .FoodId(recipe.getFoodId())
                            .build())
                    .collect(Collectors.toList());

            return GetRecipesResponse.builder()
                    .resultCode("00360")
                    .resultMessage(GetRecipesResponse.ResultMessage.builder()
                            .en("Get recipes successful")
                            .vn("Lấy danh sách công thức thành công")
                            .build())
                    .recipes(recipeDtos)
                    .build();

        } catch (RuntimeException e) {
            return GetRecipesResponse.builder()
                    .resultCode("1999")
                    .resultMessage(GetRecipesResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }}
