package com.github.NuclearDonut47.AlathraFishing.listeners;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;
import com.github.NuclearDonut47.AlathraFishing.listeners.AlathraFishingListener;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MendingRemovalListener extends AlathraFishingListener {
    public MendingRemovalListener(AlathraFishing plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST) @SuppressWarnings("unused")
    public void removeMending(PlayerFishEvent fishEvent) {
        Item itemEntity = (Item) fishEvent.getCaught();

        if (itemEntity == null) return;

        if (itemEntity.getItemStack().getItemMeta().getEnchants().isEmpty()) return;

        ItemStack item = itemEntity.getItemStack();

        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.removeEnchant(Enchantment.MENDING);

        item.setItemMeta(itemMeta);
        itemEntity.setItemStack(item);
    }
}
