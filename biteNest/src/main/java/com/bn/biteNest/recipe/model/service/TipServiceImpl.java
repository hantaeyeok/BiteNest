package com.bn.biteNest.recipe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bn.biteNest.recipe.model.dao.TipMapper;
import com.bn.biteNest.recipe.model.vo.TipVO;

@Service
public class TipServiceImpl implements TipService {

    @Autowired
    private TipMapper tipMapper;

    @Override
    public List<TipVO> getAllTips(int recipeCD) {
        return tipMapper.getAllTips(recipeCD);
    }

    @Override
    public TipVO getTipById(int tipCD) {
        return tipMapper.getTipById(tipCD);
    }

    @Override
    public void insertTip(TipVO tip) {
        tipMapper.insertTip(tip);
    }

    @Override
    public void updateTip(TipVO tip) {
        tipMapper.updateTip(tip);
    }

    @Override
    public void deleteTip(int tipCD) {
        tipMapper.deleteTip(tipCD);
    }
}