package com.bn.biteNest.recipe.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bn.biteNest.recipe.model.vo.RecipeVO;

@Mapper
public interface RecipeMapper {
    // ������ ��� ��ȸ
    List<RecipeVO> getAllRecipes();

    // ������ ID�� ��ȸ
    RecipeVO getRecipeById(int recipeCd);

    // ������ �߰�
    void insertRecipe(RecipeVO recipe);

    // ������ ����
    void updateRecipe(RecipeVO recipe);

    // ������ ����
    void deleteRecipe(int recipeCd);
}
