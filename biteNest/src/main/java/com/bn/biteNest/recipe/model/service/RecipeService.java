package com.bn.biteNest.recipe.model.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.bn.biteNest.recipe.model.dto.RecipeDetailDTO;
import com.bn.biteNest.recipe.model.vo.IngredientTypeVO;
import com.bn.biteNest.recipe.model.vo.RecipeStepVO;
import com.bn.biteNest.recipe.model.vo.RecipeVO;
import com.bn.biteNest.recipe.model.vo.TipVO;

import jakarta.servlet.http.HttpSession;

public interface RecipeService {

	//레시피 등록 로직
	public int createRecipeWithDetails(
            Map<String, String> formData,
            List<Map<String, String>> ingredients,
            List<Map<String, String>> steps,
            List<Map<String, String>> tips,
            MultipartFile mainImage,
            List<MultipartFile> stepImages,
            HttpSession session);


    // ID로 레시피 조회
        RecipeDetailDTO getRecipeById(int recipeCD); // 레시피 ID로 상세 조회
    
    
}
