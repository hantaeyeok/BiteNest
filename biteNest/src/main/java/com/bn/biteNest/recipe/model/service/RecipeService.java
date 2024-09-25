package com.bn.biteNest.recipe.model.service;

import java.util.List;

import com.bn.biteNest.recipe.model.vo.RecipeVO;

public interface RecipeService {
    // 모든 레시피 조회
    List<RecipeVO> getAllRecipes();

    // ID로 레시피 조회
    RecipeVO getRecipeById(int recipeCd);

    // 레시피 추가
    void insertRecipe(RecipeVO recipe);

    // 레시피 수정
    void updateRecipe(RecipeVO recipe);

    // 레시피 삭제
    void deleteRecipe(int recipeCd);
}
