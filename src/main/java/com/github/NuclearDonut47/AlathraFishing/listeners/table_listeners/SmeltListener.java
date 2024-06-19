package com.github.NuclearDonut47.AlathraFishing.listeners.table_listeners;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;
import com.github.NuclearDonut47.AlathraFishing.items.Fish;
import com.github.NuclearDonut47.AlathraFishing.items.generators.CustomFishManager;
import com.github.NuclearDonut47.AlathraFishing.listeners.AlathraFishingListener;
import com.github.milkdrinkers.colorparser.ColorParser;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class SmeltListener extends AlathraFishingListener {
    private static CustomFishManager fish;

    public SmeltListener(AlathraFishing plugin, CustomFishManager fishInstance) {
        super(plugin);
        fish = fishInstance;
    }

    @EventHandler(priority = EventPriority.HIGHEST) @SuppressWarnings("unused")
    public void nameChange(BlockCookEvent smeltEvent) {
        Component name = null;

        if (smeltEvent.getResult().getType() == Material.COOKED_SALMON) {
            name = ColorParser.of("Cooked Freshwater Fish").build();
        }

        if (smeltEvent.getResult().getType() == Material.COOKED_COD) {
            name = ColorParser.of("Cooked Saltwater Fish").build();
        }

        if (name == null) return;

        if (!smeltEvent.getSource().getItemMeta().hasCustomModelData()) return;

        boolean validModel = false;

        for (Fish fish : fish.getFish()) {
            if (fish.getModel() == smeltEvent.getSource().getItemMeta().getCustomModelData()) {
                validModel = true;
                break;
            }
        }

        if (!validModel) return;

        ItemStack result = smeltEvent.getResult();

        ItemMeta resultMeta = result.getItemMeta();

        resultMeta.displayName(name);

        result.setItemMeta(resultMeta);
    }
}
