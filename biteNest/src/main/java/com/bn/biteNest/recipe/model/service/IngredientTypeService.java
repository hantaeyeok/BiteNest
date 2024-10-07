package com.bn.biteNest.recipe.model.service;

import java.util.List;
import com.bn.biteNest.recipe.model.vo.IngredientTypeVO;

public interface IngredientTypeService {
	boolean insertIngredientType(IngredientTypeVO ingredientTypeVO);
	List<IngredientTypeVO> getIngredientsByRecipeCD(int recipeCD);
	boolean updateIngredientTypesByRecipe(int recipeCD, List<IngredientTypeVO> ingredientTypeList);
	boolean deleteIngredientTypesByRecipeCD(int recipeCD);
	boolean deleteIngredientTypeByRecipeCDAndIngredientCD(int recipeCD, int ingredientCD);
}
