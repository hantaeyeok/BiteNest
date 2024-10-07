package com.bn.biteNest.recipeBookmark.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bn.biteNest.recipeBookmark.model.dao.RecipeBookmarkMapper;
import com.bn.biteNest.recipeBookmark.model.vo.BookmarkVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeBookmarkServiceImpl implements RecipeBookmarkService {

    private final RecipeBookmarkMapper bookmarkMapper;

    @Override
    public boolean addBookmark(BookmarkVO bookmark) {
        return bookmarkMapper.insertBookmark(bookmark) > 0;
    }

    @Override
    public boolean deleteBookmark(int recipeCD, String userId) {
        return bookmarkMapper.deleteBookmark(recipeCD, userId) > 0;
    }

    @Override
    public List<BookmarkVO> getBookmarksByRecipe(int recipeCD) {
        return bookmarkMapper.selectBookmarksByRecipe(recipeCD);
    }

    @Override
    public List<BookmarkVO> getBookmarksByUser(String userID) {
        return bookmarkMapper.selectBookmarksByUser(userID);
    }
}
