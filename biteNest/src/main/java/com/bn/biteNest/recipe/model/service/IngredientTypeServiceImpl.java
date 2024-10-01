package com.bn.biteNest.recipe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bn.biteNest.recipe.model.dao.IngredientTypeMapper;
import com.bn.biteNest.recipe.model.vo.IngredientTypeVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientTypeServiceImpl implements IngredientTypeService {

    private final IngredientTypeMapper ingredientTypeMapper;

    @Override
    public int insertIngredientType(IngredientTypeVO ingredientTypeVO) {
        return ingredientTypeMapper.insertIngredientType(ingredientTypeVO);
    }
}