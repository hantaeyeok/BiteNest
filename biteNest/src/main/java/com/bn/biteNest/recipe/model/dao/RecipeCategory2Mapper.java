package com.bn.biteNest.recipe.model.dao;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface RecipeCategory2Mapper {
    Integer selectCategory2CDByName(String category2NM);
    String selectCategory2NameByCD(int category2CD);
}
