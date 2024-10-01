package com.bn.biteNest.recipe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bn.biteNest.recipe.model.dao.TipMapper;
import com.bn.biteNest.recipe.model.vo.TipVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipServiceImpl implements TipService {

    private final TipMapper tipMapper;

    @Override
    public int insertTip(TipVO tip) {
        return tipMapper.insertTip(tip);
    }


}