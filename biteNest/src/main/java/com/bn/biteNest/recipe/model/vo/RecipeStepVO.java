package com.bn.biteNest.recipe.model.vo;

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
public class RecipeStepVO {
    private int stepCD;             // 조리단계 기본키
    private int recipeCD;           // 레시피 기본키
    private int stepORD;            // 조리단계 순서
    private String instruction;     // 조리단계 설명
    private String imageURL;        // 단계별 이미지 URL (선택)
}