package com.github.NuclearDonut47.AlathraFishing.listeners.tool_listeners;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;
import com.github.NuclearDonut47.AlathraFishing.rewards.RewardGenerator;
import com.github.NuclearDonut47.AlathraFishing.items.generators.CustomToolsManager;
import com.github.NuclearDonut47.AlathraFishing.listeners.schedulers.NetFishingEvent;
import com.github.ipecter.rtu.biomelib.RTUBiomeLib;
import com.github.milkdrinkers.colorparser.ColorParser;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NetListener extends ToolUseListener {
    private final RewardGenerator rewardGenerator;
    private static final ArrayList<Material> interactables = new ArrayList<>(List.of(Material.ACACIA_BUTTON,
            Material.ACACIA_DOOR, Material.ACACIA_FENCE_GATE, Material.ACACIA_HANGING_SIGN, Material.ACACIA_SIGN,
            Material.ACACIA_TRAPDOOR, Material.ACACIA_WALL_HANGING_SIGN, Material.ACACIA_WALL_SIGN, Material.ANVIL,
            Material.BAMBOO_BUTTON, Material.BAMBOO_DOOR, Material.BAMBOO_FENCE_GATE, Material.BAMBOO_HANGING_SIGN,
            Material.BAMBOO_SIGN, Material.BAMBOO_TRAPDOOR, Material.BAMBOO_WALL_HANGING_SIGN,
            Material.BAMBOO_WALL_SIGN, Material.BARREL, Material.BEACON, Material.BELL, Material.BIRCH_BUTTON,
            Material.BIRCH_DOOR, Material.BIRCH_FENCE_GATE, Material.BIRCH_HANGING_SIGN, Material.BIRCH_SIGN,
            Material.BIRCH_TRAPDOOR, Material.BIRCH_WALL_HANGING_SIGN, Material.BIRCH_WALL_SIGN, Material.BLACK_BED,
            Material.BLACK_SHULKER_BOX, Material.BLAST_FURNACE, Material.BLUE_BED, Material.BLUE_SHULKER_BOX,
            Material.BREWING_STAND, Material.BROWN_BED, Material.BROWN_SHULKER_BOX, Material.CARTOGRAPHY_TABLE,
            Material.CHERRY_BUTTON, Material.CHERRY_DOOR, Material.CHERRY_FENCE_GATE, Material.CHERRY_HANGING_SIGN,
            Material.CHERRY_SIGN, Material.CHERRY_TRAPDOOR, Material.CHERRY_WALL_HANGING_SIGN,
            Material.CHERRY_WALL_SIGN, Material.CHEST, Material.CHIPPED_ANVIL, Material.COMPARATOR,
            Material.CRAFTING_TABLE, Material.CRIMSON_BUTTON, Material.CRIMSON_DOOR, Material.CRIMSON_FENCE_GATE,
            Material.CRIMSON_HANGING_SIGN, Material.CRIMSON_SIGN, Material.CRIMSON_TRAPDOOR,
            Material.CRIMSON_WALL_HANGING_SIGN, Material.CRIMSON_WALL_SIGN, Material.CYAN_BED,
            Material.CYAN_SHULKER_BOX, Material.DAMAGED_ANVIL, Material.DARK_OAK_BUTTON, Material.DARK_OAK_DOOR,
            Material.DARK_OAK_FENCE_GATE, Material.DARK_OAK_HANGING_SIGN, Material.DARK_OAK_SIGN,
            Material.DARK_OAK_TRAPDOOR, Material.DARK_OAK_WALL_HANGING_SIGN, Material.DARK_OAK_WALL_SIGN,
            Material.DAYLIGHT_DETECTOR, Material.DECORATED_POT, Material.DEEPSLATE_REDSTONE_ORE, Material.DISPENSER,
            Material.DROPPER, Material.ENCHANTING_TABLE, Material.ENDER_CHEST, Material.FURNACE, Material.GRAY_BED,
            Material.GRAY_SHULKER_BOX, Material.GREEN_BED, Material.GREEN_SHULKER_BOX, Material.GRINDSTONE,
            Material.HOPPER, Material.JUKEBOX, Material.JUNGLE_BUTTON, Material.JUNGLE_DOOR, Material.JUNGLE_FENCE_GATE,
            Material.JUNGLE_HANGING_SIGN, Material.JUNGLE_SIGN, Material.JUNGLE_TRAPDOOR,
            Material.JUNGLE_WALL_HANGING_SIGN, Material.JUNGLE_WALL_SIGN, Material.LECTERN, Material.LEVER,
            Material.LIGHT_BLUE_BED, Material.LIGHT_BLUE_SHULKER_BOX, Material.LIGHT_GRAY_BED,
            Material.LIGHT_GRAY_SHULKER_BOX, Material.LIME_BED, Material.LIME_SHULKER_BOX, Material.LOOM,
            Material.MAGENTA_BED, Material.MAGENTA_SHULKER_BOX, Material.MANGROVE_BUTTON, Material.MANGROVE_DOOR,
            Material.MANGROVE_FENCE_GATE, Material.MANGROVE_HANGING_SIGN, Material.MANGROVE_SIGN,
            Material.MANGROVE_TRAPDOOR, Material.MANGROVE_WALL_HANGING_SIGN, Material.MANGROVE_WALL_SIGN,
            Material.NOTE_BLOCK, Material.OAK_BUTTON, Material.OAK_DOOR, Material.OAK_FENCE_GATE,
            Material.OAK_HANGING_SIGN, Material.OAK_SIGN, Material.OAK_TRAPDOOR, Material.OAK_WALL_HANGING_SIGN,
            Material.OAK_WALL_SIGN, Material.ORANGE_BED, Material.ORANGE_SHULKER_BOX, Material.PINK_BED,
            Material.PINK_SHULKER_BOX, Material.POLISHED_BLACKSTONE_BUTTON, Material.PURPLE_BED,
            Material.PURPLE_SHULKER_BOX, Material.REDSTONE_ORE, Material.REDSTONE_WIRE, Material.RED_BED,
            Material.RED_SHULKER_BOX, Material.REPEATER, Material.SHULKER_BOX, Material.SMITHING_TABLE, Material.SMOKER,
            Material.STONECUTTER, Material.SPRUCE_BUTTON, Material.SPRUCE_DOOR, Material.SPRUCE_FENCE_GATE,
            Material.SPRUCE_HANGING_SIGN, Material.SPRUCE_SIGN, Material.SPRUCE_TRAPDOOR,
            Material.SPRUCE_WALL_HANGING_SIGN, Material.SPRUCE_WALL_SIGN, Material.STONE_BUTTON, Material.TRAPPED_CHEST,
            Material.WARPED_BUTTON, Material.WARPED_DOOR, Material.WARPED_FENCE_GATE, Material.WARPED_HANGING_SIGN,
            Material.WARPED_SIGN, Material.WARPED_TRAPDOOR, Material.WARPED_WALL_HANGING_SIGN,
            Material.WARPED_WALL_SIGN, Material.WHITE_BED, Material.WHITE_SHULKER_BOX, Material.YELLOW_BED,
            Material.YELLOW_SHULKER_BOX));
    private static final Component prepareMessage =
            ColorParser.of("You look at the water and prepare to cast your net.").build();
    private static final Component cancelMessage = ColorParser.of("You look away from the water.").build();
    private static final Component castMessage =
            ColorParser.of("You cast your net. Right-click again to pull it in!").build();
    private static final Component failMessage = ColorParser.of("Your net came up empty.").build();
    private static final ArrayList<NetFishingEvent> activeNetTasks = new ArrayList<>();
    private static final Random random = new Random();

    public NetListener(AlathraFishing plugin, CustomToolsManager tools, RewardGenerator rewardGeneratorInstance) {
        super(plugin, tools);
        rewardGenerator = rewardGeneratorInstance;
    }

    @EventHandler(priority = EventPriority.HIGHEST) @SuppressWarnings("unused")
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

        if (netEvent.getPlayer().isUnderWater()) return;

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

    @EventHandler(priority = EventPriority.HIGHEST) @SuppressWarnings("unused")
    public void midNetItemSwap(InventoryClickEvent clickEvent) {
        if (!(clickEvent.getAction() == InventoryAction.PICKUP_ALL) ||
                (clickEvent.getAction() == InventoryAction.PICKUP_HALF)) return;

        if (!(customToolCheck(0, clickEvent.getCurrentItem()))) return;

        if (!(clickEvent.getWhoClicked() instanceof Player)) return;

        NetFishingEvent existingTask = netFishingCheck((Player) clickEvent.getWhoClicked());

        if (existingTask == null) return;

        cancelEvent(existingTask, clickEvent.getCurrentItem());
    }

    @EventHandler(priority = EventPriority.HIGHEST) @SuppressWarnings("unused")
    public void midNetDrop(ItemSpawnEvent dropEvent) {
        if (!(customToolCheck(0, dropEvent.getEntity().getItemStack()))) return;

        Player player = Bukkit.getPlayer(dropEvent.getEntity().getThrower());

        if (player == null) return;

        NetFishingEvent existingTask = netFishingCheck(player);

        if (existingTask == null) return;

        cancelEvent(existingTask, dropEvent.getEntity().getItemStack());
    }

    private boolean checkInteractable(Block block) {
        if (block == null) return false;

        return interactables.contains(block.getType());
    }

    private NetFishingEvent netFishingCheck(Player player) {
        for (NetFishingEvent activeTask : activeNetTasks) {
            if (!activeTask.isPlayer(player)) continue;

            return activeTask;
        }

        return null;
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
            int lootAmount = random.nextInt(1, 6);

            playerLocation.add(0, 2, 0);

            for (int a = 0; a < lootAmount; a++) {
                ItemStack reward = rewardGenerator.giveReward(true,
                        RTUBiomeLib.getInterface().getBiomeName(nettingLocation), true);

                if (reward == null) return;

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

        existingTask.getPlayer().playSound(existingTask.getPlayer(),
                Sound.ENTITY_FISHING_BOBBER_RETRIEVE, 1.0f, 1.0f);

        damageTool(item, existingTask.getPlayer(), 0);
    }
}