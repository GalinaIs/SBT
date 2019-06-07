package com.sbt.dao.enrich;

import com.sbt.entity.Recipe;

import java.util.List;

public interface RecipeEnricher {
    List<Recipe> enrichAll(List<Recipe> recipes);
    Recipe enrichOne(Recipe recipe);
}
