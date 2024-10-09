package com.bn.biteNest.recipe.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bn.biteNest.recipe.model.vo.IngredientTypeVO;

@Mapper
public interface IngredientTypeMapper {
	int insertIngredientType(IngredientTypeVO ingredientTypeVO);
	List<IngredientTypeVO> selectIngredientsByRecipeCD(int recipeCD);
	IngredientTypeVO selectIngredientTypeByRecipeAndIngredient(@Param("recipeCD") int recipeCD, @Param("ingredientCD")int ingredientCD);
	int updateIngredientType(IngredientTypeVO ingredientType);
	int deleteIngredientTypesByRecipeCD(int recipeCD);
	int deleteIngredientTypeByRecipeCDAndIngredientCD(int recipeCD, int ingredientCD);
}


