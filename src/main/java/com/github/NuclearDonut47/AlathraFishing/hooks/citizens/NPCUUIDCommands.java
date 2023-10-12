package com.github.NuclearDonut47.AlathraFishing.hooks.citizens;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;
import com.github.NuclearDonut47.AlathraFishing.util.Helper;

import net.citizensnpcs.api.CitizensAPI;
import org.jetbrains.annotations.NotNull;

public class NPCUUIDCommands implements CommandExecutor{
	public NPCUUIDCommands(AlathraFishing plugin) {
		plugin.getCommand("npcuuid").setExecutor(this);
	}
	
	public String helpMsg(boolean isConsole) {
		if (isConsole) {
			return Helper.chatLabelConsole() + "Command usage: /npcuuid [citizens id]";
		} else {
			return Helper.chatLabel() + Helper.color("&eCommand usage: /npcuuid [citizens id]");
		}
	}

	@SuppressWarnings("unused")
	public String uuidMsg(boolean isConsole) {
		if (isConsole) {
			return Helper.chatLabelConsole() + "Command usage: /npcuuid [citizens id]";
		} else {
			return Helper.chatLabel() + Helper.color("&eCommand usage: /npcuuid [citizens id]");
		}
	}

	public String invalidIDMsg(boolean isConsole) {
		if (isConsole) {
			return Helper.chatLabelConsole() + "Error - NPC ID is non-numeric";
		} else {
			return Helper.chatLabel() + Helper.color("&cError - NPC ID is non-numeric");
		}
	}
	
	public String uuidMsg(boolean isConsole, UUID uuid) {
		if (isConsole) {
			return Helper.chatLabelConsole() + "This NPC's UUID is: " + uuid.toString();
		} else {
			return Helper.chatLabel() + Helper.color("&eThis NPC's UUID is: &a" + uuid.toString());
		}
	}
	
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		boolean isConsole = !(sender instanceof Player);

        if (args.length == 0) {
			sender.sendMessage(helpMsg(isConsole));
			return false;
		}

		if (args.length == 1) {
			int id;
			// See if user put in non-numeric input for id arg
			try {
				id = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				sender.sendMessage(invalidIDMsg(isConsole));
				return false;
			}
			sender.sendMessage(uuidMsg(isConsole, CitizensAPI.getNPCRegistry().getById(id).getUniqueId()));
		}
		return false;
	}
}
