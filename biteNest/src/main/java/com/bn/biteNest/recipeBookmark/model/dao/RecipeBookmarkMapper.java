package com.bn.biteNest.recipeBookmark.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bn.biteNest.recipeBookmark.model.vo.BookmarkVO;

@Mapper
public interface RecipeBookmarkMapper {

    int insertBookmark(BookmarkVO bookmark); // 북마크 추가
    int deleteBookmark(@Param("recipeCD") int recipeCD,@Param("userID") String userID); // 북마크 삭제
    List<BookmarkVO> selectBookmarksByRecipe(int recipeCD); // 특정 레시피의 북마크 조회
    List<BookmarkVO> selectBookmarksByUser(String userID); // 특정 사용자의 북마크 조회

}
