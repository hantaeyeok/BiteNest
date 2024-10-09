package com.bn.biteNest.recipe.model.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.bn.biteNest.recipe.model.vo.IngredientVO;

public interface IngredientService {
    
	Optional<IngredientVO> findByName(String ingredientName);
    int saveNewIngredient(String ingredientName);
    int countRecipesUsingIngredient(int recipeCD, int ingredientCD);
	boolean updateIngredientsByRecipe(int recipeCD, List<Map<String, String>> ingredients);
	boolean deleteIngredientByCD(int ingredientCD);
	

}
