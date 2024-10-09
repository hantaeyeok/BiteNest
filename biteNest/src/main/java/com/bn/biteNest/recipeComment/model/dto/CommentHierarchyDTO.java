package com.bn.biteNest.recipeComment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentHierarchyDTO {

    private int commentCD;
    private int recipeCD;
    private int userNO;
    private String commentText;
    private int parentCommentCD;
    private String imageURL;
    private String createdDT;
    private String updatedDT;

    // 자식 댓글 목록을 추가하여 계층 구조를 표현
    private List<CommentHierarchyDTO> childComments;
}
