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
    public int insertRecipeStep(RecipeStepVO step) {
        return recipeStepMapper.insertRecipeStep(step);
    }


}
