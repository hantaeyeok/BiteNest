package com.bn.biteNest.recipe.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bn.biteNest.recipe.model.vo.RecipeCategory2VO;

@Mapper
public interface RecipeCategory2Mapper {
    List<RecipeCategory2VO> selectAllCategory2();
    int selectCategory2CDByName(String category2Name);
}
