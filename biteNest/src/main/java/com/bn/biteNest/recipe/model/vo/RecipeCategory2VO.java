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
public class RecipeCategory2VO {
    private int category2CD;   // 2�� ī�װ� �⺻Ű
    private String category2Name; // 2�� ī�װ� �̸�
}