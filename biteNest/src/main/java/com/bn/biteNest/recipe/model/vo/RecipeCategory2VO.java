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
public class RecipeCategory2VO {
    private int category2CD;   // 2차 카테고리 기본키
    private String categoryNM; // 2차 카테고리 이름
}