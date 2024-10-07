package com.bn.biteNest.recipe.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bn.biteNest.common.RecipeFileDelete;
import com.bn.biteNest.common.RecipeFileUpload;
import com.bn.biteNest.recipe.model.dao.RecipeMapper;
import com.bn.biteNest.recipe.model.dto.RecipeDetailDTO;
import com.bn.biteNest.recipe.model.vo.IngredientTypeVO;
import com.bn.biteNest.recipe.model.vo.RecipeStepVO;
import com.bn.biteNest.recipe.model.vo.RecipeVO;
import com.bn.biteNest.recipe.model.vo.TipVO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeMapper recipeMapper;
    private final IngredientService ingredientService;
    private final IngredientTypeService ingredientTypeService;
    private final RecipeStepService recipeStepService;
    private final TipService tipService;
    private final RecipeFileUpload fileUpload;
    private final RecipeCategory1Service recipeCategory1Service;
    private final RecipeCategory2Service recipeCategory2Service;
    private final RecipeFileDelete fileDelete;
    
    // 1. Recipe Insert
    @Transactional
    @Override
    public boolean createRecipeWithDetails(Map<String, String> formData, 
    									   List<Map<String, String>> ingredients,
                                           List<Map<String, String>> steps, 
                                           List<Map<String, String>> tips,
                                           MultipartFile mainImage, 
                                           List<MultipartFile> stepImages) {
        RecipeVO recipeVO = createRecipe(formData, mainImage);
        return saveRecipeDetails(recipeVO.getRecipeCD(), ingredients, steps, tips, stepImages, recipeVO.getRecipeNM());
    }

    // 1-1. Recipe 기본 정보 insert
    private RecipeVO createRecipe(Map<String, String> formData, MultipartFile mainImage) {
        RecipeVO recipeVO = buildRecipeVO(formData, mainImage);
        if (recipeMapper.insertRecipe(recipeVO) <= 0) {
            throw new RuntimeException("Failed to create recipe in the database.");
        }
        return recipeVO;
    }

    // 1-1-1. RecipeVO 생성 (레시피 기본 정보 빌드)
    private RecipeVO buildRecipeVO(Map<String, String> formData, MultipartFile mainImage) {
        int category1CD = getCategory1CDByName(formData.get("category1Name"));
        int category2CD = getCategory2CDByName(formData.get("category2Name"));

        String mainImagePath = (mainImage != null && !mainImage.isEmpty())
        		? fileUpload.saveImage(mainImage, formData.get("recipeName"), null)
                : "";

        return RecipeVO.builder()
		                .recipeNM(formData.get("recipeName"))
		                .recipeDescription(formData.get("recipeDescription"))
		                .estimatedTime(Integer.parseInt(formData.get("estimatedTime")))
		                .cookingServings(Integer.parseInt(formData.get("cookingServings")))
		                .category1CD(category1CD)
		                .category2CD(category2CD)
		                .recipeMainImage(mainImagePath)
		                .build();
    }

    // 1-1-2. 1차 카테고리 CD 조회
    private int getCategory1CDByName(String category1Name) {
        return recipeCategory1Service.getCategory1CDByName(category1Name)
        		.orElseThrow(() -> new RuntimeException("Invalid Category1 Name: " + category1Name));
    }

    // 1-1-3. 2차 카테고리 CD 조회
    private int getCategory2CDByName(String category2Name) {
        return recipeCategory2Service.getCategory2CDByName(category2Name)
                .orElseThrow(() -> new RuntimeException("Invalid Category2 Name: " + category2Name));
    }

    // 1-2. Recipe 세부 정보 insert (재료, 조리 단계, 팁 정보 저장)
    private boolean saveRecipeDetails(int recipeCD, 
    								  List<Map<String, String>> ingredients, 
    								  List<Map<String, String>> steps,
                                      List<Map<String, String>> tips, 
                                      List<MultipartFile> stepImages, 
                                      String recipeName) {
        return saveIngredients(recipeCD, ingredients) &&
               saveSteps(recipeCD, steps, stepImages, recipeName) &&
               saveTips(recipeCD, tips);
    }

    // 1-2-1. 재료 정보 저장
    private boolean saveIngredients(int recipeCD, List<Map<String, String>> ingredients) {
        for (Map<String, String> ingredient : ingredients) {
            int ingredientCD = ingredientService.findByName(ingredient.get("ingredientName"))
                                                .map(ingredientVO -> ingredientVO.getIngredientCD())
                                                .orElseGet(() -> ingredientService.saveNewIngredient(ingredient.get("ingredientName")));

            IngredientTypeVO ingredientTypeVO = IngredientTypeVO.builder()
											            		.recipeCD(recipeCD)
											                    .ingredientCD(ingredientCD)
											                    .ingredientAmt(ingredient.get("ingredientAmount"))
											                    .ingredientType(ingredient.get("ingredientType"))
											                    .build();

            if (!ingredientTypeService.insertIngredientType(ingredientTypeVO)) {
                return false;
            }
        }
        return true;
    }

    // 1-2-2. 조리 단계 정보 저장
    private boolean saveSteps(int recipeCD, 
    						  List<Map<String, String>> steps, 
    						  List<MultipartFile> stepImages, 
    						  String recipeName) {
    	
        for (int i = 0; i < steps.size(); i++) {
            String stepImagePath = (i < stepImages.size() && !stepImages.get(i).isEmpty())
                    ? fileUpload.saveImage(stepImages.get(i), recipeName, i + 1)
                    : "";

            RecipeStepVO stepVO = RecipeStepVO.builder()
						                      .recipeCD(recipeCD)
						                      .stepORD(Integer.parseInt(steps.get(i).get("stepsOrder")))
						                      .instruction(steps.get(i).get("stepsDescription"))
						                      .imageURL(stepImagePath)
						                      .build();

            if (!recipeStepService.insertRecipeStep(stepVO)) { 
            	return false; 
            	}
        }
        return true;
    }

    // 1-2-3. 팁 정보 저장
    private boolean saveTips(int recipeCD, List<Map<String, String>> tips) {
        for (Map<String, String> tip : tips) {
            TipVO tipVO =  TipVO.builder()
			                    .recipeCD(recipeCD)
			                    .tipContent(tip.get("tipContent"))
			                    .tipORD(Integer.parseInt(tip.get("tipOrder")))
			                    .build();

            if (!tipService.insertTip(tipVO)) {
                return false;
            }
        }
        return true;
    }
    
    
    
 // 2. Recipe Update 
    @Transactional
    @Override
    public boolean updateRecipeWithDetails(int recipeCD,
    									   Map<String, String> formData, 
    									   List<Map<String, String>> ingredients,
                                           List<Map<String, String>> steps, 
                                           List<Map<String, String>> tips,
                                           MultipartFile mainImage, 
                                           List<MultipartFile> stepImages) {
        boolean isRecipeUpdated = updateRecipe(recipeCD, formData, mainImage);
        boolean areDetailsUpdated = updateRecipeDetails(recipeCD, ingredients, steps, tips, stepImages, formData.get("recipeName"));
        return isRecipeUpdated && areDetailsUpdated;
    }

    // 2-1. Recipe 기본 정보 update
    private boolean updateRecipe(int recipeCD, Map<String, String> formData, MultipartFile mainImage) {
    	 // 기존 레시피 정보를 조회하여 기존 파일 경로를 가져옴
        RecipeDetailDTO existingRecipe = getRecipeByCD(recipeCD);
        // 기존 메인 이미지 경로
        String oldMainImage = existingRecipe.getRecipeMainImage();
        // 새로운 파일을 업로드하여 경로 생성
        String newMainImagePath = (mainImage != null && !mainImage.isEmpty())
        		? fileUpload.saveImage(mainImage, formData.get("recipeName"), null)
        		: oldMainImage; // 새로운 이미지가 없으면 기존 경로 유지

        // 새로운 파일이 존재하고 기존 파일이 존재하면 기존 파일 삭제
        if (mainImage != null && !mainImage.isEmpty() && oldMainImage != null && !oldMainImage.isEmpty()) {
        	fileDelete.deleteFile(oldMainImage); // 기존 파일 삭제
        }
    	
     // RecipeVO를 새롭게 생성
        RecipeVO updatedRecipe = buildRecipeVO(formData, mainImage);
        updatedRecipe.setRecipeCD(recipeCD);
        updatedRecipe.setRecipeMainImage(newMainImagePath); // 새 경로 설정

        return recipeMapper.updateRecipe(updatedRecipe) > 0;
    }

    // 2-2. Recipe 세부 정보 update
    private boolean updateRecipeDetails(int recipeCD, 
    									List<Map<String, String>> ingredients, 
    									List<Map<String, String>> steps,
                                        List<Map<String, String>> tips, 
                                        List<MultipartFile> stepImages, 
                                        String recipeName) {
        
        return ingredientService.updateIngredientsByRecipe(recipeCD, ingredients) &&
               recipeStepService.updateRecipeSteps(recipeCD, steps, stepImages, recipeName) &&
               tipService.updateTipsByRecipe(recipeCD, tips);
    }

    // 3. Recipe Select
    @Override
    public RecipeDetailDTO getRecipeByCD(int recipeCD) {
        return recipeMapper.selectRecipeDetailByCD(recipeCD)
                .orElseThrow(() -> new RuntimeException("Recipe not found for ID: " + recipeCD));
    }

    // 4. Recipe Delete
    @Transactional
    @Override
    public boolean deleteRecipeByCD(int recipeCD) {
        RecipeDetailDTO recipeDetail = getRecipeByCD(recipeCD);
        fileDelete.deleteFile(recipeDetail.getRecipeMainImage());
    	
    	if (!deleteRecipeDetails(recipeCD)) {
            log.error("Failed to delete recipe details for Recipe ID: {}", recipeCD);
            return false;
        }
    	
        boolean isRecipeDeleted = recipeMapper.deleteRecipeByCD(recipeCD) > 0;
        if (!isRecipeDeleted) {
            log.error("Failed to delete main recipe for Recipe ID: {}", recipeCD);
        }
        
        return isRecipeDeleted;
    }
    
    //4-1 Recip 세부 정보 삭ㅈ[ 재료, 조리, 팁]
    private boolean deleteRecipeDetails(int recipeCD) {
        boolean areIngredientsDeleted = ingredientTypeService.deleteIngredientTypesByRecipeCD(recipeCD);
        boolean areStepsDeleted = recipeStepService.deleteStepsByRecipeCD(recipeCD);
        boolean areTipsDeleted = tipService.deleteTipsByRecipeCD(recipeCD);

        // 세부 정보 삭제가 모두 성공했는지 확인
        if (!areIngredientsDeleted || !areStepsDeleted || !areTipsDeleted) {
            log.error("Failed to delete one or more recipe details for Recipe ID: {}. Ingredients deleted: {}, Steps deleted: {}, Tips deleted: {}",
                      recipeCD, areIngredientsDeleted, areStepsDeleted, areTipsDeleted);
            return false;
        }

        return true;
    }


}
