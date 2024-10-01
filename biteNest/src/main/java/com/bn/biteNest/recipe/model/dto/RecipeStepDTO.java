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
public class RecipeStepDTO {
    private int stepOrder;                 // ���� �ܰ� ����
    private String instruction;            // ���� �ܰ� ����
    private String imageURL;               // ���� �ܰ� �̹��� ���
}