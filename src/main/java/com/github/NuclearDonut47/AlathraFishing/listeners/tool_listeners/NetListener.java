package com.github.NuclearDonut47.AlathraFishing.listeners.tool_listeners;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;
import com.github.NuclearDonut47.AlathraFishing.fish.RewardGenerator;
import com.github.NuclearDonut47.AlathraFishing.items.CustomTools;
import com.github.NuclearDonut47.AlathraFishing.listeners.schedulers.NetFishingEvent;
import com.github.milkdrinkers.colorparser.ColorParser;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;

public class NetListener extends ToolUseListener {
    private final RewardGenerator rewardGenerator;
    private static final Component prepareMessage = ColorParser.of("You look at the water and prepare to cast your net.").build();
    private static final Component cancelMessage = ColorParser.of("You look away from the water.").build();
    private static final Component castMessage = ColorParser.of("You cast your net. Right-click again to pull it in!").build();
    private static final Component failMessage = ColorParser.of("Your net came up empty.").build();
    private static final ArrayList<NetFishingEvent> activeNetTasks = new ArrayList<>();
    private static final Random random = new Random();

    public NetListener(AlathraFishing plugin, CustomTools tools, RewardGenerator rewardGeneratorInstance) {
        super(plugin, tools);
        rewardGenerator = rewardGeneratorInstance;
    }

    @EventHandler @SuppressWarnings("unused")
    public void netListener(PlayerInteractEvent netEvent) {
        if (netEvent.getHand() != EquipmentSlot.HAND) return;

        if (netEvent.getAction().isLeftClick()) return;
        // API is broken at time of writing.
        // Listener will trip twice, once for right-click, once for left-click.
        // This only happens for right-clicking.
        // Left-clicking will trip event for two left-clicks.

        if (!customToolCheck(0, netEvent.getItem())) return;

        if (checkInteractable(netEvent.getClickedBlock())) return;

        netEvent.setCancelled(true);

        NetFishingEvent existingTask = netFishingCheck(netEvent.getPlayer());

        if (existingTask == null) {
            netPrepare(netEvent);
            return;
        }

        if (existingTask.getPreparationPhase()) {
            netCast(netEvent, existingTask);
            return;
        }

        netPull(netEvent, existingTask);
    }

    @EventHandler @SuppressWarnings("unused")
    public void midNetItemSwap(InventoryClickEvent clickEvent) {
        if (!(clickEvent.getAction() == InventoryAction.PICKUP_ALL) ||
                (clickEvent.getAction() == InventoryAction.PICKUP_HALF)) return;

        if (!(customToolCheck(0, clickEvent.getCurrentItem()))) return;

        if (!(clickEvent.getWhoClicked() instanceof Player)) return;

        NetFishingEvent existingTask = netFishingCheck((Player) clickEvent.getWhoClicked());

        if (existingTask == null) return;

        cancelEvent(existingTask, clickEvent.getCurrentItem());
    }

    @EventHandler @SuppressWarnings("unused")
    public void midNetDrop(ItemSpawnEvent dropEvent) {
        if (!(customToolCheck(0, dropEvent.getEntity().getItemStack()))) return;

        Player player = Bukkit.getPlayer(dropEvent.getEntity().getThrower());

        if (player == null) return;

        NetFishingEvent existingTask = netFishingCheck(player);

        if (existingTask == null) return;

        cancelEvent(existingTask, dropEvent.getEntity().getItemStack());
    }

    private NetFishingEvent netFishingCheck(Player player) {
        for (NetFishingEvent activeTask : activeNetTasks) {
            if (!activeTask.isPlayer(player)) continue;

            return activeTask;
        }

        return null;
    }

    private boolean checkInteractable(Block block) {
        if (block == null) return false;

        return block.getType().isInteractable();
    }

    private void netPrepare(PlayerInteractEvent prepareEvent) {
        Player player = prepareEvent.getPlayer();

        if (player.getTargetBlock(null, 16).getType() != Material.WATER) return;

        player.sendMessage(prepareMessage);

        Location nettingLocation = player.getTargetBlock(null, 16).getLocation();

        nettingLocation.add(0.5, 1.0, 0.5);

        NetFishingEvent netFishingEvent = new NetFishingEvent(this, player, nettingLocation);

        netFishingEvent.runTaskTimer(plugin, 1, 1);

        activeNetTasks.add(netFishingEvent);
    }

    private void netCast(PlayerInteractEvent castEvent, NetFishingEvent netFishingEvent) {
        netFishingEvent.leavePreparationPhase();
        castEvent.getPlayer().sendMessage(castMessage);

        castEvent.getPlayer().playSound(castEvent.getPlayer(), Sound.ENTITY_EGG_THROW, 1.0f, 1.0f);
    }

    private void netPull(PlayerInteractEvent pullEvent, NetFishingEvent netFishingEvent) {
        if (netFishingEvent.getCobwebsPresent()) {
            netFishingEvent.removeCobwebs();
            return;
            // Early return because API is broken here, too.
            // PlayerInteractEvent listener happens an erroneous extra time when
            // the cobwebs are removed in the same tick.
            // By not cancelling until the cobwebs were already removed, prepareCast doesn't run again.
            // Using blockdata vs blocks has no effect on this (this plugin uses blockstates right now, anyway).
            // As such, this is likely the most elegant solution.
        }

        cancelEvent(netFishingEvent, pullEvent.getItem());

        if (netFishingEvent.isFishCatchable()) {
            Location nettingLocation = netFishingEvent.getNettingLocation();
            Location playerLocation = pullEvent.getPlayer().getLocation();
            int lootAmount = random.nextInt(1, 8);

            playerLocation.add(0, 2, 0);

            for (int a = 0; a < lootAmount; a++) {
                ItemStack reward = new ItemStack(Material.LILY_PAD);

                Item rewardDrop = pullEvent.getPlayer().getWorld().dropItem(nettingLocation, reward);

                Vector velocity = new Vector(2 * ((playerLocation.getX() - nettingLocation.getX()) / 20),
                        (2 * (playerLocation.getY() - nettingLocation.getY()) / 20),
                        (2 * (playerLocation.getZ() - nettingLocation.getZ()) / 20));

                rewardDrop.setVelocity(velocity);
            }

            return;
        }

        pullEvent.getPlayer().sendMessage(failMessage);
    }

    public void cancelEvent(NetFishingEvent existingTask, ItemStack item) {
        existingTask.cancel();
        activeNetTasks.remove(existingTask);

        if (existingTask.getPreparationPhase()) {
            existingTask.getPlayer().sendMessage(cancelMessage);
            return;
        }

        if (existingTask.getCobwebsPresent()) existingTask.removeCobwebs();

        damageTool(item, existingTask.getPlayer());
    }

    protected void damageTool(ItemStack usedItem, Player player) {
        player.playSound(player, Sound.ENTITY_FISHING_BOBBER_RETRIEVE, 1.0f, 1.0f);

        ItemMeta usedItemMeta = toolUse(new NamespacedKey(plugin, tools.getDefaultToolPaths()[0] + "_durability"),
                new NamespacedKey(plugin, tools.getDefaultToolPaths()[0] + "_max_durability"),
                usedItem);

        if (usedItemMeta == null) {
            player.playSound(player, Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
            usedItem.subtract();
            return;
        }

        usedItem.setItemMeta(usedItemMeta);
    }
}