package com.github.NuclearDonut47.AlathraFishing;

import com.github.NuclearDonut47.AlathraFishing.listeners.AlathraFishingListener;
import com.github.NuclearDonut47.AlathraFishing.listeners.misc.EnchantmentListener;
import com.github.NuclearDonut47.AlathraFishing.listeners.tool_listeners.AnglingListener;
import com.github.NuclearDonut47.AlathraFishing.listeners.tool_listeners.NetListener;
import com.github.NuclearDonut47.AlathraFishing.util.BiomeUtil;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.NuclearDonut47.AlathraFishing.commands.AlathraFishingCommands;
import com.github.NuclearDonut47.AlathraFishing.commands.TestCommand;
import com.github.NuclearDonut47.AlathraFishing.config.Config;
import com.github.NuclearDonut47.AlathraFishing.hooks.citizens.CitizensRightClickNPCListener;
import com.github.NuclearDonut47.AlathraFishing.hooks.citizens.NPCUUIDCommands;
import com.github.NuclearDonut47.AlathraFishing.items.CustomTools;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class AlathraFishing extends JavaPlugin {
    private static CustomTools tools;
    private static Config config;
    private final static ArrayList<AlathraFishingListener> alathraFishingListeners = new ArrayList<>();
    
    private void initConfig() {
        FileConfiguration fileConfig = this.getConfig();
        config = new Config(this, fileConfig);

    	saveDefaultConfig();
    }
    
    private void initListeners() {
        alathraFishingListeners.add(new NetListener(this, tools));
        alathraFishingListeners.add(new AnglingListener(this, tools));
        alathraFishingListeners.add(new EnchantmentListener(this, tools));

        for (AlathraFishingListener listener: alathraFishingListeners) {
            listener.registerListener();
        }
    }
    
    private void initCommands() {
    	PluginCommand testCommand = this.getCommand("test");

        if (testCommand != null) {
            testCommand.setExecutor(new TestCommand(tools));
        }
        
        new AlathraFishingCommands(this, config);
    }
    
    private void initHooks() {
    	// Check if the server us running Citizens, init citizens related classes
    	if (Bukkit.getServer().getPluginManager().getPlugin("Citizens") != null)  {
    		Bukkit.getLogger().info("[" + this.getName() + "] Citizens detected! Enabling support...");
    		Bukkit.getServer().getPluginManager().registerEvents(new CitizensRightClickNPCListener(config), this);
    		new NPCUUIDCommands(this);
    	}
    	BiomeUtil.init(config);
    }

    @Override
    public void onEnable() {
    	initConfig();
    	tools = new CustomTools(this, config);
        initListeners();
        initCommands();
        initHooks();
    }
}