package com.bn.biteNest.recipe.model.dao;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface RecipeCategory2Mapper {
    Integer selectCategory2CDByName(String category2Name);
    String selectCategory2NameById(int category2CD);
}
