package com.github.NuclearDonut47.AlathraFishing.listeners.schedulers;

import com.github.NuclearDonut47.AlathraFishing.listeners.tool_listeners.NetListener;
import com.github.milkdrinkers.colorparser.ColorParser;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public final class NetFishingEvent extends BukkitRunnable {
    private final NetListener netListener;
    private final Player player;
    private final Location nettingLocation;
    private final ArrayList<Location> nettingArea = new ArrayList<>();
    private int waitTime;
    private int count;
    private int stopCount;
    private final Random random = new Random();
    private final Component timeoutMessage = ColorParser.of("The fish got away...").build();
    private boolean fishCatchable;
    private boolean preparationPhase;
    private boolean cobwebsPresent;
    private final int slot;

    public NetFishingEvent(NetListener listener, Player netPlayer, Location location) {
        netListener = listener;
        player = netPlayer;
        nettingLocation = location;
        waitTime = 20 * random.nextInt(5, 30);
        count = 0;
        fishCatchable = false;
        preparationPhase = true;
        cobwebsPresent = false;

        detectWaterSurface();
        slot = player.getInventory().getHeldItemSlot();
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    private void detectWaterSurface() {
        double yAdjust = -0.1;

        for (int a = 0; a < 25; a++) {
            double xAdjust = (a % 5) - 2;
            double zAdjust = (a / 5) - 2;

            if (Math.pow(Math.pow(xAdjust, 2) + Math.pow(zAdjust, 2), 0.5) >= 2.5) continue;

            Location spotCandidate = new Location(nettingLocation.getWorld(),
                    nettingLocation.getX() + xAdjust,
                    nettingLocation.getY() + yAdjust,
                    nettingLocation.getZ() + zAdjust);

            if (spotCandidate.getBlock().getType() != Material.WATER) continue;

            spotCandidate.add(0, -(2 * yAdjust), 0);

            if (spotCandidate.getBlock().getType() == Material.AIR)
                nettingArea.add(spotCandidate.add(0, yAdjust, 0));
        }
    }

    @Override
    public void run() {
        checkCancelConditions();

        if (preparationPhase) {
            particleSpawner(Particle.WATER_DROP, 5);
            prepareCast();
            count++;
            return;
        }

        particleSpawner(Particle.WATER_DROP, 1);

        if (count > stopCount) return;

        if (count + 10 == stopCount) spawnCobwebs();

        if (count == stopCount) {
            removeCobwebs();
            player.playSound(player, Sound.ENTITY_FISHING_BOBBER_SPLASH, 1.0f, 1.0f);
        }

        count++;
    }


    private void checkCancelConditions() {
        if (player.getInventory().getHeldItemSlot() != slot) {
            netListener.cancelEvent(this, player.getInventory().getItem(slot));
            return;
        }

        if (player.getLocation().distance(nettingLocation) > 8)
            netListener.cancelEvent(this, player.getInventory().getItemInMainHand());
    }

    private void particleSpawner(Particle particle, int amount) {
        for (int a = 0; a < amount; a++) {
            int particleBlock = random.nextInt(nettingArea.size());
            double xAdjust = random.nextDouble() - 0.5;
            double zAdjust = random.nextDouble() - 0.5;

            Location particleSpot = new Location(nettingLocation.getWorld(),
                    nettingArea.get(particleBlock).getX() + xAdjust,
                    nettingArea.get(particleBlock).getY(),
                    nettingArea.get(particleBlock).getZ() + zAdjust);

            player.getWorld().spawnParticle(particle, particleSpot, 1);
        }
    }

    private void prepareCast() {
        if (count < waitTime) return;

        if (count == waitTime) fishCatchable = true;

        if (count < (waitTime + 60)) particleSpawner(Particle.WATER_BUBBLE, 5);

        if (count >= (waitTime + 60)) {
            count = 0;
            fishCatchable = false;
            waitTime = 20 * random.nextInt(5, 30);
            player.sendMessage(timeoutMessage);
        }
    }

    private void spawnCobwebs() {
        for (Location location : nettingArea) {
            if (invalidNetBlocksCheck(location)) continue;

            player.sendBlockChange(location, Material.COBWEB.createBlockData());
        }

        cobwebsPresent = true;
    }

    public void removeCobwebs() {
        for (Location location : nettingArea) {
            if (invalidNetBlocksCheck(location)) continue;

            player.sendBlockChange(location, Material.AIR.createBlockData());
        }

        cobwebsPresent = false;
    }

    private boolean invalidNetBlocksCheck(Location location) {
        if (Math.abs((location.getX() - nettingLocation.getX())) > 1) return true;

        return (Math.abs((location.getZ() - nettingLocation.getZ())) > 1);
    }

    public void leavePreparationPhase() {
        preparationPhase = false;
        stopCount = count + 20;
    }

    public boolean isPlayer(Player checkPlayer) {
        return player == checkPlayer;
    }

    public boolean isFishCatchable() {
        return fishCatchable;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getNettingLocation() {
        return nettingLocation;
    }

    public boolean getPreparationPhase() {
        return preparationPhase;
    }

    public boolean getCobwebsPresent() {
        return cobwebsPresent;
    }
}