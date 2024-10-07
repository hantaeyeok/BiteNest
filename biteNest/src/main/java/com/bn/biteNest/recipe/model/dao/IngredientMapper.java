package com.bn.biteNest.recipe.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bn.biteNest.recipe.model.vo.IngredientVO;

@Mapper
public interface IngredientMapper {

	IngredientVO selectByName(String ingredientName);    
    //int insertIngredient(IngredientVO newIngredient);
	int deleteIngredientsByCD(List<Integer> unusedIngredientCD);
	int deleteIngredientByCD(int ingredientCD);
	int countRecipesUsingIngredient(int recipeCD, int ingredientCD);
	int mergeIngredient(IngredientVO ingredient);

}
