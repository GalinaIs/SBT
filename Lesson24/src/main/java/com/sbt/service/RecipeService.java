package com.sbt.service;

import com.sbt.dao.RecipeDao;
import com.sbt.entity.Recipe;
import com.sbt.exception.NoUniqueEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
public class RecipeService {
    private final RecipeDao recipeDao;

    @Autowired
    public RecipeService(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    @Transactional(readOnly = true, isolation = READ_COMMITTED, propagation = REQUIRED)
    public List<Recipe> getAllRecipes() {
        return recipeDao.selectAll();
    }

    @Transactional(readOnly = true, isolation = READ_COMMITTED, propagation = REQUIRED)
    public List<Recipe> getRecipesByName(String name) {
        return recipeDao.findRecipesByName(name);
    }

    @Transactional(isolation = READ_COMMITTED, propagation = REQUIRED, rollbackFor = {Throwable.class})
    public void deleteRecipe(List<Recipe> recipes) {
        for (Recipe recipe : recipes)
            recipeDao.delete(recipe);
    }

    @Transactional(isolation = READ_COMMITTED, propagation = REQUIRED, rollbackFor = {Throwable.class})
    public void addRecipe(Recipe recipe) throws NoUniqueEntityException {
        recipeDao.insert(recipe);
    }
}
