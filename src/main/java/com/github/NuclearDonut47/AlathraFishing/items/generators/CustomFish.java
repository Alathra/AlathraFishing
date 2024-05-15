package com.github.NuclearDonut47.AlathraFishing.items.generators;

import com.github.NuclearDonut47.AlathraFishing.config.Config;
import com.github.NuclearDonut47.AlathraFishing.items.Fish;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class CustomFish {
    private final static ArrayList<Fish> fish = new ArrayList<>();
    private final static String defaultMessage = "Default value(s) is (are) being used.";

    public CustomFish(Plugin plugin, Config config) {
        ConfigurationSection fishSection = config.getFishSection();

        if (fishSection == null)
            plugin.getLogger().info("fish section is missing from config.yml. " + defaultMessage);

        for (String fishPath : fishSection.getKeys(false)) {
            boolean necessaryInfoMissing = false;

            if (fishSection.getConfigurationSection(fishPath).getString("name") == null) {
                plugin.getLogger().info("Missing name at fish." + fishPath + "." + defaultMessage);
                necessaryInfoMissing = true;
            }

            if (fishSection.getConfigurationSection(fishPath).getString("item") == null) {
                plugin.getLogger().info("Missing item at fish." + fishPath + "." + defaultMessage);
                necessaryInfoMissing = true;
            }

            int model = modelCheck(fishSection.getConfigurationSection(fishPath));

            if (model == -1) {
                plugin.getLogger().info("Missing model at fish." + fishPath + "." + defaultMessage);
                necessaryInfoMissing = true;
            }

            if (!fishSection.getConfigurationSection(fishPath).getString("rarity").equals("abundant"))
                necessaryInfoMissing = true;

            if (necessaryInfoMissing) return;

            fish.add(new Fish(plugin, fishPath, fishSection.getConfigurationSection(fishPath), model,
                    config.getConfig().getDouble("min_fish_length"),
                    config.getConfig().getConfigurationSection("stretch").getInt(
                            fishSection.getConfigurationSection(fishPath).getString("rarity"), 0)));
        }
    }

    private int modelCheck(ConfigurationSection fishSection) {
        if (fishSection.getBoolean("no_model")) return 0;

        if (fishSection.getInt("model") != 0) return fishSection.getInt("model");

        return -1;
    }

    public ArrayList<Fish> getFish() {
        return fish;
    }
}
