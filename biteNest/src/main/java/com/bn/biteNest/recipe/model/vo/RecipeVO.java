package com.bn.biteNest.recipe.model.vo;

import java.sql.Date;

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
public class RecipeVO {
    private int recipeCD;               // ������ �⺻Ű
    private int userNO;                 // ����� ��ȣ
    private String recipeNM;             // ������ ����
    private String recipeDescription;    // ������ ����
    private int estimatedTime;          // ���� �ҿ�ð�
    private int cookingServings;        // ���� �κ�
    private String recipeMainImage;      // ��ǥ �̹��� URL
    private int category1CD;
    private int category2CD;            // ������ ī�װ� 2 �⺻Ű
    private Date createdDT;         // ������ ����Ͻ�
    private Date updatedDT;         // ������ �����Ͻ�
}
