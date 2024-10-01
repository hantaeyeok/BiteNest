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
public class TipDTO {
    private int tipOrder;                  // ÆÁ ¼ø¼­
    private String tipContent;             // ÆÁ ³»¿ë
}
