package com.github.NuclearDonut47.AlathraFishing;

import net.md_5.bungee.api.ChatColor;

public class Helper {
	
	public static String chatLabel() {
		return ChatColor.translateAlternateColorCodes('&', "&a[&eAlathraFishing&a] ");
	}
	
	public static String chatLabelConsole() {
		return "[AlathraFishing] ";
	}
	
	public static String color(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
}
