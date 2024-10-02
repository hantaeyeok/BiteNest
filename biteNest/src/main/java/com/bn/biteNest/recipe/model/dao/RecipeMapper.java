package com.bn.biteNest.recipe.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bn.biteNest.recipe.model.dto.RecipeDetailDTO;
import com.bn.biteNest.recipe.model.vo.RecipeVO;

@Mapper
public interface RecipeMapper {
    // ������ ��� ��ȸ
    List<RecipeVO> getAllRecipes();

    // ������ ID�� ��ȸ
    RecipeDetailDTO selectRecipeDetailById(int recipeCd);

    // ������ �߰�
    int insertRecipe(RecipeVO recipe);


}
