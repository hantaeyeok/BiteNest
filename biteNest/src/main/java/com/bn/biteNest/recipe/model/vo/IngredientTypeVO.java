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
    private int recipeCD;           // ������ �⺻Ű
    private int ingredientCD;       // ��� ���� ID
    private String ingredientType;  // ��� ���� (�����, ����� ��)
    private String ingredientAmt;   // ��� ��
}
