package com.github.NuclearDonut47.AlathraFishing.listeners.table_listeners;

import com.github.NuclearDonut47.AlathraFishing.items.generators.CustomTools;
import com.github.NuclearDonut47.AlathraFishing.listeners.AlathraFishingListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class EnchantmentListener extends AlathraFishingListener {
    private static CustomTools tools;

    public EnchantmentListener(Plugin plugin, CustomTools toolsInstance) {
        super(plugin);
        tools = toolsInstance;
    }

    @EventHandler(priority = EventPriority.HIGHEST) @SuppressWarnings("unused")
    public void removeEnchants(PrepareItemEnchantEvent prepEvent) {
        Material item = prepEvent.getItem().getType();
        int model = 0;
        boolean invalidItemCheck = true;

        if (prepEvent.getItem().getItemMeta().hasCustomModelData())
            model = prepEvent.getItem().getItemMeta().getCustomModelData();

        for (int a = 0; a < tools.getDefaultToolPaths().length; a++) {
            if (item != tools.getBaseItems().get(a)) continue;
            if (model != tools.getModelOverrides().get(a)) continue;

            invalidItemCheck = false;
            break;
        }

        if (invalidItemCheck) return;

        tools.convertVanillaTool(prepEvent.getItem(), model);

        if (prepEvent.getItem().getItemMeta().getPersistentDataContainer().get(tools.getVanillaKey(),
                PersistentDataType.BOOLEAN)) return;

        prepEvent.setCancelled(true);
    }
}