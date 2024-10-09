package com.bn.biteNest.recipeComment.model.vo;

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
public class CommentVO {
    private int commentCD;
    private int recipeCD;
    private int userNO;
    private String commentText;
    private int parentCommentCD;
    private String imageURL;
    private String createdDT;
    private String updatedDT;
}