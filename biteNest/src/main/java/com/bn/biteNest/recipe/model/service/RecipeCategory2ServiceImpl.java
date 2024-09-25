package com.bn.biteNest.recipe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bn.biteNest.recipe.model.dao.RecipeCategory2Mapper;
import com.bn.biteNest.recipe.model.vo.RecipeCategory2VO;

@Service
public class RecipeCategory2ServiceImpl implements RecipeCategory2Service {

    @Autowired
    private RecipeCategory2Mapper recipeCategory2Mapper;

    @Override
    public List<RecipeCategory2VO> getAllCategory2() {
        return recipeCategory2Mapper.selectAllCategory2();
    }

    @Override
    public int getCategory2CDByName(String category2Name) {
        return recipeCategory2Mapper.selectCategory2CDByName(category2Name);
    }
}