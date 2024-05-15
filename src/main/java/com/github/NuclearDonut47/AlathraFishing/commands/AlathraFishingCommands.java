package com.github.NuclearDonut47.AlathraFishing.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;
import com.github.NuclearDonut47.AlathraFishing.config.Config;
import com.github.NuclearDonut47.AlathraFishing.util.Helper;
import org.jetbrains.annotations.NotNull;

public class AlathraFishingCommands implements CommandExecutor {
	private static Config config;

	public AlathraFishingCommands(AlathraFishing plugin, Config configInstance) {
		plugin.getCommand("alathrafishing").setExecutor(this);
		config = configInstance;
	}

	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
							 String[] args) {

		boolean isConsole = true;

		Player player = null;
		if (sender instanceof Player) {
			isConsole = false;
			player = (Player) sender;
		}

		if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			if (!isConsole) {
				if (!player.hasPermission("AlathraFishing.reload")) {
					player.sendMessage(Helper.chatLabel() + Helper.color("&cYou do not have permission to do this"));
					return false;
				}
				player.sendMessage(Helper.chatLabel() + Helper.color("&econfig.yml reloaded"));
			} else {
				sender.sendMessage(Helper.chatLabelConsole() +Helper.color("&econfig.yml reloaded"));
			}
			config.reloadConfig();
		}
		return false;
	}
}
