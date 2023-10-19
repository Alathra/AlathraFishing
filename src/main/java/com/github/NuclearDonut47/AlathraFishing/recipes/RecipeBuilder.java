package com.github.NuclearDonut47.AlathraFishing.recipes;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class RecipeBuilder {
    private static Plugin plugin;
    private static Recipe recipe;

    public RecipeBuilder(Plugin pluginInstance) {
        plugin = pluginInstance;
    }

    public RecipeBuilder create(boolean shaped, String name, ItemStack result) {
        NamespacedKey key = new NamespacedKey(plugin, name);

        if (shaped) {
            recipe = new ShapedRecipe(key, result);
        } else {
            recipe = new ShapelessRecipe(key, result);
        }

        return this;
    }

    public void build() {
        plugin.getServer().addRecipe(recipe);

        recipe = null;
    }

    public RecipeBuilder setShape(String[] shape) {
        if (recipe instanceof ShapedRecipe shapedRecipe) shapedRecipe.shape(shape);

        return this;
    }

    public RecipeBuilder setIngredients(HashMap<Character, RecipeChoice> ingredients) {
        if (recipe instanceof ShapedRecipe shapedRecipe)
            for (char character : ingredients.keySet()) {
                shapedRecipe.setIngredient(character, ingredients.get(character));
            }

        return this;
    }

    public RecipeBuilder addIngredients(ArrayList<RecipeChoice> ingredients) {
        if (recipe instanceof ShapelessRecipe shapelessRecipe) {
            for (RecipeChoice ingredient: ingredients) {
                shapelessRecipe.addIngredient(ingredient);
            }
        }

        return this;
    }

    public void destroy(String namespace, String key) {
        if (plugin.getServer().getRecipe(new NamespacedKey(namespace, key)) != null)
            plugin.getServer().removeRecipe(new NamespacedKey(namespace, key));
    }
}
