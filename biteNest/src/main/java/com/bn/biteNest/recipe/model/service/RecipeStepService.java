package com.bn.biteNest.recipe.model.service;

import java.util.List;

import com.bn.biteNest.recipe.model.vo.RecipeStepVO;

public interface RecipeStepService {
    List<RecipeStepVO> getAllRecipeSteps(int recipeCD);
    RecipeStepVO getRecipeStepById(int stepCD);
    void insertRecipeStep(RecipeStepVO step);
    void updateRecipeStep(RecipeStepVO step);
    void deleteRecipeStep(int stepCD);
}
