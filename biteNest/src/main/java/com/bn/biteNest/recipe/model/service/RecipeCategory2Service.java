package com.bn.biteNest.recipe.model.service;

import java.util.List;

import com.bn.biteNest.recipe.model.vo.RecipeCategory2VO;

public interface RecipeCategory2Service {

    List<RecipeCategory2VO> getAllCategory2();
    int getCategory2CDByName(String category2Name);
}
