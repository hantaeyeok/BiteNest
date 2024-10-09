package com.bn.biteNest.recipe.model.dao;


import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface RecipeCategory1Mapper {
	Integer selectCategory1CDByName(String category1NM); 
	String selectCategory1NameByCD(int category1CD);
}
	