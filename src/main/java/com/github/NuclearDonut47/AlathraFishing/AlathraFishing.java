package com.github.NuclearDonut47.AlathraFishing;

import com.github.NuclearDonut47.AlathraFishing.rewards.RewardGenerator;
import com.github.NuclearDonut47.AlathraFishing.items.CustomFish;
import com.github.NuclearDonut47.AlathraFishing.listeners.AlathraFishingListener;
import com.github.NuclearDonut47.AlathraFishing.listeners.table_listeners.AnvilListener;
import com.github.NuclearDonut47.AlathraFishing.listeners.table_listeners.EnchantmentListener;
import com.github.NuclearDonut47.AlathraFishing.listeners.tool_listeners.AnglingListener;
import com.github.NuclearDonut47.AlathraFishing.listeners.tool_listeners.NetListener;
import com.github.NuclearDonut47.AlathraFishing.recipes.Recipes;
import com.github.NuclearDonut47.AlathraFishing.util.Biomes;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.NuclearDonut47.AlathraFishing.commands.AlathraFishingCommands;
import com.github.NuclearDonut47.AlathraFishing.config.Config;
import com.github.NuclearDonut47.AlathraFishing.hooks.citizens.CitizensRightClickNPCListener;
import com.github.NuclearDonut47.AlathraFishing.hooks.citizens.NPCUUIDCommands;
import com.github.NuclearDonut47.AlathraFishing.items.CustomTools;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class AlathraFishing extends JavaPlugin {
    private void initHooks(Config config) {
        // Check if the server is running Citizens, init citizens related classes
        if (Bukkit.getServer().getPluginManager().getPlugin("Citizens") != null)  {
            Bukkit.getLogger().info("[" + this.getName() + "] Citizens detected! Enabling support...");
            Bukkit.getServer().getPluginManager().registerEvents(new CitizensRightClickNPCListener(config), this);
            new NPCUUIDCommands(this);
        }
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        FileConfiguration fileConfig = this.getConfig();
        Config config = new Config(this, fileConfig);

        CustomTools tools = new CustomTools(this, config);
        CustomFish fish = new CustomFish(this, config);

        RewardGenerator rewardGenerator = new RewardGenerator(fish);

        Biomes biomes = null;

        if (Bukkit.getServer().getPluginManager().getPlugin("Terra") != null) biomes = new Biomes(config);

        ArrayList<AlathraFishingListener> alathraFishingListeners = new ArrayList<>();

        alathraFishingListeners.add(new NetListener(this, tools, rewardGenerator));
        alathraFishingListeners.add(new AnglingListener(this, tools, rewardGenerator));
        alathraFishingListeners.add(new EnchantmentListener(this, tools));
        alathraFishingListeners.add(new AnvilListener(this, tools));

        for (AlathraFishingListener listener: alathraFishingListeners) listener.registerListener();

        new Recipes(this, config, tools).addRecipes();

        new AlathraFishingCommands(this, config);

        initHooks(config);
    }
}