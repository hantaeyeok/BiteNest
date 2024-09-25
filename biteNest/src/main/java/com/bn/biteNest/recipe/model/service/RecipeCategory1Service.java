package com.bn.biteNest.recipe.model.service;

import java.util.List;

import com.bn.biteNest.recipe.model.vo.RecipeCategory1VO;

public interface RecipeCategory1Service {

    List<RecipeCategory1VO> getAllCategory1();
    int getCategory1CDByName(String category1Name);
}
