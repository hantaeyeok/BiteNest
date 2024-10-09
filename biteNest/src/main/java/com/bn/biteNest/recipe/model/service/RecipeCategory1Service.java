package com.bn.biteNest.recipe.model.service;


import java.util.Optional;


public interface RecipeCategory1Service {

	Optional<Integer> getCategory1CDByName(String category1NM);
	Optional<String> getCategory1NameByCD(int category1CD);
}
