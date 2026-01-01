package com.example.smartShopping.controller;

import com.example.smartShopping.dto.request.DeleteRecipeRequest;
import com.example.smartShopping.dto.request.RecipeRequest;
import com.example.smartShopping.dto.request.UpdateRecipeRequest;
import com.example.smartShopping.dto.response.GetRecipesResponse;
import com.example.smartShopping.dto.response.RecipeDeleteResponse;
import com.example.smartShopping.dto.response.RecipeResponse;
import com.example.smartShopping.dto.response.RecipeUpdateResponse;
import com.example.smartShopping.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipeController {
    
    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<GetRecipesResponse> getRecipesByFoodId(@RequestParam Long foodId) {
        try {
            GetRecipesResponse response = recipeService.getRecipesByFoodId(foodId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(
                    GetRecipesResponse.builder()
                            .resultCode("1999")
                            .resultMessage(GetRecipesResponse.ResultMessage.builder()
                                    .en("System error")
                                    .vn("Lỗi hệ thống")
                                    .build())
                            .build()
            );
        }
    }

    @PostMapping
    public ResponseEntity<RecipeResponse> createRecipe(@RequestBody RecipeRequest request) {
        try {
            RecipeResponse response = recipeService.createRecipe(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(
                    RecipeResponse.builder()
                            .resultCode("1999")
                            .resultMessage(RecipeResponse.ResultMessage.builder()
                                    .en("System error")
                                    .vn("Lỗi hệ thống")
                                    .build())
                            .build()
            );
        }
    }

    @PutMapping
    public ResponseEntity<RecipeUpdateResponse> updateRecipe(@RequestBody UpdateRecipeRequest request) {
        try {
            RecipeUpdateResponse response = recipeService.updateRecipe(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(
                    RecipeUpdateResponse.builder()
                            .resultCode("1999")
                            .resultMessage(RecipeUpdateResponse.ResultMessage.builder()
                                    .en("System error")
                                    .vn("Lỗi hệ thống")
                                    .build())
                            .build()
            );
        }
    }

    @DeleteMapping
    public ResponseEntity<RecipeDeleteResponse> deleteRecipe(@RequestBody DeleteRecipeRequest request) {
        try {
            RecipeDeleteResponse response = recipeService.deleteRecipe(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(
                    RecipeDeleteResponse.builder()
                            .resultCode("1999")
                            .resultMessage(RecipeDeleteResponse.ResultMessage.builder()
                                    .en("System error")
                                    .vn("Lỗi hệ thống")
                                    .build())
                            .build()
            );
        }
    }
}
