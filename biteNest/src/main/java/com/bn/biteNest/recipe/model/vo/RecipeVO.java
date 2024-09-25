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
    private int recipeCD;               // 레시피 기본키
    private int userNO;                 // 사용자 번호
    private String recipeNM;             // 레시피 제목
    private String recipeDescription;    // 레시피 설명
    private int estimatedTime;          // 예상 소요시간
    private int cookingServings;        // 기준 인분
    private String recipeMainImage;      // 대표 이미지 URL
    private int category1CD;
    private int category2CD;            // 레시피 카테고리 2 기본키
    private Date createdDT;         // 레시피 등록일시
    private Date updatedDT;         // 레시피 수정일시
}
