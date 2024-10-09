package com.bn.biteNest.recipe.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientDTO {

	private int ingredientCD;
	private String ingredientNM;
	private String ingredientAmt;
	private String ingredientType;

}
