package com.bn.biteNest.recipe.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import com.bn.biteNest.recipe.model.dto.RecipeDetailDTO;

public interface RecipeService {

	RecipeDetailDTO getRecipeByCD(int recipeCD);
	
	boolean createRecipeWithDetails(
            Map<String, String> formData,
            List<Map<String, String>> ingredients,
            List<Map<String, String>> steps,
            List<Map<String, String>> tips,
            MultipartFile mainImage,
            List<MultipartFile> stepImages);

	boolean updateRecipeWithDetails(
			int recipeCD, 
			Map<String, String> formData,
			List<Map<String, String>> ingredients, 
			List<Map<String, String>> steps, 
			List<Map<String, String>> tips,
			MultipartFile mainImage, 
			List<MultipartFile> stepImages
			);

	boolean deleteRecipeByCD(int recipeCD);
    
    
}
