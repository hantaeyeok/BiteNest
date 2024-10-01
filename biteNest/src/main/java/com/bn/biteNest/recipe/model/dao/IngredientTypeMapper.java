package com.bn.biteNest.recipe.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.bn.biteNest.recipe.model.vo.IngredientTypeVO;

@Mapper
public interface IngredientTypeMapper {

	int insertIngredientType(IngredientTypeVO ingredientTypeVO);
}
