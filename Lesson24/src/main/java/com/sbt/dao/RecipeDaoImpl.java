package com.sbt.dao;

import com.sbt.dao.enrich.RecipeEnricher;
import com.sbt.entity.Recipe;
import com.sbt.exception.NoUniqueEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class RecipeDaoImpl extends AbstractDao<Recipe> implements RecipeDao {
    private static final String SELECT_BY_ID = "select id, name, description from recipe where id = :id";
    private static final String SELECT_ALL = "select id, name, description from recipe";
    private static final String DELETE_BY_ID = "delete from recipe where id = :id";
    private static final String INSERT = "INSERT INTO recipe (name, description) VALUES (:name, :description)";
    private static final String UPDATE = "UPDATE recipe SET name = :name, description = :description WHERE id = :id";
    private static final String CHECK_UNIQUE = "select id, name, description from recipe where name = :name and " +
            "description = :description";
    private static final String SELECT_BY_NAME = "select id, name, description from recipe where lower(name) like lower(:name)";

    private final RowMapper<Recipe> rowMapper = (resultSet, i) ->
            new Recipe(resultSet.getLong("id"), resultSet.getString("name"),
                    resultSet.getString("description"));

    private final RecipeIngredientDao recipeIngredientDao;
    private final RecipeEnricher enricher;

    @Autowired
    public RecipeDaoImpl(DataSource dataSource, RecipeIngredientDao recipeIngredientDao, RecipeEnricher enricher) {
        super(dataSource);
        this.recipeIngredientDao = recipeIngredientDao;
        this.enricher = enricher;
    }

    @Override
    public Recipe selectById(long id) {
        Recipe recipe = selectById(id, SELECT_BY_ID, rowMapper);
        return enricher.enrichOne(recipe);
    }

    @Override
    public List<Recipe> selectAll() {
        List<Recipe> recipeList = selectAll(SELECT_ALL, rowMapper);
        return enricher.enrichAll(recipeList);
    }

    @Override
    public void insert(Recipe entity) throws NoUniqueEntityException {
        long id = insert(entity, INSERT, CHECK_UNIQUE, rowMapper).getId();
        entity.setId(id);

        recipeIngredientDao.insertIngredientsForRecipe(entity);
    }

    @Override
    public void update(Recipe entity) throws NoUniqueEntityException {
        update(entity, UPDATE, CHECK_UNIQUE, rowMapper);
    }

    @Override
    public void delete(Recipe entity) {
        recipeIngredientDao.deleteIngredientsForRecipe(entity);
        delete(entity.getId(), DELETE_BY_ID);
    }

    @Override
    public List<Recipe> findRecipesByName(String name) {
        name = "%" + name + "%";
        List<Recipe> recipeList = jdbc.query(SELECT_BY_NAME, new MapSqlParameterSource("name", name), rowMapper);
        return enricher.enrichAll(recipeList);
    }
}
