package com.bn.biteNest.recipe.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.bn.biteNest.recipe.model.vo.RecipeStepVO;

public interface RecipeStepService {
    boolean insertRecipeStep(RecipeStepVO step);
    boolean updateRecipeSteps(int recipeCD, 
    						  List<Map<String, String>> steps, 
    						  List<MultipartFile> stepImages, 
    						  String recipeName);
    List<RecipeStepVO> getRecipeStepsByRecipeCD(int recipeCD);
    boolean deleteStepsByRecipeCD(int recipeCD);





}
