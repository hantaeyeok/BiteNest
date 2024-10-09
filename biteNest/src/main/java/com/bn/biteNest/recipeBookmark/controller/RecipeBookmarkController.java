package com.bn.biteNest.recipeBookmark.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bn.biteNest.common.Message;
import com.bn.biteNest.common.ResponseHandler;
import com.bn.biteNest.recipeBookmark.model.service.RecipeBookmarkService;
import com.bn.biteNest.recipeBookmark.model.vo.BookmarkVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recipes/bookmarks")
@CrossOrigin("*")
public class RecipeBookmarkController {

    private final RecipeBookmarkService bookmarkService;
    private final ResponseHandler responseHandler;

    // 1. 북마크 추가
    @PostMapping("/add")
    public ResponseEntity<Message> addBookmark(@RequestBody BookmarkVO bookmark) {
        boolean isSuccess = bookmarkService.addBookmark(bookmark);
        return isSuccess ? responseHandler.createResponse("Bookmark added successfully", "success", HttpStatus.CREATED)
                         : responseHandler.createResponse("Failed to add bookmark", "error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 2. 레시피 ID로 북마크 조회
    @GetMapping("/recipe/{recipeCD}")
    public ResponseEntity<Message> getBookmarksByRecipe(@PathVariable("recipeCD") int recipeCD) {
        List<BookmarkVO> bookmarks = bookmarkService.getBookmarksByRecipe(recipeCD);
        log.info("Fetched bookmarks: {}", bookmarks); // 추가된 로그

        return responseHandler.createResponse("Bookmarks fetched successfully", bookmarks, HttpStatus.OK);
    }

    // 3. 유저별 북마크 조회
    @GetMapping("/user/{userID}")
    public ResponseEntity<Message> getBookmarksByUser(@PathVariable("userID") String userID) {
        List<BookmarkVO> bookmarks = bookmarkService.getBookmarksByUser(userID);
        return responseHandler.createResponse("Bookmarks fetched successfully", bookmarks, HttpStatus.OK);
    }

    // 4. 북마크 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<Message> deleteBookmark(@RequestBody BookmarkVO markVO) {
        boolean isSuccess = bookmarkService.deleteBookmark(markVO.getRecipeCD(), markVO.getUserID());
        return isSuccess ? responseHandler.createResponse("Bookmark deleted successfully", "success", HttpStatus.OK)
                         : responseHandler.createResponse("Failed to delete bookmark", "error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
