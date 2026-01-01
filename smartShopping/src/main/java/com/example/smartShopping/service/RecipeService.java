package com.example.smartShopping.service;

import com.example.smartShopping.dto.request.DeleteRecipeRequest;
import com.example.smartShopping.dto.request.RecipeRequest;
import com.example.smartShopping.dto.request.UpdateRecipeRequest;
import com.example.smartShopping.dto.response.GetRecipesResponse;
import com.example.smartShopping.dto.response.RecipeDeleteResponse;
import com.example.smartShopping.dto.response.RecipeResponse;
import com.example.smartShopping.dto.response.RecipeUpdateResponse;

public interface RecipeService {
    RecipeResponse createRecipe(RecipeRequest request);
    RecipeUpdateResponse updateRecipe(UpdateRecipeRequest request);
    RecipeDeleteResponse deleteRecipe(DeleteRecipeRequest request);
    GetRecipesResponse getRecipesByFoodId(Long foodId);
}
