package com.sbt.entity;

import java.util.List;

public class Recipe {
    private long id;
    private String name;
    private List<Ingredient> listIngredient;
    private String description;

    public Recipe() {
    }

    public Recipe(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Recipe(String name, List<Ingredient> listIngredient, String description) {
        this.name = name;
        this.listIngredient = listIngredient;
        this.description = description;
    }

    public Recipe(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getListIngredient() {
        return listIngredient;
    }

    public void setListIngredient(List<Ingredient> listIngredient) {
        this.listIngredient = listIngredient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Рецепт номер: " + id +
                "\n\t Название: " + name +
                "\n\t Список ингредиентов" + listIngredient +
                "\n\t Описание рецепта: " + description + "\n";
    }
}
