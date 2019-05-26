package com.sbt.dao;

import com.sbt.entity.CountUnit;
import com.sbt.entity.Ingredient;
import com.sbt.exception.NoUniqueEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class IngredientDaoImpl extends AbstractDao<Ingredient> implements IngredientDao {
    private static final String SELECT_BY_ID = "select id, name from Ingredient where id = :id";
    private static final String SELECT_ALL = "select id, name from Ingredient";
    private static final String DELETE_BY_ID = "delete from Ingredient where id = :id";
    private static final String INSERT = "INSERT INTO Ingredient (name) VALUES (:name)";
    private static final String UPDATE = "UPDATE Ingredient SET name = :name WHERE id = :id";
    private static final String SELECT_BY_NAME = "select id, name from Ingredient where name = :name";
    private static final String SELECT_ALL_FOR_RECIPE = "select Ingredient.id, Ingredient.name, A.count, A.id_countUnit " +
            "from Ingredient INNER JOIN (select * from recipe_Ingredient where id_recipe = :idRecipe) As A " +
            "on Ingredient.id = A.id_ingredient";

    private final RowMapper<Ingredient> rowMapper = (resultSet, i) ->
            new Ingredient(resultSet.getLong("id"), resultSet.getString("name"));

    @Autowired
    protected IngredientDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Ingredient selectById(long id) {
        return selectById(id, SELECT_BY_ID, rowMapper);
    }

    @Override
    public List<Ingredient> selectAll() {
        return selectAll(SELECT_ALL, rowMapper);
    }

    @Override
    public void insert(Ingredient entity) throws NoUniqueEntityException {
        long id = insert(entity, INSERT, SELECT_BY_NAME, rowMapper).getId();
        entity.setId(id);
    }

    @Override
    public void update(Ingredient entity) throws NoUniqueEntityException {
        update(entity, UPDATE, SELECT_BY_NAME, rowMapper);
    }

    @Override
    public void delete(Ingredient entity) {
        delete(entity.getId(), DELETE_BY_ID);
    }

    @Override
    public Ingredient findIngredientByName(String name) {
        return findByName(name, SELECT_BY_NAME, rowMapper);
    }

    @Override
    public List<Ingredient> findIngredientsForRecipe(long idRecipe) {
        return jdbc.query(SELECT_ALL_FOR_RECIPE, new MapSqlParameterSource("idRecipe", idRecipe),
                (resultSet, i) -> {
                    CountUnit countUnit = new CountUnit(resultSet.getLong("id_countUnit"));

                    return new Ingredient(resultSet.getLong("id"), resultSet.getString("name"),
                            resultSet.getLong("count"), countUnit);
                });
    }
}
