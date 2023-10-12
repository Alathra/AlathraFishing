package com.github.NuclearDonut47.AlathraFishing.hooks.citizens;

import com.github.milkdrinkers.colorparser.ColorParser;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.NuclearDonut47.AlathraFishing.config.Config;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

public class CitizensRightClickNPCListener implements Listener {
	private static Config config;

	public CitizensRightClickNPCListener(Config configInstance) {
		config = configInstance;
	}

	@EventHandler @SuppressWarnings("unused")
	public static void onNPCRightClick(NPCRightClickEvent e) {
		NPC npc	= e.getNPC();
		if (npc.getUniqueId().toString().contentEquals(config.getFishermanNPCUUID().toString())) {
			Bukkit.broadcast(ColorParser.of("Fisherman NPC Detected").build());
		}
	}
}
