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
            @RequestPart("formData") Map<String, String> formData,  // �Ϲ� �����͸� Map���� ����
            @RequestPart("ingredients") List<Map<String, String>> ingredients,  // ��� ���� �迭
            @RequestPart("steps") List<Map<String, String>> steps,  // ���� �ܰ� ���� �迭
            @RequestPart("tips") List<Map<String, String>> tips,  // �� �迭
            @RequestPart("imageFiles") List<MultipartFile> imageFiles,  // �̹��� ���ϵ� (��Ÿ ����)
            HttpSession session) {
        try {
            // �̹��� ���� ����Ʈ���� ���� �̹����� ���� �̹��� �и�
            MultipartFile mainImage = imageFiles.isEmpty() ? null : imageFiles.get(0);  // ù ��° �̹����� ���� �̹����� ���
            List<MultipartFile> stepImages = (imageFiles.size() > 1) ? imageFiles.subList(1, imageFiles.size()) : List.of();  // ������ �̹����� stepImages��

            // ���� �޼��� ȣ�� �� ���� �̹����� ���� �̹����� ������ ����
            recipeService.createRecipeWithDetails(formData, ingredients, steps, tips, mainImage, stepImages, session);
            
            return ResponseEntity.status(HttpStatus.OK)
                .body(Message.builder().data("success").message("Recipe created successfully").build());

        } catch (Exception e) {
            log.error("Recipe creation failed: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Message.builder().data("Recipe Insert Error").message("Unexpected error: " + e.getMessage()).build());
        }
    }

    // Recipe ��ȸ
    @GetMapping("/{recipeCD}")
    public ResponseEntity<Message> getRecipeById(@PathVariable("recipeCD") int recipeCD) {
        try {
            // Service ������ ȣ���Ͽ� ������ �� ������ ��ȸ
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
