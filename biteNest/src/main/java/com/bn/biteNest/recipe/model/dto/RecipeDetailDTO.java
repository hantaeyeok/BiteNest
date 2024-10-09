package com.bn.biteNest.recipe.model.dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDetailDTO {
    private int recipeCD;                  // 레시피 ID
    private String recipeNM;               // 레시피 이름
    private String recipeDescription;      // 레시피 설명
    private int estimatedTime;             // 예상 소요 시간 (분)
    private int cookingServings;           // 기준 인분
    private String recipeMainImage;        // 메인 이미지 경로
    private CategoryDTO category;  // 카테고리 정보 객체
    private List<IngredientDTO> ingredients;  // 재료 정보 배열
    private List<RecipeStepDTO> steps;        // 조리 단계 정보 배열
    private List<TipDTO> tips;               // 팁 정보 배열
	
}