package com.bn.biteNest.recipe.model.service;

import java.util.List;

import com.bn.biteNest.recipe.model.vo.TipVO;

public interface TipService {
    List<TipVO> getAllTips(int recipeCD);
    TipVO getTipById(int tipCD);
    void insertTip(TipVO tip);
    void updateTip(TipVO tip);
    void deleteTip(int tipCD);
}
