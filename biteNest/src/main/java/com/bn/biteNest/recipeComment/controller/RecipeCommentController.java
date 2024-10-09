package com.bn.biteNest.recipeComment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bn.biteNest.common.Message;
import com.bn.biteNest.common.ResponseHandler;
import com.bn.biteNest.recipeComment.model.dto.CommentHierarchyDTO;
import com.bn.biteNest.recipeComment.model.service.RecipeCommentService;
import com.bn.biteNest.recipeComment.model.vo.CommentLikeVO;
import com.bn.biteNest.recipeComment.model.vo.CommentVO;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/recipes/comments")
public class RecipeCommentController {

    private final RecipeCommentService commentService;
    private final ResponseHandler responseHandler;
    
    @PostMapping("/add")
    public ResponseEntity<Message> addComment(@RequestBody String commentJson, 
                                              @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            boolean isSuccess = commentService.addComment(commentJson,imageFile);
            return isSuccess ? responseHandler.createResponse("Comments insert successfully", "success", HttpStatus.CREATED)
                             : responseHandler.createResponse("Commnets insert failed", "error", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Commnet created failed: ", e);
            return responseHandler.handleException("Commnet created failed.", e);
        }
    }

    @GetMapping("/recipe/{recipeCD}")
    public ResponseEntity<Message> getCommentsByRecipe(@PathVariable int recipeCD) {
        try {
            List<CommentVO> comments = commentService.getCommentsByRecipe(recipeCD);
            return responseHandler.createResponse("Comments retrieved successfully", comments, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Comment search failed: ", e);
            return responseHandler.createResponse("Comments not found: " + e.getMessage(), null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error during comment search: ", e);
            return responseHandler.handleException("Unexpected error during comment search", e);
        }
    }

    @PutMapping("/update/{commentCD}")
    public ResponseEntity<Message> updateComment(@PathVariable("commentCD") int commentCD, 
    											 @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
									    		 @RequestBody CommentVO comment) {
        try {
            comment.setCommentCD(commentCD);
            boolean isSuccess = commentService.updateComment(comment, imageFile);
            return isSuccess ? responseHandler.createResponse("Comment updated successfully", "success", HttpStatus.OK)
                             : responseHandler.createResponse("Failed to update comment", "error", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            log.error("Comment update failed: ", e);
            return responseHandler.createResponse("Comment not found: " + e.getMessage(), null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error during comment update: ", e);
            return responseHandler.handleException("Unexpected error during comment update", e);
        }
    }

    @DeleteMapping("/delete/{commentCD}")
    public ResponseEntity<Message> deleteComment(@PathVariable("commentCD") int commentCD) {
        try {
            boolean isSuccess = commentService.deleteComment(commentCD);
            return isSuccess ? responseHandler.createResponse("Comment deleted successfully", "success", HttpStatus.OK)
                             : responseHandler.createResponse("Comment not found for deletion", null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Comment deletion failed: ", e);
            return responseHandler.handleException("Comment deletion failed", e);
        }
    }

    @GetMapping("/hierarchy/{recipeCD}")
    public ResponseEntity<Message> getRecipeCommentsHierarchy(@PathVariable("recipeCD") int recipeCD) {
        try {
            List<CommentHierarchyDTO> commentsHierarchy = commentService.getRecipeCommentsHierarchy(recipeCD);
            return responseHandler.createResponse("Comments hierarchy retrieved successfully", commentsHierarchy, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Comments hierarchy search failed: ", e);
            return responseHandler.createResponse("Comments hierarchy not found: " + e.getMessage(), null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error during comments hierarchy search: ", e);
            return responseHandler.handleException("Unexpected error during comments hierarchy search", e);
        }
    }
    
    
    @PostMapping("/like")
    public ResponseEntity<Message> likeComment(@RequestBody CommentLikeVO commentLike) {
    	String result = commentService.likeComment(commentLike);
        switch (result) {
            case "AlreadyLiked":
                return responseHandler.createResponse("You have already liked this comment.", "Already", HttpStatus.OK);
            case "LikedSuccessfully":
                return responseHandler.createResponse("Comment liked successfully.", "success", HttpStatus.OK);
            case "InsertFailed":
            default:
                return responseHandler.createResponse("Failed to add like on the server.", "error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/unlike")
    public ResponseEntity<Message> unlikeComment(@RequestBody CommentLikeVO commentLike) {
        return commentService.unlikeComment(commentLike) 
        		? responseHandler.createResponse("Comment unliked successfully", "success", HttpStatus.OK) 
				: responseHandler.createResponse("Failed to unlike comment", "error", HttpStatus.BAD_REQUEST);
    }

}
