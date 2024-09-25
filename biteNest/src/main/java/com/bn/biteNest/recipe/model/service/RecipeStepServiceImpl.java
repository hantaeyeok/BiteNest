package com.bn.biteNest.recipe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bn.biteNest.recipe.model.dao.RecipeStepMapper;
import com.bn.biteNest.recipe.model.vo.RecipeStepVO;

@Service
public class RecipeStepServiceImpl implements RecipeStepService {

    @Autowired
    private RecipeStepMapper recipeStepMapper;

    @Override
    public List<RecipeStepVO> getAllRecipeSteps(int recipeCD) {
        return recipeStepMapper.getAllRecipeSteps(recipeCD);
    }

    @Override
    public RecipeStepVO getRecipeStepById(int stepCD) {
        return recipeStepMapper.getRecipeStepById(stepCD);
    }

    @Override
    public void insertRecipeStep(RecipeStepVO step) {
        recipeStepMapper.insertRecipeStep(step);
    }

    @Override
    public void updateRecipeStep(RecipeStepVO step) {
        recipeStepMapper.updateRecipeStep(step);
    }

    @Override
    public void deleteRecipeStep(int stepCD) {
        recipeStepMapper.deleteRecipeStep(stepCD);
    }
}
