package com.github.NuclearDonut47.AlathraFishing.tool_listeners.listeners;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;
import com.github.NuclearDonut47.AlathraFishing.items.CustomTools;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public abstract class ToolUseListener implements Listener {
    protected final AlathraFishing plugin;
    protected final CustomTools tools;

    public ToolUseListener(AlathraFishing pluginInstance, CustomTools toolsInstance) {
        plugin = pluginInstance;
        tools = toolsInstance;
    }

    public boolean toolCheck(int index, ItemStack item) {
        if (item == null) return false;

        if (item.getType() != tools.getBaseItems()[0]) return false;

        if (item.getItemMeta().hasCustomModelData())
            return tools.getModelOverrides()[index] == item.getItemMeta().getCustomModelData();

        return tools.getModelOverrides()[index] == 0;
    }

    public ItemMeta toolUse(NamespacedKey durKey, NamespacedKey maxKey, ItemStack item) {
        Damageable damageable = (Damageable) item.getItemMeta();

        int durability = damageable.getPersistentDataContainer().get(durKey, PersistentDataType.INTEGER);
        int maxDurability = damageable.getPersistentDataContainer().get(maxKey, PersistentDataType.INTEGER);

        durability--;

        if (durability == 0) {
            return null;
        }

        int damage = (int) ((1 - ((double) durability / maxDurability)) * item.getType().getMaxDurability());

        damageable.setDamage(damage);
        damageable.getPersistentDataContainer().set(durKey, PersistentDataType.INTEGER, durability);

        return damageable;
    }

    public void registerListener() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}