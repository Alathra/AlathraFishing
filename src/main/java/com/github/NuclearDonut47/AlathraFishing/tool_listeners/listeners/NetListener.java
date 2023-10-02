package com.github.NuclearDonut47.AlathraFishing.tool_listeners.listeners;

import com.github.NuclearDonut47.AlathraFishing.AlathraFishing;
import com.github.NuclearDonut47.AlathraFishing.items.CustomTools;
import com.github.NuclearDonut47.AlathraFishing.tool_listeners.schedulers.NetFishingEvent;
import com.github.milkdrinkers.colorparser.ColorParser;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;

// Failing restarts net fishing.

public class NetListener extends ToolUseListener {
    private final Component prepareMessage = ColorParser.of("You look at the water and prepare to cast your net.").build();
    private final Component cancelMessage = ColorParser.of("You look away from the water.").build();
    private final Component castMessage = ColorParser.of("You cast your net.").build();
    private final Component failMessage = ColorParser.of("Your net came up empty.").build();
    private final ArrayList<NetFishingEvent> activeNetTasks = new ArrayList<>();

    public NetListener(AlathraFishing pluginInstance, CustomTools toolsInstance) {
        super(pluginInstance, toolsInstance);
    }

    public NetFishingEvent netFishingCheck(Player player) {
        for (NetFishingEvent activeTask : activeNetTasks) {
            if (!activeTask.isPlayer(player)) continue;

            return activeTask;
        }

        return null;
    }

    private boolean correctEventCheck(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return false;
        // return if event is not using main hand

        if (event.getAction().isLeftClick()) return false;
        // return if event is not right click
        // API is broken. Listener will trip twice, once for right-click, once for left-click.
        // This only happens for right-clicking. Left-clicking will

        return toolCheck(0, event.getItem());
        // Item is net.
    }

    private void netPrepare(PlayerInteractEvent prepareEvent) {
        Player player = prepareEvent.getPlayer();

        if (player.getTargetBlock(null, 16).getType() != Material.WATER) return;
        // Stop doing stuff if player is not looking at water.

        player.sendMessage(prepareMessage);
        // Tells player they are casting their net before fishing hook entity is deleted.

        Location nettingLocation = player.getTargetBlock(null, 16).getLocation();

        nettingLocation.add(0.5, 1.0, 0.5);
        // Get location to watch for fishing.

        NetFishingEvent netFishingEvent = new NetFishingEvent(this, player, nettingLocation);

        netFishingEvent.runTaskTimer(plugin, 1, 1);
        // Begin net fishing

        activeNetTasks.add(netFishingEvent);
        // Add net fishing task to active net fishing tasks.

        Bukkit.getServer().getLogger().info("end of prep listener");
    }

    private void netCast(PlayerInteractEvent castEvent, NetFishingEvent netFishingEvent) {
        Bukkit.getServer().getLogger().info("cast event passes");

        netFishingEvent.leavePreparationPhase();
        castEvent.getPlayer().sendMessage(castMessage);

        castEvent.getPlayer().playSound(castEvent.getPlayer(), Sound.ENTITY_FISHING_BOBBER_THROW, 1.0f, 1.0f);
    }

    public ItemStack damageTool(ItemStack usedItem, Player player) {
        player.playSound(player, Sound.ENTITY_FISHING_BOBBER_RETRIEVE, 1.0f, 1.0f);

        ItemMeta usedItemMeta = toolUse(new NamespacedKey(plugin, tools.getDefaultToolPaths()[0] + "_durability"),
                new NamespacedKey(plugin, tools.getDefaultToolPaths()[0] + "_max_durability"),
                usedItem);

        if (usedItemMeta == null) {
            player.playSound(player, Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
            return new ItemStack(Material.AIR);
        }

        usedItem.setItemMeta(usedItemMeta);

        return usedItem;
    }

    public ItemStack cancelEvent(NetFishingEvent existingTask, ItemStack item) {
        existingTask.cancel();
        activeNetTasks.remove(existingTask);

        if (existingTask.getPreparationPhase()) {
            existingTask.getPlayer().sendMessage(cancelMessage);
            return item;
        }

        return damageTool(item, existingTask.getPlayer());
    }

    private void netReel(PlayerInteractEvent reelEvent, NetFishingEvent netFishingEvent) {
        Bukkit.getServer().getLogger().info("reel event passes");

        reelEvent.getPlayer().getInventory().setItemInMainHand(cancelEvent(netFishingEvent, reelEvent.getItem()));

        if (netFishingEvent.isFishCatchable()) {
            Location nettingLocation = netFishingEvent.getNettingLocation();
            Location playerLocation = reelEvent.getPlayer().getLocation();
            ItemStack reward = new ItemStack(Material.ENCHANTED_BOOK);

            ItemMeta rewardMeta = reward.getItemMeta();

            rewardMeta.addEnchant(Enchantment.MENDING, 1, false);

            reward.setItemMeta(rewardMeta);

            Item rewardDrop = reelEvent.getPlayer().getWorld().dropItem(nettingLocation, reward);

            Vector velocity = new Vector((playerLocation.getX() - nettingLocation.getX()) / 20,
                    (8 * (playerLocation.getY() - nettingLocation.getY()) / 20),
                    (8 * (playerLocation.getZ() - nettingLocation.getZ()) / 20));

            rewardDrop.setVelocity(velocity);
            return;
        }

        reelEvent.getPlayer().sendMessage(failMessage);
    }

    @EventHandler @SuppressWarnings("unused")
    public void netListener(PlayerInteractEvent netEvent) {
        if (!correctEventCheck(netEvent)) return;

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

        netReel(netEvent, existingTask);
    }

    @EventHandler @SuppressWarnings("unused")
    public void midNetItemSwap(InventoryClickEvent clickEvent) {
        if (!(clickEvent.getAction() == InventoryAction.PICKUP_ALL) ||
                (clickEvent.getAction() == InventoryAction.PICKUP_HALF)) return;

        if (!(toolCheck(0, clickEvent.getCurrentItem()))) return;

        if (!(clickEvent.getWhoClicked() instanceof Player)) return;

        NetFishingEvent existingTask = netFishingCheck((Player) clickEvent.getWhoClicked());

        if (existingTask == null) return;

        clickEvent.setCurrentItem(cancelEvent(existingTask, clickEvent.getCurrentItem()));
    }

    @EventHandler @SuppressWarnings("unused")
    public void midNetDrop(ItemSpawnEvent dropEvent) {
        if (!(toolCheck(0, dropEvent.getEntity().getItemStack()))) return;

        Player player = Bukkit.getPlayer(dropEvent.getEntity().getThrower());

        if (player == null) return;

        NetFishingEvent existingTask = netFishingCheck(player);

        if (existingTask == null) return;

        dropEvent.getEntity().setItemStack(cancelEvent(existingTask, dropEvent.getEntity().getItemStack()));
    }
}