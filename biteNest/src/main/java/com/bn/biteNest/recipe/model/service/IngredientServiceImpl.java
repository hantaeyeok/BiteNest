package com.bn.biteNest.recipe.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bn.biteNest.recipe.model.dao.IngredientMapper;
import com.bn.biteNest.recipe.model.vo.IngredientTypeVO;
import com.bn.biteNest.recipe.model.vo.IngredientVO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientMapper ingredientMapper;
    private final IngredientTypeService ingredientTypeService;
    
    //재료 이름으로 재료 검색
    @Override
    public Optional<IngredientVO> findByName(String ingredientName) {
        return Optional.ofNullable(ingredientMapper.selectByName(ingredientName));
    }
    
    // 새로운 재료 저장 (MERGE 적용)
    @Override
    @Transactional
    public int saveNewIngredient(String ingredientName) {
        IngredientVO ingredient = IngredientVO.builder()
                                              .ingredientNM(ingredientName)
                                              .build();
        try {
            // MERGE 구문을 사용하여 삽입 또는 업데이트 수행
            ingredientMapper.mergeIngredient(ingredient);

            // 삽입 또는 업데이트 후 해당 재료 ID를 반환
            return ingredient.getIngredientCD();
        } catch (Exception e) {
            log.error("Error occurred during ingredient insertion: {}", ingredientName, e);
            throw new RuntimeException("Failed to insert ingredient: " + ingredientName);
        }
    }
    
    // 레시피의 재료 업데이트
    @Override
    public boolean updateIngredientsByRecipe(int recipeCD, List<Map<String, String>> ingredients) {
        try {
            List<IngredientTypeVO> newIngredients = new ArrayList<>();
            
            for(Map<String, String> ingredient : ingredients) {
                String ingredientName = ingredient.get("ingredientName");
                String ingredientAmount = ingredient.get("ingredientAmount");
                String ingredientType = ingredient.get("ingredientType");

                // MERGE 구문을 사용하여 재료 저장 후 ID 반환
                int ingredientCD = saveNewIngredient(ingredientName);

                // IngredientTypeVO 객체 생성 및 리스트에 추가
                IngredientTypeVO ingredientTypeVO = IngredientTypeVO.builder()
                                                                    .recipeCD(recipeCD)
                                                                    .ingredientCD(ingredientCD)
                                                                    .ingredientAmt(ingredientAmount)
                                                                    .ingredientType(ingredientType)
                                                                    .build();
                newIngredients.add(ingredientTypeVO);            
            }

            return ingredientTypeService.updateIngredientTypesByRecipe(recipeCD, newIngredients);
        } catch (Exception e) {
            log.error("Failed to update ingredients for recipe CD: {}", recipeCD, e);
            return false;
        }
    }
    
    //레시피 재료 삭제
    public boolean deleteRecipeIngredients(int recipeCD) {
        try {
            List<IngredientTypeVO> ingredientTypes = ingredientTypeService.getIngredientsByRecipeCD(recipeCD);            

            for (IngredientTypeVO ingredientType : ingredientTypes) {
                int ingredientCD = ingredientType.getIngredientCD();

             	// 다른 레시피에서 사용되는 재료인지 확인
                if (countRecipesUsingIngredient(recipeCD, ingredientCD) > 0) {
                    ingredientTypeService.deleteIngredientTypeByRecipeCDAndIngredientCD(recipeCD, ingredientCD);
                } else {
                    ingredientTypeService.deleteIngredientTypeByRecipeCDAndIngredientCD(recipeCD, ingredientCD);
                    deleteIngredientByCD(ingredientCD);
                }
            }
            return true;
        } catch (Exception e) {
            log.error("Failed to delete ingredients for recipe: " + recipeCD, e);
            return false;
        }
    }
    
    @Override
    public int countRecipesUsingIngredient(int recipeCD, int ingredientCD) {
    	return ingredientMapper.countRecipesUsingIngredient(recipeCD, ingredientCD);
    }

    @Override
    public boolean deleteIngredientByCD(int ingredientCD) {
    	return ingredientMapper.deleteIngredientByCD(ingredientCD) > 0;
    }
    
}