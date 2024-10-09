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
public class CommentLikeVO {
	private int likeCD; // Like 기본키
    private int commentCD; // 댓글 기본키
    private int userNO; // 유저 번호
    private String likedDT; // 좋아요한 날짜
}
