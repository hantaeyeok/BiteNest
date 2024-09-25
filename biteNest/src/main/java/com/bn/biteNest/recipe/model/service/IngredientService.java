package com.bn.biteNest.recipe.model.service;

import java.util.List;

import com.bn.biteNest.recipe.model.vo.IngredientVO;

public interface IngredientService {
    List<IngredientVO> getAllIngredients();
    IngredientVO getIngredientById(int ingredientCD);
    IngredientVO findByName(String ingredientName);
    
    
    void insertIngredient(IngredientVO ingredient);
    void updateIngredient(IngredientVO ingredient);
    void deleteIngredient(int ingredientCD);
}
