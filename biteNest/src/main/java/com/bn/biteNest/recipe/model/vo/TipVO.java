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
    private int tipCD;         // �� �⺻Ű
    private int recipeCD;      // ������ �⺻Ű
    private String tipContent; // �� ����
    private int tipORD;        // �� ����
}