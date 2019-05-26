package com.sbt.dao.enrich;

import com.sbt.dao.CountUnitDao;
import com.sbt.dao.IngredientDao;
import com.sbt.entity.CountUnit;
import com.sbt.entity.Ingredient;
import com.sbt.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeEnricherImpl implements RecipeEnricher {
    private  final CountUnitDao countUnitDao;
    private  final IngredientDao ingredientDao;


    @Autowired
    public RecipeEnricherImpl(CountUnitDao countUnitDao, IngredientDao ingredientDao) {
        this.countUnitDao = countUnitDao;
        this.ingredientDao = ingredientDao;
    }

    @Override
    public List<Recipe> enrichAll(List<Recipe> recipes) {
        for (Recipe recipe : recipes)
            enrichOne(recipe);
        return recipes;
    }

    @Override
    public Recipe enrichOne(Recipe recipe) {
        List<Ingredient> ingredientList = ingredientDao.findIngredientsForRecipe(recipe.getId());
        recipe.setListIngredient(ingredientList);

        for (Ingredient ingredient : ingredientList) {
            CountUnit countUnit = countUnitDao.selectById(ingredient.getCountUnit().getId());
            ingredient.setCountUnit(countUnit);
        }

        return recipe;
    }

}
