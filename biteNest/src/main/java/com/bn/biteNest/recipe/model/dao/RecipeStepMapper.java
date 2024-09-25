package com.bn.biteNest.recipe.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bn.biteNest.recipe.model.vo.RecipeStepVO;

@Mapper
public interface RecipeStepMapper {
    List<RecipeStepVO> getAllRecipeSteps(int recipeCD);
    RecipeStepVO getRecipeStepById(int stepCD);
    void insertRecipeStep(RecipeStepVO step);
    void updateRecipeStep(RecipeStepVO step);
    void deleteRecipeStep(int stepCD);
}
