package com.github.NuclearDonut47.AlathraFishing.items;

import com.github.NuclearDonut47.AlathraFishing.config.Config;
import net.kyori.adventure.text.Component;

import static org.bukkit.Bukkit.getServer;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import com.github.milkdrinkers.colorparser.ColorParser;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class CustomTools {
    private static Plugin plugin;
    private static final String[] defaultToolPaths = {"net", "custom_fishing_rod"};
    private static final Material[] baseItems = {Material.FISHING_ROD, Material.FISHING_ROD};
    private static final String[] names = {"Net", "Fishing Rod"};
    private static final int[] modelOverrides = {1, 2};
    private static final int[] durabilities = {64, 64};
    private static final String defaultMessage = "Default value(s) is (are) being used.";
    private static final HashMap<Material, Integer> vanillaConversions = new HashMap<>();
    private static NamespacedKey vanillaKey;

    public CustomTools(Plugin pluginInstance, Config config) {
        plugin = pluginInstance;
        ConfigurationSection toolsSection = config.getToolsSection();
        ConfigurationSection vanillaSection = config.getVanillaSection();

        if (toolsSection == null) {
            getServer().getLogger().info("tools section is missing from config.yml. " + defaultMessage);
            return;
        }

        for (int a = 0; a < defaultToolPaths.length; a++) {
            if (!toolsSection.contains(defaultToolPaths[a])) {
                getServer().getLogger().info(defaultToolPaths[a] +
                        " section is missing from tools section in config.yml. " +
                        defaultMessage);
                continue;
            }

            ConfigurationSection toolData = toolsSection.getConfigurationSection(defaultToolPaths[a]);

            if (toolData == null) {
                getServer().getLogger().info(defaultToolPaths[a] +
                        " section is missing from tools section in config.yml. " +
                        defaultMessage);
                continue;
            }

            setName(toolData, a);
            setItem(toolData, a);
            setModel(toolData, a);
            setDurabilities(toolData, a);
        }

        if (vanillaSection == null) {
            getServer().getLogger().info("vanilla section is missing from config.yml. " + defaultMessage);
            return;
        }

        if (!vanillaSection.contains("fishing_rod")) {
            getServer().getLogger().info("fishing_rod section is missing from vanilla section of config.yml" +
                    defaultMessage);
        }

        vanillaConversions.put(Material.FISHING_ROD, checkModelOriginality(vanillaSection.getInt("fishing_rod")));
        vanillaKey = new NamespacedKey(plugin, "vanilla_tool");
    }

    private void setItem(ConfigurationSection toolData, int a) {
        final String baseItem = toolData.getString("item");

        if (baseItem == null) {
            getServer().getLogger().info(defaultToolPaths[a] + ".name section is missing from config.yml. "
                    + defaultMessage);
            return;
        }

        final Material material = Material.getMaterial(baseItem);

        if (material == null) {
            getServer().getLogger().info("value at " + defaultToolPaths[a] +
                    ".item in config.yml is not a valid Material enum. " + defaultMessage);
            return;
        }

        if (material.getMaxDurability() == 0) {
            getServer().getLogger().info("item given at " + defaultToolPaths[a] +
                    ".item in config.yml is not an item with durability. " + defaultMessage);
            return;
        }

        baseItems[a] = material;
    }

    private void setName(ConfigurationSection toolData, int a) {
        String name = toolData.getString("name");

        if (name == null) {
            getServer().getLogger().info(defaultToolPaths[a] + ".name section is missing from config.yml. " +
                    defaultMessage);
            return;
        }

        names[a] = name;
    }

    private void setModel(ConfigurationSection toolData, int a) {
        int modelOverride = toolData.getInt("model");

        for (int b = 0; b < a; b++) {
            if (baseItems[b] != baseItems[a]) continue;

            if (modelOverrides[b] != modelOverrides[a]) continue;

            getServer().getLogger().info("item and model are the same for " + defaultToolPaths[b] + " and " +
                    defaultToolPaths[a] + ". CustomModelData for " + defaultToolPaths[a] + " will now be " +
                    (modelOverride + 1) + ".");
            modelOverride++;
            b = 0;
        }

        modelOverrides[a] = modelOverride;
    }

    private void setDurabilities(ConfigurationSection toolData, int a) {
        final int durability = toolData.getInt("durability");

        if (durability <= 0) {
            getServer().getLogger().info(defaultToolPaths[a] +
                    ".durability section is either 0 or missing from config.yml." + defaultMessage);
            return;
        }

        durabilities[a] = durability;
    }

    private int checkModelOriginality(int candidate) {
        for (int a = 0; a < modelOverrides.length; a++) {
            if (candidate != modelOverrides[a]) continue;

            candidate++;
            a = 0;
        }

        return candidate;
    }

    public ItemStack getTool(String tool) {
        int toolVal = -1;

        for (int a = 0; a < defaultToolPaths.length; a++) {
            if (defaultToolPaths[a].equals(tool)) {
                toolVal = a;
                break;
            }
        }

        if (!(-1 < toolVal)) {
            getServer().getLogger().info("Invalid String used to call tool in CustomItems.getTool().");
            return null;
        }

        ItemStack customItem = new ItemStack(baseItems[toolVal]);
        Damageable damageable = (Damageable) customItem.getItemMeta();

        Component name = ColorParser.of(names[toolVal]).build();

        NamespacedKey durKey= new NamespacedKey(plugin, defaultToolPaths[toolVal] + "_durability");
        NamespacedKey maxKey = new NamespacedKey(plugin, defaultToolPaths[toolVal] + "_max_durability");

        damageable.displayName(name);
        damageable.setCustomModelData(modelOverrides[toolVal]);
        damageable.getPersistentDataContainer().set(durKey, PersistentDataType.INTEGER, durabilities[toolVal]);
        damageable.getPersistentDataContainer().set(maxKey, PersistentDataType.INTEGER, durabilities[toolVal]);
        damageable.getPersistentDataContainer().set(vanillaKey, PersistentDataType.BOOLEAN, false);

        customItem.setItemMeta(damageable);

        return customItem;
    }

    public void convertVanillaTool(ItemStack item, int itemModel) {
        ItemMeta itemMeta = item.getItemMeta();

        if (!itemMeta.getPersistentDataContainer()
                .getOrDefault(vanillaKey, PersistentDataType.BOOLEAN, true)) return;

        if (itemModel != vanillaConversions.getOrDefault(item.getType(), itemModel))
            itemMeta.setCustomModelData(vanillaConversions.get(item.getType()));

        item.setItemMeta(itemMeta);
    }

    public Material[] getBaseItems(){
        return baseItems;
    }

    public int[] getModelOverrides() {
        return modelOverrides;
    }

    public String[] getDefaultToolPaths() {
        return defaultToolPaths;
    }

    public NamespacedKey getVanillaKey() {
        return vanillaKey;
    }
}