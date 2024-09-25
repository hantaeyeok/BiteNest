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
    private int tipCD;         // �� �⺻Ű
    private int recipeCD;      // ������ �⺻Ű
    private String tipContent; // �� ����
    private int tipORD;        // �� ����
}