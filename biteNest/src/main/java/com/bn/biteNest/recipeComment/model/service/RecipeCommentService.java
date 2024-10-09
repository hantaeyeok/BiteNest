package com.bn.biteNest.recipeComment.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bn.biteNest.recipeComment.model.dto.CommentHierarchyDTO;
import com.bn.biteNest.recipeComment.model.vo.CommentLikeVO;
import com.bn.biteNest.recipeComment.model.vo.CommentVO;


public interface RecipeCommentService {

    boolean addComment(String commentJson, MultipartFile imageFile);
    boolean updateComment(CommentVO comment, MultipartFile imageFile);
    boolean deleteComment(int commentCD);
    List<CommentHierarchyDTO> getRecipeCommentsHierarchy(int recipeCD);
    
    String likeComment(CommentLikeVO commentLike);
    boolean unlikeComment(CommentLikeVO commentLike);
    int getCommentLikes(int commentCD);
    
    List<CommentVO> getCommentsByRecipe(int recipeCD);
}