package com.bn.biteNest.recipe.model.service;

import java.util.List;
import java.util.Optional;

import com.bn.biteNest.recipe.model.vo.RecipeCategory2VO;

public interface RecipeCategory2Service {

	Optional<Integer> getCategory2CDByName(String category2Name);
	Optional<String> getCategory2NameById(int category2cd);
}
