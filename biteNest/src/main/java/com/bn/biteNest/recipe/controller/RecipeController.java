package com.bn.biteNest.recipe.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.bn.biteNest.common.Message;
import com.bn.biteNest.common.ResponseHandler;
import com.bn.biteNest.recipe.model.dto.RecipeDetailDTO;
import com.bn.biteNest.recipe.model.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final ResponseHandler responseHandler;
    
    // 1. Recipe Insert
    @PostMapping(value = "/create", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<Message> createRecipe(
            @RequestPart("formData") Map<String, String> formData,
            @RequestPart("ingredients") List<Map<String, String>> ingredients,
            @RequestPart("steps") List<Map<String, String>> steps,
            @RequestPart("tips") List<Map<String, String>> tips,
            @RequestPart("imageFiles") List<MultipartFile> imageFiles) {
        try {
            log.info("Received formData: {}", formData);
            log.info("Received ingredients: {}", ingredients);
            log.info("Received steps: {}", steps);
            log.info("Received tips: {}", tips);
            log.info("Received number of image files: {}", imageFiles.size());

            // 임시 응답으로 받은 데이터를 모두 포함하여 응답을 만들어 봅니다.
            Map<String, Object> responseData = Map.of(
                "formData", formData,
                "ingredients", ingredients,
                "steps", steps,
                "tips", tips,
                "imageFilesCount", imageFiles.size()
            );

            return responseHandler.createResponse("Received recipe data", responseData, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Recipe creation failed: ", e);
            return responseHandler.handleException("Recipe creation failed", e);
        }
    }


    // 2. Recipe Select
    @GetMapping("/{recipeCD}")
    public ResponseEntity<Message> getRecipeById(@PathVariable("recipeCD") int recipeCD) {
        try {
            RecipeDetailDTO recipeDetail = recipeService.getRecipeByCD(recipeCD);
            return responseHandler.createResponse("Recipe search success", recipeDetail, HttpStatus.OK);

        } catch (RuntimeException e) {
            log.error("Recipe search failed: ", e);
            return responseHandler.createResponse("Recipe not found: " + e.getMessage(), null, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            log.error("Unexpected error during recipe search: ", e);
            return responseHandler.handleException("Unexpected error during recipe search", e);
        }
    }
    
    // 3. Recipe Update
    @PutMapping("/{recipeCD}")
    public ResponseEntity<Message> updateRecipe(
            @PathVariable("recipeCD") int recipeCD,
            @RequestPart("formData") Map<String, String> formData,
            @RequestPart("ingredients") List<Map<String, String>> ingredients,
            @RequestPart("steps") List<Map<String, String>> steps,
            @RequestPart("tips") List<Map<String, String>> tips,
            @RequestPart(value = "mainImage", required = false) MultipartFile mainImage,
            @RequestPart(value = "stepImages", required = false) List<MultipartFile> stepImages) {
        try {
            boolean isUpdated = recipeService.updateRecipeWithDetails(recipeCD, formData, ingredients, steps, tips, mainImage, stepImages);

            return isUpdated ? responseHandler.createResponse("Recipe updated successfully", "success", HttpStatus.OK)
                             : responseHandler.createResponse("Failed to update recipe", "error", HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (RuntimeException e) {
            log.error("Recipe update failed: ", e);
            return responseHandler.createResponse("Recipe not found: " + e.getMessage(), null, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            log.error("Unexpected error during recipe update: ", e);
            return responseHandler.handleException("Unexpected error during recipe update", e);
        }
    }

    
    // 4. Recipe Delete
    @DeleteMapping("/{recipeCD}")
    public ResponseEntity<Message> deleteRecipeById(@PathVariable("recipeCD") int recipeCD) {
        try {
            boolean isDeleted = recipeService.deleteRecipeByCD(recipeCD);

            return isDeleted ? responseHandler.createResponse("Recipe deleted successfully", "success", HttpStatus.OK)
                             : responseHandler.createResponse("Recipe not found for deletion", null, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            log.error("Recipe deletion failed: ", e);
            return responseHandler.handleException("Recipe deletion failed", e);
        }
    }
    
    
}
