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
        castEvent.getPlayer().sendMessage(new ColorParser(String.valueOf(castEvent.getHand())).build());

        if (castEvent.getHand() != EquipmentSlot.HAND) return;

        if (castEvent.getItem().getType() != tools.getBaseItems()[0]) return;
        
        if (!castEvent.getItem().getItemMeta().hasCustomModelData()) return;

        if (castEvent.getItem().getItemMeta().getCustomModelData() != tools.getModelOverrides()[0]) return;

        if (castEvent.getPlayer().getTargetBlock(null, 16).getType() != Material.WATER) {
            castEvent.setCancelled(true);
            return;
        }

        Component castingMessage = new ColorParser("You are now casting your net.").build();

        castEvent.getPlayer().sendMessage(castingMessage);
    }
}
