package com.github.NuclearDonut47.AlathraFishing.recipes;

import com.github.NuclearDonut47.AlathraFishing.config.Config;
import com.github.NuclearDonut47.AlathraFishing.items.generators.CustomTools;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class Recipes {
    private static RecipeBuilder recipeBuilder;
    private static ConfigurationSection recipesSection;
    private static CustomTools tools;

    public Recipes(Plugin plugin, Config config, CustomTools toolsInstance) {
        recipeBuilder = new RecipeBuilder(plugin);
        recipesSection = config.getRecipesSection();
        tools = toolsInstance;
    }

    public void addRecipes() {
        for (String recipePath : recipesSection.getConfigurationSection("crafting").getKeys(false)) {
            ConfigurationSection recipe = recipesSection.getConfigurationSection("crafting." + recipePath);

            if (!recipe.getBoolean("add")) continue;

            if (recipe.getBoolean("isShaped")) {
                buildShaped(recipe);
                continue;
            }

            buildShapeless(recipe);
        }

        if (recipesSection.getBoolean("remove_vanilla.fishing_rod"))
            recipeBuilder.destroy("minecraft", "fishing_rod");
    }

    public void buildShaped(ConfigurationSection recipeInfo) {
        ItemStack result = tools.getTool(recipeInfo.getName());
        String[] shape = recipeInfo.getStringList("shape").toArray(new String[0]);
        HashMap<Character, RecipeChoice> ingredients = new HashMap<>();

        for (String ingredientPath : recipeInfo.getConfigurationSection("ingredients").getKeys(false)) {
            char character = recipeInfo.getConfigurationSection("ingredients." + ingredientPath)
                    .getString("character").charAt(0);

            RecipeChoice ingredient = new RecipeChoice.MaterialChoice(Material.valueOf(
                    recipeInfo.getConfigurationSection("ingredients." + ingredientPath).getString("item")));

            ingredients.put(character, ingredient);
        }

        if (result == null) return;

        if (shape.length == 0) return;

        if (ingredients.isEmpty()) return;

        recipeBuilder.create(true, recipeInfo.getName(), result).setShape(shape).setIngredients(ingredients)
                .build();
    }

    public void buildShapeless(ConfigurationSection recipeInfo) {
        ItemStack result = tools.getTool(recipeInfo.getName());
        ArrayList<RecipeChoice> ingredients = new ArrayList<>();

        for (String ingredientPath : recipeInfo.getConfigurationSection("ingredients").getKeys(false)) {
            RecipeChoice ingredient = new RecipeChoice.MaterialChoice(Material.valueOf(
                    recipeInfo.getConfigurationSection(ingredientPath).getString("item")));

            ingredients.add(ingredient);
        }

        if (result == null) return;

        if (ingredients.isEmpty()) return;

        recipeBuilder.create(true, recipeInfo.getName(), result).addIngredients(ingredients).build();
    }
}
