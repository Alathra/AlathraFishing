package com.github.NuclearDonut47.AlathraFishing.tool_listeners;

import com.github.NuclearDonut47.AlathraFishing.items.CustomTools;
import com.github.milkdrinkers.colorparser.ColorParser;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;

public class NetListener extends ToolUseListener {
    public NetListener(Plugin pluginInstance, CustomTools toolsInstance) {
        super(pluginInstance, toolsInstance);
    }

    // Fishing:
    // Create Fishing Effect
    // Set up Timer with Random Time
    // After Time Elapses, Create SPLASHING Effects for Amount of Time
    // (If I have to go all the way, may as well make this unique).
    // Give Time Window to Reel In Haul

    // Reeling:
    // Cancel reel-in message
    // Cancel Catch                                         unimportant for prerelease
    // Get Loot
    // Reduce Durability

    @EventHandler @SuppressWarnings("unused")
    public void castNet(PlayerInteractEvent castEvent) {
        if (!castEvent.getHand().equals(EquipmentSlot.HAND)) return;
        // Item is in main hand.

        if (castEvent.getAction().isLeftClick()) return;
        // Action was right-click.
        // API is broken. Listener will trip twice, once for right-click, once for left-click.
        // This only happens for right-clicking. Left-clicking will

        if (castEvent.getItem().getType() != tools.getBaseItems()[0]) return;
        // Item is net base item.

        if (!toolCheck(0, castEvent.getItem().getItemMeta())) return;
        // Item is net.

        castEvent.setCancelled(true);
        // Cancel vanilla action.

        Player player = castEvent.getPlayer();

        if (player.getTargetBlock(null, 16).getType() != Material.WATER) return;
        // Stop doing stuff if player is not looking at water.

        Component castingMessage = ColorParser.of("You are now casting your net.").build();

        player.sendMessage(castingMessage);
        // Tells player they are casting their net before fishing hook entity is deleted.

        Location nettingArea = player.getTargetBlock(null, 16).getLocation();

        player.sendMessage(ColorParser.of(nettingArea.toString()).build());

        player.spawnParticle(Particle.SONIC_BOOM, nettingArea, 5);
    }
}