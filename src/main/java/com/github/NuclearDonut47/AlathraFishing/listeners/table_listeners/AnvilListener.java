package com.github.NuclearDonut47.AlathraFishing.listeners.table_listeners;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;
import com.github.NuclearDonut47.AlathraFishing.items.CustomTools;
import com.github.NuclearDonut47.AlathraFishing.listeners.AlathraFishingListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.persistence.PersistentDataType;

public final class AnvilListener extends AlathraFishingListener {
    private static CustomTools tools;

    public AnvilListener(AlathraFishing pluginInstance, CustomTools toolsInstance) {
        super(pluginInstance);
        tools = toolsInstance;
    }

    @EventHandler(priority = EventPriority.HIGH) @SuppressWarnings("unused")
    public void removeEnchanting(PrepareAnvilEvent prepEvent) {
        AnvilInventory anvil = prepEvent.getInventory();

        if (anvil.getFirstItem() == null) return;

        if (anvil.getSecondItem() == null) return;

        int firstModel = 0;
        int secondModel = 0;

        if (prepEvent.getInventory().getFirstItem().getItemMeta().hasCustomModelData())
            firstModel = prepEvent.getInventory().getFirstItem().getItemMeta().getCustomModelData();

        if (prepEvent.getInventory().getSecondItem().getItemMeta().hasCustomModelData())
            secondModel = prepEvent.getInventory().getSecondItem().getItemMeta().getCustomModelData();

        boolean invalidFirstItem = invalidToolCheck(anvil.getFirstItem().getType(), firstModel);
        boolean invalidSecondItem = invalidToolCheck(anvil.getSecondItem().getType(), secondModel);

        if (invalidFirstItem && invalidSecondItem) {
            return;
        }

        if (!invalidFirstItem) tools.convertVanillaTool(anvil.getFirstItem(), firstModel);

        if (!invalidSecondItem) tools.convertVanillaTool(anvil.getSecondItem(), secondModel);

        boolean nonVanillaToolPresent = !anvil.getFirstItem().getItemMeta().getPersistentDataContainer()
                .get(tools.getVanillaKey(), PersistentDataType.BOOLEAN);

        if (!anvil.getSecondItem().getItemMeta().getPersistentDataContainer()
                .get(tools.getVanillaKey(), PersistentDataType.BOOLEAN)) nonVanillaToolPresent = true;

        if (!nonVanillaToolPresent) return;

        if (anvil.getFirstItem().getType() == anvil.getSecondItem().getType()) prepEvent.setResult(null);

        if (anvil.getSecondItem().getType() == Material.ENCHANTED_BOOK) prepEvent.setResult(null);
    }

    private boolean invalidToolCheck(Material item, int model) {
        for (int a = 0; a < tools.getDefaultToolPaths().length; a++) {
            if (item != tools.getBaseItems()[a]) continue;

            if (model != tools.getModelOverrides()[a]) continue;

            return false;
        }

        return true;
    }
}
