package com.bn.biteNest.recipe.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
            @RequestPart("formData") Map<String, String> formData,  // 일반 데이터를 Map으로 받음
            @RequestPart("ingredients") List<Map<String, String>> ingredients,  // 재료 정보 배열
            @RequestPart("steps") List<Map<String, String>> steps,  // 조리 단계 설명 배열
            @RequestPart("tips") List<Map<String, String>> tips,  // 팁 배열
            @RequestPart("imageFiles") List<MultipartFile> imageFiles,  // 이미지 파일들 (오타 수정)
            HttpSession session) {
        try {
            // 이미지 파일 리스트에서 메인 이미지와 조리 이미지 분리
            MultipartFile mainImage = imageFiles.isEmpty() ? null : imageFiles.get(0);  // 첫 번째 이미지를 메인 이미지로 사용
            List<MultipartFile> stepImages = (imageFiles.size() > 1) ? imageFiles.subList(1, imageFiles.size()) : List.of();  // 나머지 이미지를 stepImages로

            // 서비스 메서드 호출 시 메인 이미지와 조리 이미지를 별도로 전달
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
            // Service 계층을 호출하여 레시피 상세 정보를 조회
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
}
