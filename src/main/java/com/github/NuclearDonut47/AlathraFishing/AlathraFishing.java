package com.github.NuclearDonut47.AlathraFishing;

import com.github.NuclearDonut47.AlathraFishing.tool_listeners.NetListener;
import com.github.NuclearDonut47.AlathraFishing.tool_listeners.ToolUseListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import com.github.NuclearDonut47.AlathraFishing.items.CustomTools;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class AlathraFishing extends JavaPlugin {
    private CustomTools tools;
    
    @Override
    public void onLoad() {
        tools = new CustomTools(this, getConfig().getConfigurationSection("tools"));
    }

    @Override
    public void onEnable() {
        ArrayList<ToolUseListener> toolUseListeners = new ArrayList<>();

        ToolUseListener netListener = new NetListener(this, tools);

        toolUseListeners.add(netListener);

        for (ToolUseListener listener: toolUseListeners) {
            listener.registerListener();
        }

        PluginCommand testCommand = this.getCommand("test");

        if (testCommand != null) {
            testCommand.setExecutor(new TestCommand(tools));
        }
    }
}