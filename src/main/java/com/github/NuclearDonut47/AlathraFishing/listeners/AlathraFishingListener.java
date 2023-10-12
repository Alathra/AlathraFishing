package com.github.NuclearDonut47.AlathraFishing.listeners;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public abstract class AlathraFishingListener implements Listener {
    protected static Plugin plugin;

    protected AlathraFishingListener(Plugin pluginInstance) {
        plugin = pluginInstance;
    }

    public final void registerListener() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
