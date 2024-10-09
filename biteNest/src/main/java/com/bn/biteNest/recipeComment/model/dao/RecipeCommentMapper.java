package com.bn.biteNest.recipeComment.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bn.biteNest.recipeComment.model.vo.CommentLikeVO;
import com.bn.biteNest.recipeComment.model.vo.CommentVO;

@Mapper
public interface RecipeCommentMapper {
    int insertComment(CommentVO comment);	
    int updateComment(CommentVO comment);	
    int deleteComment(int commentCD);		
    List<CommentVO> selectCommentsByRecipe(int recipeCD);
    int deleteCommentsByParent(int parentCommentCD); 
    
    CommentVO selectCommentByCD(int commentCD);
    int countChildComments(int commentCD);
    int deleteCommentsByChlid(int parentCommentCD);
    
    
    int insertCommentLike(CommentLikeVO commentLike);
    int deleteCommentLike(CommentLikeVO commentLike);  
    int countCommentLikes(int commentCD);  
	List<CommentVO> selectChildComments(int commentCD);
    CommentLikeVO selectCommentLikeByUser(@Param("commentCD")int commentCD, @Param("userNO") int userNO);

}