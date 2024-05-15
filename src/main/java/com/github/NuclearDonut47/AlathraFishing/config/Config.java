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
    private static ConfigurationSection miscLootSection;
    private static ConfigurationSection biomesSection;
    private static ConfigurationSection distributionSection;
    private static UUID fishermanNPCUUID;
    private static String packExtension;
    private static boolean devMode;
    private static ConfigurationSection vanillaBiomesSection;

    public Config(AlathraFishing pluginInstance, FileConfiguration configInstance) {
        plugin = pluginInstance;
        config = configInstance;
        toolsSection = configInstance.getConfigurationSection("tools");
        vanillaSection = configInstance.getConfigurationSection("vanilla");
        recipesSection = configInstance.getConfigurationSection("recipes");
        fishSection = configInstance.getConfigurationSection("fish");
        miscLootSection = configInstance.getConfigurationSection("misc_loot");
        biomesSection = configInstance.getConfigurationSection("biomes");
        distributionSection = configInstance.getConfigurationSection("distribution");
        packExtension = configInstance.getString("pack_extension");
        fishermanNPCUUID = UUID.fromString(configInstance.getString("fisherman_uuid"));
        devMode = configInstance.getBoolean("dev_mode", false);
        vanillaBiomesSection = configInstance.getConfigurationSection("vanilla_biomes");
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

    public ConfigurationSection getMiscLootSection() {
        return miscLootSection;
    }

    public ConfigurationSection getBiomesSection() {
        return biomesSection;
    }

    public ConfigurationSection getDistributionSection() {
        return distributionSection;
    }

    public String getPackExtension() {
        return packExtension;
    }

    public UUID getFishermanNPCUUID() {
        return fishermanNPCUUID;
    }

    public boolean getDevMode() {
        return devMode;
    }

    public ConfigurationSection getVanillaBiomesSection() {
        return vanillaBiomesSection;
    }
}