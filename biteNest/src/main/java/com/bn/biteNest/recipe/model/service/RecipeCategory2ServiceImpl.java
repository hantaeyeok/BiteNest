package com.bn.biteNest.recipe.model.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bn.biteNest.recipe.model.dao.RecipeCategory2Mapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeCategory2ServiceImpl implements RecipeCategory2Service{

	private final RecipeCategory2Mapper category2Mapper;
     	
	@Override
	public Optional<Integer> getCategory2CDByName(String category2NM) {
		return Optional.ofNullable(category2Mapper.selectCategory2CDByName(category2NM));
	}

	@Override
	public Optional<String> getCategory2NameByCD(int category2CD) {
		return Optional.ofNullable(category2Mapper.selectCategory2NameByCD(category2CD));
	}



	
}
