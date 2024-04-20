package com.github.NuclearDonut47.AlathraFishing.config;

import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;

public final class Config {
    private static AlathraFishing plugin;
    private static FileConfiguration config;
    private static ConfigurationSection toolsSection;
    private static ConfigurationSection vanillaSection;
    private static ConfigurationSection recipesSection;
    private static ConfigurationSection fishSection;
    private static ConfigurationSection biomesSection;
    private static UUID fishermanNPCUUID;
    private static String packExtension;
    private static ConfigurationSection devModeSection;

    public Config(AlathraFishing pluginInstance, FileConfiguration configInstance) {
        plugin = pluginInstance;
        config = configInstance;
        toolsSection = configInstance.getConfigurationSection("tools");
        vanillaSection = configInstance.getConfigurationSection("vanilla");
        recipesSection = configInstance.getConfigurationSection("recipes");
        fishSection = configInstance.getConfigurationSection("fish");
        biomesSection = configInstance.getConfigurationSection("biomes");
        fishermanNPCUUID = UUID.fromString(configInstance.getString("fisherman_uuid"));
        packExtension = configInstance.getString("pack_extension");
        devModeSection = configInstance.getConfigurationSection("dev_mode");
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        plugin.saveDefaultConfig();
    }

    public FileConfiguration getConfig() {return config;}

    public ConfigurationSection getToolsSection() {
        return toolsSection;
    }

    public ConfigurationSection getVanillaSection() {
        return vanillaSection;
    }

    public ConfigurationSection getRecipesSection() {
        return recipesSection;
    }

    public ConfigurationSection getFishSection() {
        return fishSection;
    }

    public ConfigurationSection getBiomesSection() {
        return biomesSection;
    }

    public UUID getFishermanNPCUUID() {
        return fishermanNPCUUID;
    }

    public String getPackExtension() {
        return packExtension;
    }

    public ConfigurationSection getDevModeSection() {
        return devModeSection;
    }
}