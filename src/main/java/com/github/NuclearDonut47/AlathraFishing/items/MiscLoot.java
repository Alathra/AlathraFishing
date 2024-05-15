package com.github.NuclearDonut47.AlathraFishing.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class MiscLoot extends Loot {
    private static Plugin plugin;
    private final Material item;

    public MiscLoot(Plugin pluginInstance, String identifier, ConfigurationSection miscLootSection) {
        super(identifier);
        plugin = pluginInstance;

        if (Material.getMaterial(miscLootSection.getString(identifier)) != null) {
            item = Material.getMaterial(miscLootSection.getString(identifier));
        } else {
            item = Material.STICK;
        }
    }

    public ItemStack generateLootStack() {
        ItemStack miscLoot = new ItemStack(item);

        ItemMeta miscLootMeta = miscLoot.getItemMeta();

        NamespacedKey idKey = new NamespacedKey(plugin, "identifier");
        NamespacedKey rarityKey = new NamespacedKey(plugin, "rarity");

        miscLootMeta.getPersistentDataContainer().set(idKey, PersistentDataType.STRING, identifier);
        miscLootMeta.getPersistentDataContainer().set(rarityKey, PersistentDataType.STRING, "miscellaneous");

        return miscLoot;
    }
}
