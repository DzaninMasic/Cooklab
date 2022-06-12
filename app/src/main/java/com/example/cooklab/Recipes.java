package com.example.cooklab;

import java.util.ArrayList;

public class Recipes {

    private String RecipeName;
    private ArrayList<String> RecipeIngredients;
    private String RecipeMethodTitle;
    private String Recipe;
    private int Thumbnail;


    public Recipes(String name, ArrayList<String> recipeIngredients, String recipeMethodTitle,String recipe, int thumbnail){

        RecipeName = name;
        RecipeIngredients = recipeIngredients;
        RecipeMethodTitle = recipeMethodTitle;
        Recipe = recipe;
        Thumbnail = thumbnail;

    }


    public  String getRecipeName() {

        return RecipeName;
    }
    public ArrayList<String> getRecipeIngredients(){
        return RecipeIngredients;
    }

    public String getRecipeMethodTitle(){
        return RecipeMethodTitle;
    }

    public String getRecipe(){
        return Recipe;
    }

    public int getThumbnail(){
        return Thumbnail;
    }
}
