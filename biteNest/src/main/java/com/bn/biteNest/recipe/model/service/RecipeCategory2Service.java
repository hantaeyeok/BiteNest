package com.bn.biteNest.recipe.model.service;

import java.util.Optional;

public interface RecipeCategory2Service {

	Optional<Integer> getCategory2CDByName(String category2NM);
	Optional<String> getCategory2NameByCD(int category2cd);
}
