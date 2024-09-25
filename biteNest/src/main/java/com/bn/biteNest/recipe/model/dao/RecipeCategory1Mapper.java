package com.bn.biteNest.recipe.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bn.biteNest.recipe.model.vo.RecipeCategory1VO;

@Mapper
public interface RecipeCategory1Mapper {
    List<RecipeCategory1VO> selectAllCategory1();
    int selectCategory1CDByName(String category1Name);
}