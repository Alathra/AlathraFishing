package com.github.NuclearDonut47.AlathraFishing.hooks.citizens;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.NuclearDonut47.AlathraFishing.config.Config;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

public class CitizensRightClickNPCListener implements Listener {

	@EventHandler
	public static void onNPCRightClick(NPCRightClickEvent e) {
		NPC npc	= e.getNPC();
		if (npc.getUniqueId().toString().contentEquals(Config.fishermanNPCUUID.toString())) {
			Bukkit.broadcastMessage("Fisherman NPC Detected");
		}
	}
}
