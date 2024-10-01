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
            // 1. ������ �⺻ ���� ����
            Optional<RecipeVO> recipeVOOptional = createRecipe(formData, session, mainImage);
            RecipeVO recipeVO = recipeVOOptional.orElseThrow(() -> new RuntimeException("Failed to create recipe."));

            // 2. ��� ���� ����
            boolean ingredientsSaved = saveIngredients(recipeVO.getRecipeCD(), ingredients);
            if (!ingredientsSaved) throw new RuntimeException("Failed to save ingredients.");

            // 3. ���� �ܰ� ���� ����
            boolean stepsSaved = saveSteps(recipeVO.getRecipeCD(), steps, stepImages, session, recipeVO.getRecipeNM());
            if (!stepsSaved) throw new RuntimeException("Failed to save steps.");

            // 4. �� ���� ����
            boolean tipsSaved = saveTips(recipeVO.getRecipeCD(), tips);
            if (!tipsSaved) throw new RuntimeException("Failed to save tips.");

            return recipeVO.getRecipeCD();

        } catch (Exception e) {
            log.error("Recipe creation failed: ", e);
            throw new RuntimeException("Recipe creation failed: " + e.getMessage());
        }
    }

    // 1-1. ������ ���� ����
//    private Optional<RecipeVO> createRecipe(Map<String, String> formData, HttpSession session, MultipartFile mainImage) {
//        try {
//            String recipeName = formData.get("recipeName");
//            String recipeDescription = formData.get("recipeDescription");
//            int estimatedTime = Integer.parseInt(formData.get("estimatedTime"));
//            int cookingServings = Integer.parseInt(formData.get("cookingServings"));
//            String category1Name = formData.get("category1Name");
//            String category2Name = formData.get("category2Name");
//            
//            int category1CD = getCategory1CDByName(category1Name);  // 1�� ī�װ� �ڵ� ��ȸ
//            int category2CD = getCategory2CDByName(category2Name);  // 2�� ī�װ� �ڵ� ��ȸ
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
//            // ���� �̹��� ����
//            if (mainImage != null && !mainImage.isEmpty()) {
//                String mainImagePath = fileUpload.saveImage(mainImage, session, recipeName, null);
//                recipeVO.setRecipeMainImage(mainImagePath);  // ����� ���� �̹��� ��� ����
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
            // �Է� ������ Ȯ��
            String recipeName = formData.get("recipeName");
            String recipeDescription = formData.get("recipeDescription");
            int estimatedTime = Integer.parseInt(formData.get("estimatedTime"));
            int cookingServings = Integer.parseInt(formData.get("cookingServings"));
            String category1Name = formData.get("category1Name");
            String category2Name = formData.get("category2Name");

            log.info("Recipe Name: {}, Description: {}, Estimated Time: {}, Servings: {}, Category1: {}, Category2: {}",
                     recipeName, recipeDescription, estimatedTime, cookingServings, category1Name, category2Name);

            // ī�װ� �ڵ� ��ȸ
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

            // �����ͺ��̽��� ���� �õ�
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


    // 1-1-1. ī�װ�1 �ڵ� ��ȸ
    private int getCategory1CDByName(String category1Name) {
        return recipeCategory1Service.getCategory1CDByName(category1Name)
                .orElseThrow(() -> new RuntimeException("Invalid Category1 Name: " + category1Name));
    }

    // 1-1-2. ī�װ�2 �ڵ� ��ȸ
    private int getCategory2CDByName(String category2Name) {
        return recipeCategory2Service.getCategory2CDByName(category2Name)
                .orElseThrow(() -> new RuntimeException("Invalid Category2 Name: " + category2Name));
    }

    // 1-2. ��� ���� ����
    private boolean saveIngredients(int recipeCD, List<Map<String, String>> ingredients) {
        for (Map<String, String> ingredient : ingredients) {
            String ingredientName = ingredient.get("ingredientName");
            String ingredientAmount = ingredient.get("ingredientAmount");
            String ingredientType = ingredient.get("ingredientType");

            // ��� �̸��� �������� ��ȸ�ϰ�, �������� ������ ���� �߰��Ͽ� ID�� ����
            int ingredientCD = ingredientService.findByName(ingredientName)
                    .map(ingredientVO -> ingredientVO.getIngredientCD())  // ��ᰡ ������ ��� ID ��ȯ
                    .orElseGet(() -> ingredientService.saveNewIngredient(ingredientName)); // ������ �� ��� �߰� �� ID ��ȯ

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

    // 1-3. ���� �ܰ� ���� ����
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

    // 1-4. �� ���� ����
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

    // ������ ��ü ��ȸ
    @Override
    public RecipeDetailDTO getRecipeById(int recipeCD) {
        RecipeDetailDTO recipeDetail = recipeMapper.selectRecipeById(recipeCD);

        if (recipeDetail == null) {
            throw new RuntimeException("Recipe not found for ID: " + recipeCD);
        }

        return recipeDetail;
    }
}
