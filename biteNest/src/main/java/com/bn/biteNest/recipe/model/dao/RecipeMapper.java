package com.bn.biteNest.recipe.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bn.biteNest.recipe.model.dto.RecipeDetailDTO;
import com.bn.biteNest.recipe.model.vo.RecipeVO;

@Mapper
public interface RecipeMapper {
    // 레시피 목록 조회
    List<RecipeVO> getAllRecipes();

    // 레시피 ID로 조회
    RecipeDetailDTO selectRecipeDetailById(int recipeCd);

    // 레시피 추가
    int insertRecipe(RecipeVO recipe);


}
