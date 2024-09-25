package com.bn.biteNest.recipe.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IngredientTypeVO {
    private int recipeCD;           // 레시피 기본키
    private int ingredientCD;       // 재료 고유 ID
    private String ingredientType;  // 재료 유형 (주재료, 부재료 등)
    private String ingredientAmt;   // 재료 양
}
