package com.github.NuclearDonut47.AlathraFishing.config;

import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;

public final class Config {
    private static AlathraFishing plugin;
    private static FileConfiguration config;
    private static ConfigurationSection toolsSection;
    private static ConfigurationSection fishSection;
    private static ConfigurationSection vanillaSection;
    private static ConfigurationSection recipesSection;
    private static UUID fishermanNPCUUID;
    private static String packExtension;

    public Config(AlathraFishing pluginInstance, FileConfiguration configInstance) {
        plugin = pluginInstance;
        config = configInstance;
        toolsSection = configInstance.getConfigurationSection("tools");
        fishSection = configInstance.getConfigurationSection("fish");
        vanillaSection = configInstance.getConfigurationSection("vanilla");
        recipesSection = configInstance.getConfigurationSection("recipes");
        fishermanNPCUUID = UUID.fromString(configInstance.getString("fisherman_uuid"));
        packExtension = configInstance.getString("pack_extension");
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        plugin.saveDefaultConfig();
    }

    public FileConfiguration getConfig() {return config;}

    public ConfigurationSection getToolsSection() {
        return toolsSection;
    }

    public ConfigurationSection getFishSection() {return fishSection;}

    public ConfigurationSection getVanillaSection() {
        return vanillaSection;
    }

    public ConfigurationSection getRecipesSection() {
        return recipesSection;
    }

    public UUID getFishermanNPCUUID() {
        return fishermanNPCUUID;
    }

    public String getPackExtension() {
        return packExtension;
    }
}