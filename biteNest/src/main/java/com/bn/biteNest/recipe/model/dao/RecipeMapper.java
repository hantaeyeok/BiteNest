package com.bn.biteNest.recipe.model.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.bn.biteNest.recipe.model.dto.RecipeDetailDTO;
import com.bn.biteNest.recipe.model.vo.RecipeVO;

@Mapper
public interface RecipeMapper {

    Optional<RecipeDetailDTO> selectRecipeDetailByCD(int recipeCd);
    int insertRecipe(RecipeVO recipe);
	int updateRecipe(RecipeVO recipeVO);
	int deleteRecipeByCD(int recipeCD);

	
    
    
}
