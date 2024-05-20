package com.github.NuclearDonut47.AlathraFishing.listeners.table_listeners;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;
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
    public CraftingListener(AlathraFishing plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST) @SuppressWarnings("unused")
    public void addOrHidLength(PrepareItemCraftEvent craftEvent) {
        CraftingRecipe recipe = (CraftingRecipe) craftEvent.getRecipe();

        if (!(recipe.getKey().toString().equals("salmonLength") || recipe.getKey().toString().equals("codLength"))) {
            return;
        }

        NamespacedKey lengthDisplayed = new NamespacedKey(plugin, "lengthDisplayed");
        NamespacedKey length = new NamespacedKey(plugin, "length");

        if (craftEvent.getInventory().getResult().getItemMeta().getPersistentDataContainer()
                .get(lengthDisplayed, PersistentDataType.BOOLEAN)) {
            ItemStack result = craftEvent.getInventory().getResult();

            ItemMeta resultMeta = result.getItemMeta();

            List<Component> resultLore = resultMeta.lore();

            resultMeta.lore(List.of(resultLore.get(0), resultLore.get(2)));

            result.setItemMeta(resultMeta);

            craftEvent.getInventory().setResult(result);

            return;
        }

        ItemStack result = craftEvent.getInventory().getResult();

        ItemMeta resultMeta = result.getItemMeta();

        List<Component> resultLore = resultMeta.lore();

        resultMeta.lore(List.of(resultLore.get(0),
                ColorParser.of(
                        resultMeta.getPersistentDataContainer().get(length, PersistentDataType.STRING)).build(),
                resultLore.get(1)));

        result.setItemMeta(resultMeta);

        craftEvent.getInventory().setResult(result);
    }
}
