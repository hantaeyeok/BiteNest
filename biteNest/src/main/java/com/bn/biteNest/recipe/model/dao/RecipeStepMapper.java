package com.bn.biteNest.recipe.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bn.biteNest.recipe.model.vo.RecipeStepVO;

@Mapper
public interface RecipeStepMapper {
	int insertRecipeStep(RecipeStepVO step);
	int updateRecipeStep(RecipeStepVO stepVO);
	List<RecipeStepVO> selectRecipeStepByRecipeCD(int recipeCD);
	int deleteStepsByRecipeCD(int recipeCD);
}




