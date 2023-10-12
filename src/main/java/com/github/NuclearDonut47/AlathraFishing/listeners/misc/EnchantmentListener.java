package com.github.NuclearDonut47.AlathraFishing.listeners.misc;

import com.github.NuclearDonut47.AlathraFishing.items.CustomTools;
import com.github.NuclearDonut47.AlathraFishing.listeners.AlathraFishingListener;
import com.github.milkdrinkers.colorparser.ColorParser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class EnchantmentListener extends AlathraFishingListener {
    private static CustomTools tools;

    public EnchantmentListener(Plugin plugin, CustomTools toolsInstance) {
        super(plugin);
        tools = toolsInstance;
    }

    @EventHandler @SuppressWarnings("unused")
    public void removeEnchants(PrepareItemEnchantEvent prepEvent) {
        int model = 0;

        if (prepEvent.getItem().getItemMeta().hasCustomModelData())
            model = prepEvent.getItem().getItemMeta().getCustomModelData();

        tools.convertVanillaTool(prepEvent.getItem(), model);

        if (prepEvent.getItem().getItemMeta().getPersistentDataContainer().get(tools.getVanillaKey(),
                PersistentDataType.BOOLEAN)) return;

        prepEvent.setCancelled(true);
    }
}