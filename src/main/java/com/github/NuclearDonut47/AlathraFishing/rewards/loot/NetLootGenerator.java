package com.github.NuclearDonut47.AlathraFishing.rewards.loot;

import com.github.NuclearDonut47.AlathraFishing.rewards.LootTable;
import com.github.NuclearDonut47.AlathraFishing.items.Fish;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class NetLootGenerator extends LootTable {
    // Handles generating loot for net.
    // Any logic concerning logic for what can and cannot be caught for a net at any given time goes here.

    public NetLootGenerator(ArrayList<Fish> fishList) {
        super(fishList);
    }

    @Override
    public ItemStack generateLoot() {
        int fishIndex = random.nextInt(0, weightTotal);
        int weightTally = 0;

        for (Fish thisFish: fish) {
            weightTally += thisFish.getWeight();

            if (fishIndex < weightTally) return thisFish.getFishStack();
        }

        return null;
    }
}