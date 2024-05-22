package com.github.NuclearDonut47.AlathraFishing;

import com.github.NuclearDonut47.AlathraFishing.items.generators.CustomMiscLootManager;
import com.github.NuclearDonut47.AlathraFishing.listeners.table_listeners.CraftingListener;
import com.github.NuclearDonut47.AlathraFishing.listeners.table_listeners.SmeltListener;
import com.github.NuclearDonut47.AlathraFishing.listeners.tool_listeners.MendingRemovalListener;
import com.github.NuclearDonut47.AlathraFishing.rewards.RewardGenerator;
import com.github.NuclearDonut47.AlathraFishing.items.generators.CustomFishManager;
import com.github.NuclearDonut47.AlathraFishing.listeners.AlathraFishingListener;
import com.github.NuclearDonut47.AlathraFishing.listeners.table_listeners.AnvilListener;
import com.github.NuclearDonut47.AlathraFishing.listeners.table_listeners.EnchantmentListener;
import com.github.NuclearDonut47.AlathraFishing.listeners.tool_listeners.AnglingListener;
import com.github.NuclearDonut47.AlathraFishing.listeners.tool_listeners.NetListener;
import com.github.NuclearDonut47.AlathraFishing.recipes.Recipes;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.NuclearDonut47.AlathraFishing.commands.AlathraFishingCommands;
import com.github.NuclearDonut47.AlathraFishing.config.Config;
import com.github.NuclearDonut47.AlathraFishing.hooks.citizens.CitizensRightClickNPCListener;
import com.github.NuclearDonut47.AlathraFishing.hooks.citizens.NPCUUIDCommands;
import com.github.NuclearDonut47.AlathraFishing.items.generators.CustomToolsManager;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class AlathraFishing extends JavaPlugin {
    private void initHooks(Config config) {
        // Check if the server is running Citizens, init citizens related classes
        if (Bukkit.getServer().getPluginManager().getPlugin("Citizens") != null)  {
            getLogger().info("[" + this.getName() + "] Citizens detected! Enabling support...");
            Bukkit.getServer().getPluginManager().registerEvents(new CitizensRightClickNPCListener(config), this);
            new NPCUUIDCommands(this);
        }
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        FileConfiguration fileConfig = this.getConfig();
        Config config = new Config(this, fileConfig);

        CustomToolsManager tools = new CustomToolsManager(this, config);
        CustomFishManager fishManager = new CustomFishManager(this, config);
        CustomMiscLootManager miscLoot = new CustomMiscLootManager(this, config);

        RewardGenerator rewardGenerator = new RewardGenerator(this, config, tools, fishManager, miscLoot);

        ArrayList<AlathraFishingListener> alathraFishingListeners = new ArrayList<>();

        alathraFishingListeners.add(new NetListener(this, tools, rewardGenerator));
        alathraFishingListeners.add(new AnglingListener(this, tools, rewardGenerator));
        alathraFishingListeners.add(new EnchantmentListener(this, tools));
        alathraFishingListeners.add(new AnvilListener(this, tools));
        alathraFishingListeners.add(new MendingRemovalListener(this));
        alathraFishingListeners.add(new SmeltListener(this, fishManager));
        alathraFishingListeners.add(new CraftingListener(this, fishManager));

        for (AlathraFishingListener listener: alathraFishingListeners) listener.registerListener();

        new Recipes(this, config, tools).addRecipes();

        new AlathraFishingCommands(this, config);

        initHooks(config);
    }
}