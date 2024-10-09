package com.bn.biteNest.recipe.model.service;

import java.util.List;
import java.util.Map;

import com.bn.biteNest.recipe.model.vo.TipVO;

public interface TipService {
	boolean insertTip(TipVO tip);
    boolean updateTipsByRecipe(int recipeCD, List<Map<String, String>> tips);
    List<TipVO> getTipsByRecipeId(int recipeCD);
    boolean deleteTipsByRecipeCD(int recipeCD);
}
