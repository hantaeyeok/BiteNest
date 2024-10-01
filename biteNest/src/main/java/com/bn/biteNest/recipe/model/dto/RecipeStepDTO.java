package com.bn.biteNest.recipe.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RecipeStepDTO {
    private int stepOrder;                 // 조리 단계 순서
    private String instruction;            // 조리 단계 설명
    private String imageURL;               // 조리 단계 이미지 경로
}