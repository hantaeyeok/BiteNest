package com.bn.biteNest.recipe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bn.biteNest.recipe.model.dao.RecipeMapper;
import com.bn.biteNest.recipe.model.vo.RecipeVO;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public List<RecipeVO> getAllRecipes() {
        return recipeMapper.getAllRecipes();
    }

    @Override
    public RecipeVO getRecipeById(int recipeCd) {
        return recipeMapper.getRecipeById(recipeCd);
    }

    @Override
    public void insertRecipe(RecipeVO recipe) {
    	recipeMapper.insertRecipe(recipe);
    }

    @Override
    public void updateRecipe(RecipeVO recipe) {
    	recipeMapper.updateRecipe(recipe);
    }

    @Override
    public void deleteRecipe(int recipeCd) {
    	recipeMapper.deleteRecipe(recipeCd);
    }
}
