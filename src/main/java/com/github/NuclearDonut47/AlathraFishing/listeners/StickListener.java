package com.github.NuclearDonut47.AlathraFishing.listeners;

import com.github.ipecter.rtu.biomelib.RTUBiomeLib;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;

public class StickListener extends AlathraFishingListener {
    public StickListener(Plugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void stickClick(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;

        if (event.getAction().isLeftClick()) return;

        if (event.getItem().getType() != Material.STICK) return;

        plugin.getLogger().info(
                String.valueOf(RTUBiomeLib.getInterface().getBiomeName(event.getClickedBlock().getLocation())));
    }
}
