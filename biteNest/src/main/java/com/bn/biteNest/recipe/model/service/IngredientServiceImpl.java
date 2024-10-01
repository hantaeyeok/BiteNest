package com.bn.biteNest.recipe.model.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bn.biteNest.recipe.model.dao.IngredientMapper;
import com.bn.biteNest.recipe.model.vo.IngredientVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientMapper ingredientMapper;
    
    @Override
    public Optional<IngredientVO> findByName(String ingredientName) {
        return Optional.ofNullable(ingredientMapper.selectByName(ingredientName));
    }
    
    @Override
    public int saveNewIngredient(String ingredientName) {
        IngredientVO newIngredient = IngredientVO.builder().ingredientNM(ingredientName).build();

        if (ingredientMapper.insertIngredient(newIngredient) <= 0) {
            throw new RuntimeException("Failed to insert new ingredient: " + ingredientName);
        }

        return newIngredient.getIngredientCD();
    }
}