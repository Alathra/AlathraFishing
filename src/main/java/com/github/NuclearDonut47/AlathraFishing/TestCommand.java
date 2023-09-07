package com.github.NuclearDonut47.AlathraFishing;

import com.github.NuclearDonut47.AlathraFishing.items.CustomTools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    private final CustomTools tools;

    public TestCommand(CustomTools toolsInstance) {
        tools = toolsInstance;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player player = (Player) sender;
        player.getInventory().addItem(tools.getTool("trowel"));
        player.getInventory().addItem(tools.getTool("catcher"));
        player.getInventory().addItem(tools.getTool("net"));
        return true;
    }
}