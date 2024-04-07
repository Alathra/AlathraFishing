package com.github.NuclearDonut47.AlathraFishing.rewards;

import com.github.NuclearDonut47.AlathraFishing.rewards.loot.AbundantFishLootGenerator;
import com.github.NuclearDonut47.AlathraFishing.rewards.loot.NetLootGenerator;
import com.github.NuclearDonut47.AlathraFishing.items.CustomFish;
import com.github.NuclearDonut47.AlathraFishing.items.Fish;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class RewardGenerator {
    // Store All Loot Tables
    // All Loot Tables extend abstract loot table class
    // Choose loot table
    // generateReward() on chosen loot table
    // return itemstack

    // Common should be twice as common as uncommon,
    // Uncommon should be twice as common as rare,
    // Rare should be five times as common as super rare,
    // Super rare (I'll rename these later) should be five times as common as rarest loot
    // (Five-star system, by all means rip from brewery).

    private final NetLootGenerator netLootGenerator;
    private final AbundantFishLootGenerator abundantFishLootGenerator;

    public RewardGenerator(CustomFish customFish) {
        ArrayList<Fish> netLoot = new ArrayList<>();
        ArrayList<Fish> abundantFishLoot = new ArrayList<>();

        for (Fish fish : customFish.getFish()) {
            if (fish.isNetLoot()) netLoot.add(fish);

            abundantFishLoot.add(fish);
        }

        netLootGenerator = new NetLootGenerator(netLoot);
        abundantFishLootGenerator = new AbundantFishLootGenerator(abundantFishLoot);
    }

    public ItemStack giveReward(boolean netEvent) {
        if (netEvent) return netLootGenerator.generateLoot();

        return  abundantFishLootGenerator.generateLoot();
    }
}