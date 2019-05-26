package com.sbt.dao;

import com.sbt.entity.Recipe;

public interface RecipeIngredientDao {
    void deleteIngredientsForRecipe(Recipe recipe);
    void insertIngredientsForRecipe(Recipe recipe);
}
