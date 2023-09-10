package com.github.NuclearDonut47.AlathraFishing.util;

import org.bukkit.ChatColor;

@SuppressWarnings("deprecation")
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
