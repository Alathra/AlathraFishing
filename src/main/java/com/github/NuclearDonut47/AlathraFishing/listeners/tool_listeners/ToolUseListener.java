package com.github.NuclearDonut47.AlathraFishing.listeners.tool_listeners;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;
import com.github.NuclearDonut47.AlathraFishing.listeners.AlathraFishingListener;
import com.github.NuclearDonut47.AlathraFishing.items.CustomTools;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public abstract class ToolUseListener extends AlathraFishingListener {
    protected final CustomTools tools;

    protected ToolUseListener(AlathraFishing plugin, CustomTools toolsInstance) {
        super(plugin);
        tools = toolsInstance;
    }

    protected final boolean customToolCheck(int index, ItemStack item) {
        if (item == null) return false;

        if (item.getType() != tools.getBaseItems()[index]) return false;

        int itemModel = 0;

        if (item.getItemMeta().hasCustomModelData())
            itemModel = item.getItemMeta().getCustomModelData();

        tools.convertVanillaTool(item, itemModel);

        return tools.getModelOverrides()[index] == itemModel;
    }

    protected abstract void damageTool(ItemStack usedItem, Player player);

    protected final ItemMeta toolUse(NamespacedKey durKey, NamespacedKey maxKey, ItemStack item) {
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
}