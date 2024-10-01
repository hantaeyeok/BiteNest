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
	public Optional<Integer> getCategory2CDByName(String category2Name) {
		Integer category2CD = category2Mapper.selectCategory2CDByName(category2Name);
		return Optional.ofNullable(category2CD); 
	}

	@Override
    public Optional<String> getCategory2NameById(int category2CD) {
        return Optional.ofNullable(category2Mapper.selectCategory2NameById(category2CD));
    }



	
}
