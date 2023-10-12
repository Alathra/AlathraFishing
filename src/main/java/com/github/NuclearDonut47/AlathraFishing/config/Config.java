package com.github.NuclearDonut47.AlathraFishing.config;

import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;
import com.github.NuclearDonut47.AlathraFishing.util.BiomeUtil;

public final class Config {
	private static AlathraFishing plugin;
	private static ConfigurationSection toolsSection;
	private static ConfigurationSection vanillaSection;
	private static UUID fishermanNPCUUID;
	private static String packExtension;

	public Config(AlathraFishing pluginInstance, FileConfiguration config) {
		plugin = pluginInstance;
		toolsSection = config.getConfigurationSection("tools");
		vanillaSection = config.getConfigurationSection("vanilla");
		fishermanNPCUUID = UUID.fromString(config.getString("fisherman_uuid"));
		packExtension =  config.getString("pack_extension");
	}
	
	public void reloadConfig() {
		plugin.reloadConfig();
		plugin.saveDefaultConfig();
		BiomeUtil.init(this);
	}

	public ConfigurationSection getToolsSection() {
		return toolsSection;
	}

	public ConfigurationSection getVanillaSection() {
		return vanillaSection;
	}

	public UUID getFishermanNPCUUID() {
		return fishermanNPCUUID;
	}

	public String getPackExtension() {
		return packExtension;
	}
}
