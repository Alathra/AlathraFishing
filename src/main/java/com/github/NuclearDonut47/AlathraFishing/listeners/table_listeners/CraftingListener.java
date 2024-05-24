package com.github.NuclearDonut47.AlathraFishing.listeners.table_listeners;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;
import com.github.NuclearDonut47.AlathraFishing.items.Fish;
import com.github.NuclearDonut47.AlathraFishing.items.generators.CustomFishManager;
import com.github.NuclearDonut47.AlathraFishing.listeners.AlathraFishingListener;
import com.github.milkdrinkers.colorparser.ColorParser;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class CraftingListener extends AlathraFishingListener {
    private static CustomFishManager customFishManager;

    public CraftingListener(AlathraFishing plugin, CustomFishManager customFishManagerInstance) {
        super(plugin);
        customFishManager = customFishManagerInstance;
    }

    @EventHandler(priority = EventPriority.HIGHEST) @SuppressWarnings("unused")
    public void editResultDisplayLore(PrepareItemCraftEvent craftEvent) {
        if (!(craftEvent.getRecipe() instanceof CraftingRecipe recipe)) return;

        if (!(recipe.getKey().toString().equals("alathrafishing:salmonlength") ||
                recipe.getKey().toString().equals("alathrafishing:codlength"))) {
            return;
        }

        ItemStack input = null;
        NamespacedKey identifierKey = new NamespacedKey(plugin, "identifier");

        for (ItemStack item : craftEvent.getInventory().getMatrix()) {
            if (item != null) {
                input = item;

                break;
            }
        }

        if (input == null) return;

        Fish thisFish = null;

        for (Fish fish : customFishManager.getFish()) {
            if (fish.getIdentifier().equals(input.getItemMeta().getPersistentDataContainer()
                    .get(identifierKey, PersistentDataType.STRING))) {
                thisFish = fish;

                break;
            }
        }

        if (thisFish == null) {
            craftEvent.getInventory().setResult(new ItemStack(Material.AIR));

            return;
        }

        ItemMeta newResultMeta = input.getItemMeta();

        List<Component> resultLore = newResultMeta.lore();

        if (resultLore.size() != 2) {
            craftEvent.getInventory().setResult(new ItemStack(Material.AIR));

            return;
        }

        newResultMeta.lore(List.of(resultLore.get(0), ColorParser.of("xxx.x cm").build(),
                resultLore.get(1)));

        ItemStack result = craftEvent.getInventory().getResult();

        result.setItemMeta(newResultMeta);

        craftEvent.getInventory().setResult(result);
    }

    @EventHandler(priority = EventPriority.HIGHEST) @SuppressWarnings("unused")
    public void applyLength(InventoryClickEvent clickEvent) {
        if (!((clickEvent.getClickedInventory()) instanceof CraftingInventory)) return;

        if(!(clickEvent.getSlotType() == InventoryType.SlotType.RESULT)) return;

        CraftingInventory craftingInventory = (CraftingInventory) clickEvent.getClickedInventory();

        ItemStack result = craftingInventory.getResult();

        if (result == null) return;

        NamespacedKey identifierKey = new NamespacedKey(plugin, "identifier");
        Fish thisFish = null;

        for (Fish fish : customFishManager.getFish()) {
            if (fish.getIdentifier().equals(result.getItemMeta().getPersistentDataContainer()
                    .get(identifierKey, PersistentDataType.STRING))) {
                thisFish = fish;

                break;
            }
        }

        if (thisFish == null) return;

        if (!(clickEvent.getAction() == InventoryAction.PICKUP_ALL)) {
            clickEvent.setCancelled(true);
            return;
        }

        ItemMeta newResultMeta = result.getItemMeta();

        List<Component> resultLore = newResultMeta.lore();

        newResultMeta.lore(List.of(resultLore.get(0),
                ColorParser.of(thisFish.generateLengthString() + " cm").build(), resultLore.get(2)));

        result.setItemMeta(newResultMeta);

        clickEvent.setCurrentItem(result);
    }

    @EventHandler(priority = EventPriority.HIGHEST) @SuppressWarnings("unused")
    public void disableCraftingRepair(PrepareItemCraftEvent craftEvent) {
        boolean otherItemsPresent = false;
        int fishingRodItems = 0;

        for (ItemStack item : craftEvent.getInventory().getMatrix()) {
            if (item == null) continue;

            if (item.getType() == Material.FISHING_ROD) {
                fishingRodItems++;
                continue;
            }

            otherItemsPresent = true;
        }

        if (otherItemsPresent) return;

        if (fishingRodItems != 2) return;

        for (ItemStack item : craftEvent.getInventory().getMatrix()) {
            if (item == null) continue;

            if (item.getType() != Material.FISHING_ROD) continue;

            if (item.getItemMeta().hasCustomModelData()) {
                craftEvent.getInventory().setResult(new ItemStack(Material.AIR));

                return;
            }
        }
    }
}
