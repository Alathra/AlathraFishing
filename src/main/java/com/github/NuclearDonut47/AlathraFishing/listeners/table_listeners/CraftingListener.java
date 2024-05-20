package com.github.NuclearDonut47.AlathraFishing.listeners.table_listeners;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;
import com.github.NuclearDonut47.AlathraFishing.items.Fish;
import com.github.NuclearDonut47.AlathraFishing.items.generators.CustomFishManager;
import com.github.NuclearDonut47.AlathraFishing.listeners.AlathraFishingListener;
import com.github.milkdrinkers.colorparser.ColorParser;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class CraftingListener extends AlathraFishingListener {

    private CustomFishManager customFishManager;

    public CraftingListener(AlathraFishing plugin, CustomFishManager customFishManager) {
        super(plugin);
        this.customFishManager = customFishManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST) @SuppressWarnings("unused")
    public void applyLengthWeight(PrepareItemCraftEvent craftEvent) {
        CraftingRecipe recipe = (CraftingRecipe) craftEvent.getRecipe();

        if (!(recipe.getKey().toString().equals("salmonLength") || recipe.getKey().toString().equals("codLength"))) {
            return;
        }

        NamespacedKey key = new NamespacedKey(plugin, "identifier");

        ItemStack result = craftEvent.getInventory().getResult();
        ItemMeta resultMeta = result.getItemMeta();
        for (Fish fish : customFishManager.getFishes()) {
            if (fish.getIdentifier().equals(resultMeta.getPersistentDataContainer().get(key, PersistentDataType.STRING))) {

            }
        }
        List<Component> resultLore = resultMeta.lore();
        resultMeta.lore(
                List.of(
                        resultLore.get(0),
                        ColorParser.of().build(),
                        resultLore.get(1))
                );
        result.setItemMeta(resultMeta);
        craftEvent.getInventory().setResult(result);
    }

}
