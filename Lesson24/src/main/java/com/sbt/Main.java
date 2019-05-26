package com.sbt;

import com.sbt.config.Config;
import com.sbt.entity.CountUnit;
import com.sbt.entity.Ingredient;
import com.sbt.entity.Recipe;
import com.sbt.exception.DaoException;
import com.sbt.exception.NoUniqueEntityException;
import com.sbt.service.RecipeService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final static String helloMessageForUser = "Добро пожаловать в приложение по работе с рецептами";
    private final static String messageForUser = "Для работы с приложением выберите одну из следующих команд:\n" +
            "'find' - поиск рецепта\n" +
            "'add' - добавить новый рецепт\n" +
            "'delete' - удалить существующий рецепт\n" +
            "'quit' - выход";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws DaoException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        RecipeService recipeService = context.getBean(RecipeService.class);
        consoleApplication(recipeService);
    }

    private static void consoleApplication(RecipeService recipeService) {
        System.out.println(helloMessageForUser);

        loop:
        while (true) {
            System.out.println(messageForUser);
            String answerUser = scanner.nextLine().toLowerCase();
            switch (answerUser) {
                case "find":
                    findRecipe(recipeService);
                    break;
                case "add":
                    addRecipe(recipeService);
                    break;
                case "delete":
                    deleteRecipe(recipeService);
                    break;
                case "quit":
                    System.out.println("Вы вышли из программы");
                    break loop;
                default:
                    System.out.println("Вы ввели неизвестную команду");
            }
        }
    }

    private static void deleteRecipe(RecipeService recipeService) {
        List<Recipe> recipes = findRecipe(recipeService);
        System.out.println("Вы хотите удалить все найденные рецепты? Для отмены введите 'no'");
        String answerUser = scanner.nextLine().toLowerCase();
        if (answerUser.equals("no")) {
            System.out.println("Удаление рецептов было отменено");
            return;
        }
        recipeService.deleteRecipe(recipes);
        System.out.println("Удаление рецептов прошло успешно");
    }

    private static Recipe giveFromUserRecipe() {
        System.out.println("Введите название рецепта");
        String nameRecipe = scanner.nextLine();
        boolean contin = true;
        List<Ingredient> ingredientList = new ArrayList<>();
        while(contin) {
            System.out.println("Введите название ингредиента или 'q' для прекращения ввода ингредиентов");
            String nameIng = scanner.nextLine();
            if (nameIng.toLowerCase().equals("q")) {
                contin = false;
                break;
            }
            System.out.println("Введите количество ингредиента");
            long count;
            try {
                count = Long.valueOf(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Необходимо было ввести целое число");
                break;
            }
            System.out.println("Введите единицу измерения ингредиента");
            String countUnitName = scanner.nextLine();

            ingredientList.add(new Ingredient(nameIng, count, new CountUnit(countUnitName)));
        }
        System.out.println("Введите описание рецепта");
        String descriptionRecipe = scanner.nextLine();
        return new Recipe(nameRecipe, ingredientList, descriptionRecipe);
    }

    private static void addRecipe(RecipeService recipeService) {
        Recipe recipe = giveFromUserRecipe();
        try {
            recipeService.addRecipe(recipe);
            System.out.println("Рецепт добавлен успешно");
        } catch (NoUniqueEntityException e) {
            System.out.println("Данный рецепт невозможно добавить, т.к. рецепт с названием " + recipe.getName() +
                    " уже существует");
        }
    }

    private static List<Recipe> findRecipe(RecipeService recipeService) {
        System.out.println("Введите название рецепта или часть названия для поиска");
        String nameRecipe = scanner.nextLine();
        System.out.println("По Вашему запросу найдено:");
        List<Recipe> recipes = recipeService.getRecipesByName(nameRecipe);
        System.out.println(recipes);
        return recipes;
    }
}
