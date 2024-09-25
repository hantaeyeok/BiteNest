package com.bn.biteNest.recipe.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bn.biteNest.recipe.model.vo.TipVO;

@Mapper
public interface TipMapper {
    List<TipVO> getAllTips(int recipeCD);
    TipVO getTipById(int tipCD);
    void insertTip(TipVO tip);
    void updateTip(TipVO tip);
    void deleteTip(int tipCD);
}
