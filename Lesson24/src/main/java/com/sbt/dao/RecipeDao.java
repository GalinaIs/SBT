package com.sbt.dao;

import com.sbt.entity.Recipe;

import java.util.List;

public interface RecipeDao extends Dao<Recipe> {
    List<Recipe> findRecipesByName(String name);
}
