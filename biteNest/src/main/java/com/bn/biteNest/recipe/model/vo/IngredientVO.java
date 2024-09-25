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
public class IngredientVO {
    private int ingredientCD;       // 재료 고유 ID
    private String ingredientNM;    // 재료 이름
}