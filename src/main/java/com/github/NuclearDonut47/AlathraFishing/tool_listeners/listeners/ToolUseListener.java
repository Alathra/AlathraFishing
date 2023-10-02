package com.github.NuclearDonut47.AlathraFishing.tool_listeners.listeners;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;
import com.github.NuclearDonut47.AlathraFishing.items.CustomTools;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Set;

public abstract class ToolUseListener implements Listener {
    protected final AlathraFishing plugin;
    protected final CustomTools tools;

    // Initialize
    public ToolUseListener(AlathraFishing pluginInstance, CustomTools toolsInstance) {
        plugin = pluginInstance;
        tools = toolsInstance;
    }

    public boolean toolCheck(int index, ItemStack item) {
        if (item == null) return false;

        if (item.getType() != tools.getBaseItems()[0]) return false;

        if (item.getItemMeta().hasCustomModelData()) return tools.getModelOverrides()[index] == item.getItemMeta().getCustomModelData();
        // If item has customModelData return true if match.

        return tools.getModelOverrides()[index] == 0;
        // Model has no customModelData. Return true if custom tool customModelData is 0.
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

    /*
    @EventHandler @SuppressWarnings("unused")
    public void cancelFishing(PlayerFishEvent fishEvent) {
        if (!fishEvent.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
            fishEvent.setCancelled(true);
            return;
        }

        if (fishEvent.getPlayer().getInventory().getItemInMainHand().getItemMeta().getCustomModelData()
                != tools.getModelOverrides()[1]) return;

        fishEvent.setCancelled(true);
    }
    */

    public void registerListener() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}