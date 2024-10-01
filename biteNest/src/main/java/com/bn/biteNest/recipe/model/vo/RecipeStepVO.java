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
public class RecipeStepVO {
    private int stepCD;             // �����ܰ� �⺻Ű
    private int recipeCD;           // ������ �⺻Ű
    private int stepORD;            // �����ܰ� ����
    private String instruction;     // �����ܰ� ����
    private String imageURL;        // �ܰ躰 �̹��� URL (����)
}