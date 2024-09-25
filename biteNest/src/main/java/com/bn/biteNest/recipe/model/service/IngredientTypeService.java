package com.bn.biteNest.recipe.model.service;

import java.util.List;

import com.bn.biteNest.recipe.model.vo.IngredientTypeVO;

public interface IngredientTypeService {
    List<IngredientTypeVO> getAllIngredientTypes();
    IngredientTypeVO getIngredientTypeById(int recipeCD, int ingredientCD);
    void insertIngredientType(IngredientTypeVO ingredientType);
    void updateIngredientType(IngredientTypeVO ingredientType);
    void deleteIngredientType(int recipeCD, int ingredientCD);
}
