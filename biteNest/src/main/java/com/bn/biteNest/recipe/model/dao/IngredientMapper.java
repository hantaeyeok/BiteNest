package com.bn.biteNest.recipe.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.bn.biteNest.recipe.model.vo.IngredientVO;

@Mapper
public interface IngredientMapper {

	IngredientVO selectByName(String ingredientName);    
    int insertIngredient(IngredientVO newIngredient);

}
