package com.bn.biteNest.recipe.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bn.biteNest.recipe.model.vo.IngredientTypeVO;

@Mapper
public interface IngredientTypeMapper {
    List<IngredientTypeVO> getAllIngredientTypes();
    IngredientTypeVO getIngredientTypeById(int recipeCD, int ingredientCD);
    void insertIngredientType(IngredientTypeVO ingredientType);
    void updateIngredientType(IngredientTypeVO ingredientType);
    void deleteIngredientType(int recipeCD, int ingredientCD);
}
