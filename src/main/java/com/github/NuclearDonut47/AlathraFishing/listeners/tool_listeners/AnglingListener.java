package com.github.NuclearDonut47.AlathraFishing.listeners.tool_listeners;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;
import com.github.NuclearDonut47.AlathraFishing.items.CustomTools;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.event.player.PlayerFishEvent.State;

public class AnglingListener extends ToolUseListener {
    public AnglingListener(AlathraFishing plugin, CustomTools tools) {
        super(plugin, tools);
    }

    @EventHandler @SuppressWarnings("unused")
    public void anglingFishing(PlayerFishEvent anglingEvent) {
        if (!customToolCheck(1, anglingEvent.getPlayer().getInventory().getItemInMainHand())) {
            vanillaAngling(anglingEvent);
            return;
        }

        if (anglingEvent.getState() == State.FISHING) {
            anglingEvent.getHook().setMinWaitTime(20*5);
            anglingEvent.getHook().setMaxWaitTime(20*30);
            return;
        }

        if (anglingEvent.getState() != State.CAUGHT_FISH) return;

        this.damageTool(anglingEvent.getPlayer().getInventory().getItemInMainHand(), anglingEvent.getPlayer());

        Item itemEntity = (Item) anglingEvent.getCaught();

        itemEntity.setItemStack(new ItemStack(Material.LILY_PAD));
    }

    private void vanillaAngling(PlayerFishEvent anglingEvent) {
        if (anglingEvent.getState() != State.CAUGHT_FISH) return;

        Item itemEntity = (Item) anglingEvent.getCaught();

        itemEntity.setItemStack(new ItemStack(Material.NAUTILUS_SHELL));
    }

    protected void damageTool(ItemStack usedItem, Player player) {
        ItemMeta usedItemMeta = toolUse(new NamespacedKey(plugin, tools.getDefaultToolPaths()[1] + "_durability"),
                new NamespacedKey(plugin, tools.getDefaultToolPaths()[1] + "_max_durability"),
                usedItem);

        if (usedItemMeta == null) {
            player.playSound(player, Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
            usedItem.subtract();
            return;
        }

        usedItem.setItemMeta(usedItemMeta);
    }
}
