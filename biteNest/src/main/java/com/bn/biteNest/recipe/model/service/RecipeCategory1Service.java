package com.bn.biteNest.recipe.model.service;

import java.util.List;
import java.util.Optional;

import com.bn.biteNest.recipe.model.vo.RecipeCategory1VO;

public interface RecipeCategory1Service {

	Optional<Integer> getCategory1CDByName(String category1Name);
	Optional<String> getCategory1NameById(int category1CD);
}
