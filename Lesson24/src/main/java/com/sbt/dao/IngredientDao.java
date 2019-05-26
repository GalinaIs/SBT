package com.sbt.dao;

import com.sbt.entity.Ingredient;

import java.util.List;

public interface IngredientDao extends Dao<Ingredient> {
    Ingredient findIngredientByName(String name);
    List<Ingredient> findIngredientsForRecipe(long idRecipe);
}
