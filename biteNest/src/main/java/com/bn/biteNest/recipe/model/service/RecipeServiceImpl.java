package com.bn.biteNest.recipe.model.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bn.biteNest.common.FileUpload;
import com.bn.biteNest.recipe.model.dao.RecipeMapper;
import com.bn.biteNest.recipe.model.dto.RecipeDetailDTO;
import com.bn.biteNest.recipe.model.vo.IngredientTypeVO;
import com.bn.biteNest.recipe.model.vo.RecipeStepVO;
import com.bn.biteNest.recipe.model.vo.RecipeVO;
import com.bn.biteNest.recipe.model.vo.TipVO;

import jakarta.servlet.http.HttpSession;
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
    private final FileUpload fileUpload;
    private final RecipeCategory1Service recipeCategory1Service;
    private final RecipeCategory2Service recipeCategory2Service;

    @Transactional
    @Override
    public int createRecipeWithDetails(
            Map<String, String> formData,
            List<Map<String, String>> ingredients,
            List<Map<String, String>> steps,
            List<Map<String, String>> tips,
            MultipartFile mainImage,
            List<MultipartFile> stepImages,
            HttpSession session) {

        try {
            // 1. 레시피 기본 정보 생성
            Optional<RecipeVO> recipeVOOptional = createRecipe(formData, session, mainImage);
            RecipeVO recipeVO = recipeVOOptional.orElseThrow(() -> new RuntimeException("Failed to create recipe."));

            // 2. 재료 정보 저장
            boolean ingredientsSaved = saveIngredients(recipeVO.getRecipeCD(), ingredients);
            if (!ingredientsSaved) throw new RuntimeException("Failed to save ingredients.");

            // 3. 조리 단계 정보 저장
            boolean stepsSaved = saveSteps(recipeVO.getRecipeCD(), steps, stepImages, session, recipeVO.getRecipeNM());
            if (!stepsSaved) throw new RuntimeException("Failed to save steps.");

            // 4. 팁 정보 저장
            boolean tipsSaved = saveTips(recipeVO.getRecipeCD(), tips);
            if (!tipsSaved) throw new RuntimeException("Failed to save tips.");

            return recipeVO.getRecipeCD();

        } catch (Exception e) {
            log.error("Recipe creation failed: ", e);
            throw new RuntimeException("Recipe creation failed: " + e.getMessage());
        }
    }

    // 1-1. 레시피 정보 생성
//    private Optional<RecipeVO> createRecipe(Map<String, String> formData, HttpSession session, MultipartFile mainImage) {
//        try {
//            String recipeName = formData.get("recipeName");
//            String recipeDescription = formData.get("recipeDescription");
//            int estimatedTime = Integer.parseInt(formData.get("estimatedTime"));
//            int cookingServings = Integer.parseInt(formData.get("cookingServings"));
//            String category1Name = formData.get("category1Name");
//            String category2Name = formData.get("category2Name");
//            
//            int category1CD = getCategory1CDByName(category1Name);  // 1차 카테고리 코드 조회
//            int category2CD = getCategory2CDByName(category2Name);  // 2차 카테고리 코드 조회
//
//            RecipeVO recipeVO = RecipeVO.builder()
//                    .recipeNM(recipeName)
//                    .recipeDescription(recipeDescription)
//                    .estimatedTime(estimatedTime)
//                    .cookingServings(cookingServings)
//                    .category1CD(category1CD)
//                    .category2CD(category2CD)
//                    .build();
//
//            // 메인 이미지 저장
//            if (mainImage != null && !mainImage.isEmpty()) {
//                String mainImagePath = fileUpload.saveImage(mainImage, session, recipeName, null);
//                recipeVO.setRecipeMainImage(mainImagePath);  // 저장된 메인 이미지 경로 설정
//            }
//
//            return recipeMapper.insertRecipe(recipeVO) > 0 ? Optional.of(recipeVO) : Optional.empty();
//
//        } catch (Exception e) {
//            log.error("Failed to create recipe: ", e);
//            return Optional.empty();
//        }
//    }
    private Optional<RecipeVO> createRecipe(Map<String, String> formData, HttpSession session, MultipartFile mainImage) {
        try {
            // 입력 데이터 확인
            String recipeName = formData.get("recipeName");
            String recipeDescription = formData.get("recipeDescription");
            int estimatedTime = Integer.parseInt(formData.get("estimatedTime"));
            int cookingServings = Integer.parseInt(formData.get("cookingServings"));
            String category1Name = formData.get("category1Name");
            String category2Name = formData.get("category2Name");

            log.info("Recipe Name: {}, Description: {}, Estimated Time: {}, Servings: {}, Category1: {}, Category2: {}",
                     recipeName, recipeDescription, estimatedTime, cookingServings, category1Name, category2Name);

            // 카테고리 코드 조회
            int category1CD = getCategory1CDByName(category1Name);
            int category2CD = getCategory2CDByName(category2Name);

            log.info("Category1CD: {}, Category2CD: {}", category1CD, category2CD);

            RecipeVO recipeVO = RecipeVO.builder()
                    .recipeNM(recipeName)
                    .recipeDescription(recipeDescription)
                    .estimatedTime(estimatedTime)
                    .cookingServings(cookingServings)
                    .category1CD(category1CD)
                    .category2CD(category2CD)
                    .build();

            if (mainImage != null && !mainImage.isEmpty()) {
                String mainImagePath = fileUpload.saveImage(mainImage, session, recipeName, null);
                recipeVO.setRecipeMainImage(mainImagePath);
            }

            log.info("Inserting recipe into the database: {}", recipeVO);

            // 데이터베이스에 삽입 시도
            if (recipeMapper.insertRecipe(recipeVO) > 0) {
                log.info("Recipe successfully created: {}", recipeVO.getRecipeCD());
                return Optional.of(recipeVO);
            } else {
                log.warn("Failed to insert recipe into database.");
                return Optional.empty();
            }

        } catch (Exception e) {
            log.error("Failed to create recipe: ", e);
            return Optional.empty();
        }
    }


    // 1-1-1. 카테고리1 코드 조회
    private int getCategory1CDByName(String category1Name) {
        return recipeCategory1Service.getCategory1CDByName(category1Name)
                .orElseThrow(() -> new RuntimeException("Invalid Category1 Name: " + category1Name));
    }

    // 1-1-2. 카테고리2 코드 조회
    private int getCategory2CDByName(String category2Name) {
        return recipeCategory2Service.getCategory2CDByName(category2Name)
                .orElseThrow(() -> new RuntimeException("Invalid Category2 Name: " + category2Name));
    }

    // 1-2. 재료 정보 저장
    private boolean saveIngredients(int recipeCD, List<Map<String, String>> ingredients) {
        for (Map<String, String> ingredient : ingredients) {
            String ingredientName = ingredient.get("ingredientName");
            String ingredientAmount = ingredient.get("ingredientAmount");
            String ingredientType = ingredient.get("ingredientType");

            // 재료 이름을 기준으로 조회하고, 존재하지 않으면 새로 추가하여 ID를 받음
            int ingredientCD = ingredientService.findByName(ingredientName)
                    .map(ingredientVO -> ingredientVO.getIngredientCD())  // 재료가 존재할 경우 ID 반환
                    .orElseGet(() -> ingredientService.saveNewIngredient(ingredientName)); // 없으면 새 재료 추가 후 ID 반환

            IngredientTypeVO ingredientTypeVO = IngredientTypeVO.builder()
                    .recipeCD(recipeCD)
                    .ingredientCD(ingredientCD)
                    .ingredientAmt(ingredientAmount)
                    .ingredientType(ingredientType)
                    .build();

            if (ingredientTypeService.insertIngredientType(ingredientTypeVO) <= 0) {
                return false;  
            }
        }
        return true;
    }

    // 1-3. 조리 단계 정보 저장
    private boolean saveSteps(int recipeCD, List<Map<String, String>> steps, List<MultipartFile> stepImages, HttpSession session, String recipeName) throws Exception {
        for (int i = 0; i < steps.size(); i++) {
            Map<String, String> step = steps.get(i);

            RecipeStepVO stepVO = RecipeStepVO.builder()
                    .recipeCD(recipeCD)
                    .stepORD(Integer.parseInt(step.get("stepsOrder")))
                    .instruction(step.get("stepsDescription"))
                    .build();

            if (stepImages != null && i < stepImages.size() && !stepImages.get(i).isEmpty()) {
                String stepImagePath = fileUpload.saveImage(stepImages.get(i), session, recipeName, i + 1);
                stepVO.setImageURL(stepImagePath);
            }

            if (recipeStepService.insertRecipeStep(stepVO) <= 0) return false;
        }
        return true;
    }

    // 1-4. 팁 정보 저장
    private boolean saveTips(int recipeCD, List<Map<String, String>> tips) {
        for (Map<String, String> tip : tips) {
            TipVO tipVO = TipVO.builder()
                    .recipeCD(recipeCD)
                    .tipContent(tip.get("tipContent"))
                    .tipORD(Integer.parseInt(tip.get("tipOrder")))
                    .build();
            if (tipService.insertTip(tipVO) <= 0) return false;
        }
        return true;
    }

    // 레시피 전체 조회
    @Override
    public RecipeDetailDTO getRecipeById(int recipeCD) {
        RecipeDetailDTO recipeDetail = recipeMapper.selectRecipeById(recipeCD);

        if (recipeDetail == null) {
            throw new RuntimeException("Recipe not found for ID: " + recipeCD);
        }

        return recipeDetail;
    }
}
