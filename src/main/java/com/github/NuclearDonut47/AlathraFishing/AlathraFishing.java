package com.github.NuclearDonut47.AlathraFishing;

import com.github.NuclearDonut47.AlathraFishing.tool_listeners.NetListener;
import com.github.NuclearDonut47.AlathraFishing.tool_listeners.ToolUseListener;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.CitizensPlugin;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.NuclearDonut47.AlathraFishing.commands.TestCommand;
import com.github.NuclearDonut47.AlathraFishing.config.Config;
import com.github.NuclearDonut47.AlathraFishing.hooks.CitizensRightClickNPCListener;
import com.github.NuclearDonut47.AlathraFishing.items.CustomTools;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class AlathraFishing extends JavaPlugin {
	
    private static AlathraFishing instance;
    
    private CustomTools tools;
    
    private void initConfig() {
    	saveDefaultConfig();
    	Config.initConfigVals();
    }
    
    private void initListeners() {
    	
    	// Init tool use listeners
    	ArrayList<ToolUseListener> toolUseListeners = new ArrayList<>();
        ToolUseListener netListener = new NetListener(this, tools);
        toolUseListeners.add(netListener);
        for (ToolUseListener listener: toolUseListeners) {
            listener.registerListener();
        }
        
        // Check if the server us running Citizens, init citizens listener(s) if enabled
    	if (Bukkit.getServer().getPluginManager().getPlugin("Citizens") != null)  {
    		Bukkit.getLogger().info("[" + this.getName() + "] Citizens detected! Enabling support...");
    		Bukkit.getServer().getPluginManager().registerEvents((Listener) new CitizensRightClickNPCListener(), (Plugin) this);	
    	}
    	
    }
    
    private void initCommands() {
    	PluginCommand testCommand = this.getCommand("test");

        if (testCommand != null) {
            testCommand.setExecutor(new TestCommand(tools));
        }
    }
    
    @Override
    public void onLoad() {
        tools = new CustomTools(this, getConfig().getConfigurationSection("tools"));
    }
    

    @Override
    public void onEnable() {
    	
    	instance = this;
    	
    	initConfig();
        initListeners();
        initCommands();
    }
    
    public static AlathraFishing getInstance() {
    	return instance;
    }
}