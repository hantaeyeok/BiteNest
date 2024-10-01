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
public class TipVO {
    private int tipCD;         // ÆÁ ±âº»Å°
    private int recipeCD;      // ·¹½ÃÇÇ ±âº»Å°
    private String tipContent; // ÆÁ ³»¿ë
    private int tipORD;        // ÆÁ ¼ø¼­
}