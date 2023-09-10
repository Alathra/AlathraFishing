package com.github.NuclearDonut47.AlathraFishing.config;

import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;

public class Config {
	
	// config
	private static FileConfiguration config;
	
	// config verison
	// default should be up-to-date version
	public static int configVersion = 1; // default = up-to-date version
	
	public static ConfigurationSection toolsConfigSection;
	public static UUID fishermanNPCUUID;
	
	public static void initConfigVals() {
		
		config = AlathraFishing.getInstance().getConfig();
		fishermanNPCUUID = UUID.fromString(config.getString("fisherman_uuid"));
		toolsConfigSection = config.getConfigurationSection("tools");
		
	}
	
	public static void reloadConfig() {
		AlathraFishing.getInstance().reloadConfig();
		AlathraFishing.getInstance().saveDefaultConfig();
		initConfigVals();
	}
	
	public FileConfiguration getConfig() {
		return config;
	}

}
