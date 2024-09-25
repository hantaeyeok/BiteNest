package com.bn.biteNest.recipe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bn.biteNest.recipe.model.dao.IngredientTypeMapper;
import com.bn.biteNest.recipe.model.vo.IngredientTypeVO;

@Service
public class IngredientTypeServiceImpl implements IngredientTypeService {

    @Autowired
    private IngredientTypeMapper ingredientTypeMapper;

    @Override
    public List<IngredientTypeVO> getAllIngredientTypes() {
        return ingredientTypeMapper.getAllIngredientTypes();
    }

    @Override
    public IngredientTypeVO getIngredientTypeById(int recipeCD, int ingredientCD) {
        return ingredientTypeMapper.getIngredientTypeById(recipeCD, ingredientCD);
    }

    @Override
    public void insertIngredientType(IngredientTypeVO ingredientType) {
        ingredientTypeMapper.insertIngredientType(ingredientType);
    }

    @Override
    public void updateIngredientType(IngredientTypeVO ingredientType) {
        ingredientTypeMapper.updateIngredientType(ingredientType);
    }

    @Override
    public void deleteIngredientType(int recipeCD, int ingredientCD) {
        ingredientTypeMapper.deleteIngredientType(recipeCD, ingredientCD);
    }
}