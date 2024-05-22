package com.github.NuclearDonut47.AlathraFishing.items.generators;

import com.github.NuclearDonut47.AlathraFishing.config.Config;
import com.github.NuclearDonut47.AlathraFishing.items.MiscLoot;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class CustomMiscLootManager {
    private final static ArrayList<MiscLoot> miscLoot = new ArrayList<>();
    private final static String defaultMessage = "Default value(s) is (are) being used.";

    public CustomMiscLootManager(Plugin plugin, Config config) {
        ConfigurationSection miscLootSection = config.getMiscLootSection();

        if (miscLootSection == null)
            plugin.getLogger().info("misc_loot section is missing from config.yml. " + defaultMessage);

        for (String miscLootPath : miscLootSection.getKeys(false)) {
            if (miscLootSection.getString(miscLootPath) == null) {
                plugin.getLogger().info("Missing item at fish." + miscLootPath + "." + defaultMessage);
                return;
            }

            miscLoot.add(new MiscLoot(plugin, miscLootPath, miscLootSection));
        }
    }

    public ArrayList<MiscLoot> getMiscLoot() {
        return miscLoot;
    }
}