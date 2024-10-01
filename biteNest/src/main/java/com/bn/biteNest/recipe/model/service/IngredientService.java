package com.bn.biteNest.recipe.model.service;

import java.util.Optional;

import com.bn.biteNest.recipe.model.vo.IngredientVO;

public interface IngredientService {
    
	Optional<IngredientVO> findByName(String ingredientName);
    int saveNewIngredient(String ingredientName);

}
