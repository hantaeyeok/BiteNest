package com.bn.biteNest.recipe.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bn.biteNest.recipe.model.vo.IngredientVO;

@Mapper
public interface IngredientMapper {
    List<IngredientVO> getAllIngredients();
    IngredientVO getIngredientById(int ingredientCD);
    IngredientVO findByName(String ingredientName);
    
    void insertIngredient(IngredientVO ingredient);
    void updateIngredient(IngredientVO ingredient);
    void deleteIngredient(int ingredientCD);
}
