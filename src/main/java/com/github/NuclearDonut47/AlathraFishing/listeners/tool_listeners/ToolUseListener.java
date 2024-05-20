package com.github.NuclearDonut47.AlathraFishing.listeners.tool_listeners;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;
import com.github.NuclearDonut47.AlathraFishing.listeners.AlathraFishingListener;
import com.github.NuclearDonut47.AlathraFishing.items.generators.CustomTools;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataType;

public abstract class ToolUseListener extends AlathraFishingListener {
    protected final CustomTools tools;

    protected ToolUseListener(AlathraFishing plugin, CustomTools toolsInstance) {
        super(plugin);
        tools = toolsInstance;
    }

    protected final boolean customToolCheck(int index, ItemStack item) {
        if (item == null) return false;

        if (item.getType() != tools.getBaseItems().get(index)) return false;

        int itemModel = 0;

        if (item.getItemMeta().hasCustomModelData())
            itemModel = item.getItemMeta().getCustomModelData();

        tools.convertVanillaTool(item, itemModel);

        return tools.getModelOverrides().get(index) == itemModel;
    }

    protected void damageTool(ItemStack item, Player player, int index) {
        Damageable damageable = (Damageable) item.getItemMeta();
        boolean broken = false;

        NamespacedKey durKey = new NamespacedKey(plugin, tools.getDefaultToolPaths()[index] + "_durability");
        NamespacedKey maxKey = new NamespacedKey(plugin, tools.getDefaultToolPaths()[index] + "_max_durability");

        int durability = damageable.getPersistentDataContainer().get(durKey, PersistentDataType.INTEGER);
        int maxDurability = damageable.getPersistentDataContainer().get(maxKey, PersistentDataType.INTEGER);

        durability--;

        if (durability == 0) {
            broken = true;
        }

        int damage = (int) ((1 - ((double) durability / maxDurability)) * item.getType().getMaxDurability());

        damageable.setDamage(damage);
        damageable.getPersistentDataContainer().set(durKey, PersistentDataType.INTEGER, durability);

        if (broken) {
            player.playSound(player, Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
            item.subtract();
            return;
        }

        item.setItemMeta(damageable);
    }
}