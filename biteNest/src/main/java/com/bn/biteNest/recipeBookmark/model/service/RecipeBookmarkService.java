package com.bn.biteNest.recipeBookmark.model.service;

import java.util.List;

import com.bn.biteNest.recipeBookmark.model.vo.BookmarkVO;

public interface RecipeBookmarkService {

    boolean addBookmark(BookmarkVO bookmark);
    boolean deleteBookmark(int recipeCD, String userId);
    List<BookmarkVO> getBookmarksByRecipe(int recipeCD);
    List<BookmarkVO> getBookmarksByUser(String userId);
}
