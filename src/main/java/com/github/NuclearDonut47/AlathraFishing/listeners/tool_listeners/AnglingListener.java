package com.github.NuclearDonut47.AlathraFishing.listeners.tool_listeners;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;
import com.github.NuclearDonut47.AlathraFishing.rewards.RewardGenerator;
import com.github.NuclearDonut47.AlathraFishing.items.generators.CustomToolsManager;
import com.github.ipecter.rtu.biomelib.RTUBiomeLib;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.event.player.PlayerFishEvent.State;

public class AnglingListener extends ToolUseListener {
    private final RewardGenerator rewardGenerator;

    public AnglingListener(AlathraFishing plugin, CustomToolsManager tools, RewardGenerator rewardGeneratorInstance) {
        super(plugin, tools);
        rewardGenerator = rewardGeneratorInstance;
    }

    @EventHandler(priority = EventPriority.HIGHEST) @SuppressWarnings("unused")
    public void anglingFishing(PlayerFishEvent anglingEvent) {
        if (!customToolCheck(1, anglingEvent.getPlayer().getInventory().getItemInMainHand())) {
            vanillaAngling(anglingEvent);
            return;
        }

        if (anglingEvent.getState() == State.FISHING) {
            anglingEvent.getHook().setMinWaitTime(20*5);
            anglingEvent.getHook().setMaxWaitTime(20*30);
            return;
        }

        if (anglingEvent.getState() != State.CAUGHT_FISH) return;

        damageTool(anglingEvent.getPlayer().getInventory().getItemInMainHand(), anglingEvent.getPlayer(), 1);

        boolean openWater = anglingEvent.getHook().isInOpenWater();

        ItemStack reward = rewardGenerator.giveReward(false,
                RTUBiomeLib.getInterface().getBiomeName(anglingEvent.getHook().getLocation()), true);

        if (reward == null) {
            reward = new ItemStack(Material.AIR);
            anglingEvent.setExpToDrop(0);
        }

        Item itemEntity = (Item) anglingEvent.getCaught();

        itemEntity.setItemStack(reward);
    }

    private void vanillaAngling(PlayerFishEvent anglingEvent) {
        if (anglingEvent.getState() != State.CAUGHT_FISH) return;

        ItemStack reward = rewardGenerator.giveReward(true,
                RTUBiomeLib.getInterface().getBiomeName(anglingEvent.getHook().getLocation()), true);

        if (reward == null) {
            reward = new ItemStack(Material.AIR);
            anglingEvent.setExpToDrop(0);
        }

        Item itemEntity = (Item) anglingEvent.getCaught();

        itemEntity.setItemStack(reward);
    }
}
