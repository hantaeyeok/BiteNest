package com.bn.biteNest.recipe.model.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.bn.biteNest.recipe.model.dao.RecipeCategory1Mapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeCategory1ServiceImpl implements RecipeCategory1Service{

	private final RecipeCategory1Mapper category1Mapper;
    
	 	
//	@Override
//	public Optional<Integer> getCategory1CDByName(String category1Name) {
//		Integer category1CD = category1Mapper.selectCategory1CDByName(category1Name);
//		return Optional.ofNullable(category1CD); 
//	}
//	
	@Override
	public Optional<Integer> getCategory1CDByName(String category1Name) {
		Integer category1CD = category1Mapper.selectCategory1IdByName(category1Name);
		return Optional.ofNullable(category1CD); 
	}

	@Override
    public Optional<String> getCategory1NameById(int category1CD) {
        return Optional.ofNullable(category1Mapper.selectCategory1NameById(category1CD));
    }

	

	
}
