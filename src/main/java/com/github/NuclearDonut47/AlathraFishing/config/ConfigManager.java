package com.github.NuclearDonut47.AlathraFishing.config;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;

public class ConfigManager {
    private static boolean initialized = false;

    public ConfigManager(AlathraFishing plugin) {
        if (initialized) return;

        Config.initConfig(plugin);
        initialized = true;
    }
}
