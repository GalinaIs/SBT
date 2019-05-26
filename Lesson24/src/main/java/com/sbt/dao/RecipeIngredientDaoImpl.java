package com.sbt.dao;

import com.sbt.entity.CountUnit;
import com.sbt.entity.Ingredient;
import com.sbt.entity.Recipe;
import com.sbt.exception.NoUniqueEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class RecipeIngredientDaoImpl extends AbstractDao<Recipe> implements RecipeIngredientDao {
    private static final String INSERT_ING_FOR_RECIPE = "insert into recipe_Ingredient (id_recipe, id_ingredient, count," +
            "id_countUnit) values (:id_recipe, :id_ingredient, :count, :id_countUnit)";
    private static final String DELETE_ING_FOR_RECIPE = "delete from recipe_Ingredient where id_recipe = :id_recipe";

    private final IngredientDao ingredientDao;
    private final CountUnitDao countUnitDao;

    @Autowired
    public RecipeIngredientDaoImpl(DataSource dataSource, IngredientDao ingredientDao, CountUnitDao countUnitDao) {
        super(dataSource);
        this.ingredientDao = ingredientDao;
        this.countUnitDao = countUnitDao;
    }

    @Override
    public void deleteIngredientsForRecipe(Recipe recipe) {
        jdbc.update(DELETE_ING_FOR_RECIPE, new MapSqlParameterSource("id_recipe", recipe.getId()));
    }

    @Override
    public void insertIngredientsForRecipe(Recipe recipe) {
        insertListIng(recipe.getListIngredient());
        insertIngForRecipe(recipe);
    }

    private void insertListIng(List<Ingredient> ingredientList) {
        for (Ingredient ingredient : ingredientList) {
            try {
                ingredientDao.insert(ingredient);
            } catch (NoUniqueEntityException ex) {
                long idIngr = ingredientDao.findIngredientByName(ingredient.getName()).getId();
                ingredient.setId(idIngr);
            } finally {
                insertCountUnit(ingredient.getCountUnit());
            }
        }
    }

    private void insertCountUnit(CountUnit countUnit) {
        try {
            countUnitDao.insert(countUnit);
        } catch (NoUniqueEntityException e) {
            long idIngr = countUnitDao.findCountUnitByName(countUnit.getName()).getId();
            countUnit.setId(idIngr);
        }
    }

    private void insertIngForRecipe(Recipe recipe) {
        for (Ingredient ingredient : recipe.getListIngredient())
            jdbc.update(INSERT_ING_FOR_RECIPE, new MapSqlParameterSource("id_recipe", recipe.getId())
                    .addValue("id_ingredient", ingredient.getId())
                    .addValue("count", ingredient.getCount())
                    .addValue("id_countUnit", ingredient.getCountUnit().getId()));
    }
}
