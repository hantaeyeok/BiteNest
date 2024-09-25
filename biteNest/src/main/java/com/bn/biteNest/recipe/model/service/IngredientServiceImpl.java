package com.bn.biteNest.recipe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bn.biteNest.recipe.model.dao.IngredientMapper;
import com.bn.biteNest.recipe.model.vo.IngredientVO;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientMapper ingredientMapper;

    @Override
    public List<IngredientVO> getAllIngredients() {
        return ingredientMapper.getAllIngredients();
    }
    
    @Override
	public IngredientVO findByName(String ingredientName) {
		return ingredientMapper.findByName(ingredientName);
	}



	@Override
    public IngredientVO getIngredientById(int ingredientCD) {
        return ingredientMapper.getIngredientById(ingredientCD);
    }

    @Override
    public void insertIngredient(IngredientVO ingredient) {
        ingredientMapper.insertIngredient(ingredient);
    }

    @Override
    public void updateIngredient(IngredientVO ingredient) {
        ingredientMapper.updateIngredient(ingredient);
    }

    @Override
    public void deleteIngredient(int ingredientCD) {
        ingredientMapper.deleteIngredient(ingredientCD);
    }
}