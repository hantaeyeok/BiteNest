package com.bn.biteNest.recipe.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bn.biteNest.recipe.model.vo.RecipeCategory1VO;

@Mapper
public interface RecipeCategory1Mapper {
	
	   Integer selectCategory1IdByName(String category1Name);  // �޼��� �̸� ����
	    String selectCategory1NameById(int category1CD);
}