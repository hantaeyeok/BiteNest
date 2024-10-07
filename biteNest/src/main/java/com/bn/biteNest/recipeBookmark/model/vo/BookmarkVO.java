package com.bn.biteNest.recipeBookmark.model.vo;

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
public class BookmarkVO {
    private int bookmarkCD;      // 북마크 코드
    private int recipeCD;        // 레시피 코드
    private String userID;       // 유저 ID
    private String createdDT;    // 생성일시
}
