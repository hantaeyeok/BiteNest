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
    private int recipeCD;                  // ������ ID
    private String recipeNM;               // ������ �̸�
    private String recipeDescription;      // ������ ����
    private int estimatedTime;             // ���� �ҿ� �ð� (��)
    private int cookingServings;           // ���� �κ�
    private String recipeMainImage;        // ���� �̹��� ���
    private CategoryDTO category;  // ī�װ� ���� ��ü
    private List<IngredientDTO> ingredients;  // ��� ���� �迭
    private List<RecipeStepDTO> steps;        // ���� �ܰ� ���� �迭
    private List<TipDTO> tips;               // �� ���� �迭
	
}