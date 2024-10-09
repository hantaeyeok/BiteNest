package com.bn.biteNest.recipeComment.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bn.biteNest.common.RecipeFileDelete;
import com.bn.biteNest.common.RecipeFileUpload;
import com.bn.biteNest.recipeComment.model.dao.RecipeCommentMapper;
import com.bn.biteNest.recipeComment.model.dto.CommentHierarchyDTO;
import com.bn.biteNest.recipeComment.model.vo.CommentLikeVO;
import com.bn.biteNest.recipeComment.model.vo.CommentVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeCommentServiceImpl implements RecipeCommentService {

    private final RecipeCommentMapper commentMapper;
    private final RecipeFileUpload fileUpload;
    private final RecipeFileDelete fileDelete;
    
    @Override
    public boolean addComment(String commentJson, MultipartFile imageFile) { 
        ObjectMapper objectMapper = new ObjectMapper();
        CommentVO comment = null;

        try {
            comment = objectMapper.readValue(commentJson, CommentVO.class);  // JSON 문자열을 CommentVO로 변환
        } catch (JsonProcessingException e) {
            log.error("JSON 처리 오류: ", e);
            return false; // JSON 처리 오류가 발생하면 false 반환
        }
        
        if (comment == null) {
            log.error("변환된 CommentVO 객체가 null입니다."); // null 체크 추가
            return false; // 변환된 객체가 null인 경우 false 반환
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = fileUpload.saveImage(imageFile, comment.getCommentCD()+"", comment.getParentCommentCD()); // 이미지 저장
            comment.setImageURL(imagePath); // 저장된 이미지 경로 설정
        } else {
        	comment.setImageURL(null);
        }
        
        return commentMapper.insertComment(comment) > 0; // 댓글 추가 결과 반환
    }
       
    @Override
    public boolean updateComment(CommentVO comment, MultipartFile imageFile) {
    	CommentVO existingComment = commentMapper.selectCommentByCD(comment.getCommentCD());
        String oldImagePath = existingComment.getImageURL();
       
        String newImagePath = (imageFile != null && !imageFile.isEmpty())
                ? fileUpload.saveImage(imageFile, comment.getRecipeCD() + "", comment.getParentCommentCD())
                : oldImagePath;

        if (imageFile != null && !imageFile.isEmpty() && oldImagePath != null && !oldImagePath.isEmpty()) {
            fileDelete.deleteFile(oldImagePath);
        }
        
        existingComment.setCommentText(comment.getCommentText()); // 댓글 내용 업데이트
        existingComment.setImageURL(newImagePath); 
        
        return commentMapper.updateComment(existingComment) > 0; 
    }
    
    @Transactional
    @Override
    public boolean deleteComment(int commentCD) {
        int childCommentCount = commentMapper.countChildComments(commentCD);
        if (childCommentCount > 0) {
        	deleteCommentsByChlid(commentCD); 
        }
        return commentMapper.deleteComment(commentCD) > 0;
    }
    
    public boolean deleteCommentsByChlid(int parentCommentCD) {
    	return commentMapper.deleteCommentsByChlid(parentCommentCD) > 0;
    }

    @Override
    public List<CommentHierarchyDTO> getRecipeCommentsHierarchy(int recipeCD) {
        List<CommentVO> allComments = commentMapper.selectCommentsByRecipe(recipeCD);
        Map<Integer, CommentHierarchyDTO> commentMap = new HashMap<>();
        
        for (CommentVO comment : allComments) {
            CommentHierarchyDTO commentDTO = convertToDTO(comment);
            commentMap.put(comment.getCommentCD(), commentDTO);
        }

        List<CommentHierarchyDTO> result = new ArrayList<>();
        for (CommentVO comment : allComments) {
            if (comment.getParentCommentCD() == 0) {
                result.add(commentMap.get(comment.getCommentCD()));
            } else {
                CommentHierarchyDTO parentComment = commentMap.get(comment.getParentCommentCD());
                if (parentComment != null) {
                    parentComment.getChildComments().add(commentMap.get(comment.getCommentCD()));
                }
            }
        }
        return result;
    }

    
    
    @Override
    public String likeComment(CommentLikeVO commentLike) {
    	
        CommentLikeVO existingLike = commentMapper.selectCommentLikeByUser(commentLike.getCommentCD(), commentLike.getUserNO());
        if (existingLike != null) {
            return "AlreadyLiked"; 
        }

        // 좋아요 추가 시도
        boolean isSuccess = commentMapper.insertCommentLike(commentLike) > 0;
        return isSuccess ? "LikedSuccessfully" : "InsertFailed"; 
    }
   
    @Override
    public boolean unlikeComment(CommentLikeVO commentLike) {
        return commentMapper.deleteCommentLike(commentLike) > 0;
    }

    @Override
    public int getCommentLikes(int commentCD) {
        return commentMapper.countCommentLikes(commentCD);
    }

    @Override
    public List<CommentVO> getCommentsByRecipe(int recipeCD) {
        return commentMapper.selectCommentsByRecipe(recipeCD);
    }

    // VO를 DTO로 변환하는 메소드
    private CommentHierarchyDTO convertToDTO(CommentVO comment) {
        return CommentHierarchyDTO.builder()
				                  .commentCD(comment.getCommentCD())
				                  .recipeCD(comment.getRecipeCD())
				                  .userNO(comment.getUserNO())
				                  .commentText(comment.getCommentText())
				                  .parentCommentCD(comment.getParentCommentCD())
				                  .imageURL(comment.getImageURL())
				                  .createdDT(comment.getCreatedDT())
				                  .updatedDT(comment.getUpdatedDT())
				                  .childComments(new ArrayList<>()) //초기화
				                  .build();
    }
    
    
    
}
