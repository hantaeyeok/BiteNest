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
public class TipVO {
    private int tipCD;         // ÆÁ ±âº»Å°
    private int recipeCD;      // ·¹½ÃÇÇ ±âº»Å°
    private String tipContent; // ÆÁ ³»¿ë
    private int tipORD;        // ÆÁ ¼ø¼­
}