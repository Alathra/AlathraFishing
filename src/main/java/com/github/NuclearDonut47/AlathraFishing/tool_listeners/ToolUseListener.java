package com.github.NuclearDonut47.AlathraFishing.tool_listeners;

import com.github.NuclearDonut47.AlathraFishing.items.CustomTools;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public abstract class ToolUseListener implements Listener {
    private final Plugin plugin;
    protected CustomTools tools;

    public ToolUseListener(Plugin pluginInstance, CustomTools toolsInstance) {
        plugin = pluginInstance;
        tools = toolsInstance;
    }

    public ItemStack toolUse(NamespacedKey durKey, NamespacedKey maxDurKey, ItemStack item) {
        Damageable damageable = (Damageable) item.getItemMeta();

        int durability = damageable.getPersistentDataContainer().get(durKey, PersistentDataType.INTEGER);
        int maxDurability = damageable.getPersistentDataContainer().get(maxDurKey, PersistentDataType.INTEGER);

        durability--;
        int damage = (int) (1 - ((double) durability / maxDurability)) * item.getType().getMaxDurability();

        if (durability == 0) {
            return null;
        }

        damageable.setDamage(damage);
        damageable.getPersistentDataContainer().set(durKey, PersistentDataType.INTEGER, durability);

        return item;
    }

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

    public void registerListener() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}