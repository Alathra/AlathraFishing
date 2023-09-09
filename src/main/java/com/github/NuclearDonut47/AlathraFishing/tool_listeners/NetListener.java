package com.github.NuclearDonut47.AlathraFishing.tool_listeners;

import com.github.NuclearDonut47.AlathraFishing.items.CustomTools;
import com.github.milkdrinkers.colorparser.ColorParser;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;

public class NetListener extends ToolUseListener {
    public NetListener(Plugin pluginInstance, CustomTools toolsInstance) {
        super(pluginInstance, toolsInstance);
    }

    // Casting:
    // Cancel normal fishing                                check
    // Check if Player is Looking at Water                  check
    // Print message that player is net fishing             check

    // Fishing:
    // Set Bobber Invisible (Create fishing effect without bobber, deleting it instead)
    // Disable Enchantments                                 unimportant for prerelease

    // Reeling:
    // Cancel reel-in message
    // Cancel Catch                                         unimportant for prerelease
    // Get Loot
    // Reduce Durability

    @EventHandler @SuppressWarnings("unused")
    public void castNet(PlayerInteractEvent castEvent) {
        if (castEvent.getHand() != EquipmentSlot.HAND) return;
        // Item is in main hand

        if (castEvent.getItem().getType() != tools.getBaseItems()[0]) return;
        // Item is net base item.

        if (!toolCheck(0, castEvent.getItem().getItemMeta())) return;
        // Item is net.

        castEvent.getPlayer().sendMessage(new ColorParser("Item is net.").build());

        if (castEvent.getPlayer().getTargetBlock(null, 16).getType() != Material.WATER) {
            castEvent.setCancelled(true);
            return;
        }
        // Cancel vanilla action and stop if player is not looking at water.

        Component castingMessage = new ColorParser("You are now casting your net.").build();

        castEvent.getPlayer().sendMessage(castingMessage);
    }
}
