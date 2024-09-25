package com.bn.biteNest.recipe.model.service;

import java.util.List;

import com.bn.biteNest.recipe.model.vo.RecipeVO;

public interface RecipeService {
    // ��� ������ ��ȸ
    List<RecipeVO> getAllRecipes();

    // ID�� ������ ��ȸ
    RecipeVO getRecipeById(int recipeCd);

    // ������ �߰�
    void insertRecipe(RecipeVO recipe);

    // ������ ����
    void updateRecipe(RecipeVO recipe);

    // ������ ����
    void deleteRecipe(int recipeCd);
}
