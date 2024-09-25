package com.bn.biteNest.recipe.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bn.biteNest.recipe.model.vo.RecipeVO;

@Mapper
public interface RecipeMapper {
    // 레시피 목록 조회
    List<RecipeVO> getAllRecipes();

    // 레시피 ID로 조회
    RecipeVO getRecipeById(int recipeCd);

    // 레시피 추가
    void insertRecipe(RecipeVO recipe);

    // 레시피 수정
    void updateRecipe(RecipeVO recipe);

    // 레시피 삭제
    void deleteRecipe(int recipeCd);
}
