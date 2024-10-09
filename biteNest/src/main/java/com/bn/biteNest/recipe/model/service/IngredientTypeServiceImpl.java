package com.bn.biteNest.recipe.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bn.biteNest.recipe.model.dao.IngredientTypeMapper;
import com.bn.biteNest.recipe.model.vo.IngredientTypeVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
 
@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientTypeServiceImpl implements IngredientTypeService {

    private final IngredientTypeMapper ingredientTypeMapper;

    //재료 타입 삽입
    @Override
    public boolean insertIngredientType(IngredientTypeVO ingredientTypeVO) {
        return ingredientTypeMapper.insertIngredientType(ingredientTypeVO) > 0;
    }
    // 레시피 CD로 재료 타입 목록 조회
	@Override
	public List<IngredientTypeVO> getIngredientsByRecipeCD(int recipeCD) {
        return ingredientTypeMapper.selectIngredientsByRecipeCD(recipeCD);
	}
	 
	@Override
    public boolean updateIngredientTypesByRecipe(int recipeCD, List<IngredientTypeVO> ingredientTypeList) {
        List<IngredientTypeVO> existingTypes = ingredientTypeMapper.selectIngredientsByRecipeCD(recipeCD);

        for (IngredientTypeVO ingredientType : ingredientTypeList) {// 새 재료 목록을 업데이트 또는 추가
            ingredientType.setRecipeCD(recipeCD);
            IngredientTypeVO existingType = ingredientTypeMapper.selectIngredientTypeByRecipeAndIngredient( // 기존 재료 타입 조회
            		recipeCD, ingredientType.getIngredientCD());
            if (existingType != null) {
            	ingredientTypeMapper.updateIngredientType(ingredientType);
            } else {
            	ingredientTypeMapper.insertIngredientType(ingredientType);// 새로운 재료 타입 추가
            }
        }

        // 기존 재료 목록 중에서 삭제할 재료 식별 및 삭제
        List<Integer> newIngredientCDs = new ArrayList<>();
        for (IngredientTypeVO ingredientType : ingredientTypeList) {
            newIngredientCDs.add(ingredientType.getIngredientCD());
        }
        
        // 기존 재료 목록과 새로운 재료 목록을 비교하여 삭제할 재료 식별
        for (IngredientTypeVO existingType : existingTypes) {
            if (!newIngredientCDs.contains(existingType.getIngredientCD())) {
                // 새로운 재료 목록에 존재하지 않는 기존 재료는 삭제
            	ingredientTypeMapper.deleteIngredientTypeByRecipeCDAndIngredientCD(recipeCD, existingType.getIngredientCD());
                log.info("Deleted unused ingredient type: RecipeID={}, IngredientCD={}", recipeCD, existingType.getIngredientCD());
            }
        }
        return true;
    }

	@Override
    public boolean deleteIngredientTypesByRecipeCD(int recipeCD) {
        int deletedCount = ingredientTypeMapper.deleteIngredientTypesByRecipeCD(recipeCD);
        log.info("Deleted {} ingredient types for recipe: {}", deletedCount, recipeCD);
        return deletedCount > 0;
    }

	@Override
    public boolean deleteIngredientTypeByRecipeCDAndIngredientCD(int recipeCD, int ingredientCD) {
        int deletedCount = ingredientTypeMapper.deleteIngredientTypeByRecipeCDAndIngredientCD(recipeCD, ingredientCD);
        log.info("Deleted ingredient type: RecipeID={}, IngredientCD={}", recipeCD, ingredientCD);
        return deletedCount > 0;
    }
	

	
	 
    
	
    
}