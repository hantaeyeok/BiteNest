package com.bn.biteNest.recipe.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bn.biteNest.common.Message;
import com.bn.biteNest.recipe.model.dto.RecipeDetailDTO;
import com.bn.biteNest.recipe.model.service.RecipeService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    // Recipe insert
    @PostMapping(value = "/create", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<Message> createRecipe(
            @RequestPart("formData") Map<String, String> formData,  
            @RequestPart("ingredients") List<Map<String, String>> ingredients,  
            @RequestPart("steps") List<Map<String, String>> steps,
            @RequestPart("tips") List<Map<String, String>> tips,
            @RequestPart("imageFiles") List<MultipartFile> imageFiles,
            HttpSession session) {
        try {
            MultipartFile mainImage = 
            		imageFiles.isEmpty() ? null : imageFiles.get(0); 
            List<MultipartFile> stepImages = 
            		(imageFiles.size() > 1) ? imageFiles.subList(1, imageFiles.size()) : List.of();

            recipeService.createRecipeWithDetails(formData, ingredients, steps, tips, mainImage, stepImages, session);
            
            return ResponseEntity.status(HttpStatus.OK)
                .body(Message.builder().data("success").message("Recipe created successfully").build());

        } catch (Exception e) {
            log.error("Recipe creation failed: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Message.builder().data("Recipe Insert Error").message("Unexpected error: " + e.getMessage()).build());
        }
    }

    // Recipe 조회
    @GetMapping("/{recipeCD}")
    public ResponseEntity<Message> getRecipeById(@PathVariable("recipeCD") int recipeCD) {

        try {
            RecipeDetailDTO recipeDetail = recipeService.getRecipeById(recipeCD);
            return ResponseEntity.ok(Message.builder().data(recipeDetail).message("Recipe search success").build());

        } catch (RuntimeException e) {
            log.error("Recipe search failed: ", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Message.builder().data(null).message("Recipe not found: " + e.getMessage()).build());
        } catch (Exception e) {
            log.error("Unexpected error during recipe search: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Message.builder().data(null).message("Unexpected error: " + e.getMessage()).build());
        }
    }
    
    // Recipe 수정
    @PutMapping("/{recipeCD}")
    public ResponseEntity<Message> updateRecipe(
    		@PathVariable int recipeCD,
            @RequestPart("formData") Map<String, String> formData,
            @RequestPart("ingredients") List<Map<String, String>> ingredients,
            @RequestPart("steps") List<Map<String, String>> steps,
            @RequestPart("tips") List<Map<String, String>> tips,
            @RequestPart(value = "mainImage", required = false) MultipartFile mainImage,
            @RequestPart(value = "stepImages", required = false) List<MultipartFile> stepImages,
            HttpSession session){
    	try {
    		boolean isUpdated = recipeService.updateRecipeWithDetails(
    				recipeCD,
                    formData,
                    ingredients,
                    steps,
                    tips,
                    mainImage,
                    stepImages,
                    session
    				);
    				    		
    		if (isUpdated) {
                return ResponseEntity.ok(Message.builder().data("success").message("Recipe updated successfully").build());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                     .body(Message.builder().data("error").message("Failed to update recipe").build());
            }

        } catch (RuntimeException e) {
            log.error("Recipe update failed: ", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Message.builder().data(null).message("Recipe not found: " + e.getMessage()).build());
        } catch (Exception e) {
            log.error("Unexpected error during recipe update: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Message.builder().data(null).message("Unexpected error: " + e.getMessage()).build());
        }
    }
    
    
    // Recipe 삭제
    
    
    // Recipe 임시저장
    
    
    // Recipe 댓글 
    
    
    // Recipe 댓글 좋아요
    
    
    // Recipe 북마크
    
    
    
    
}
