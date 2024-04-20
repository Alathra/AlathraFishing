package com.github.NuclearDonut47.AlathraFishing.rewards;

import com.github.NuclearDonut47.AlathraFishing.config.Config;
import com.github.NuclearDonut47.AlathraFishing.items.CustomMiscLoot;
import com.github.NuclearDonut47.AlathraFishing.items.Loot;
import com.github.NuclearDonut47.AlathraFishing.rewards.loot.AbundantFishLootGenerator;
import com.github.NuclearDonut47.AlathraFishing.rewards.loot.NetLootGenerator;
import com.github.NuclearDonut47.AlathraFishing.items.CustomFish;
import org.bukkit.configuration.ConfigurationSection;
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

    public RewardGenerator(CustomFish customFish, CustomMiscLoot miscLoot, Config config) {
        ArrayList<Loot> netLoot = new ArrayList<>();
        ArrayList<Loot> abundantFishLoot = new ArrayList<>();

        ArrayList<Loot> customLoot = new ArrayList<>();
        customLoot.addAll(customFish.getFish());
        customLoot.addAll(miscLoot.getMiscLoot());

        for (Loot loot : customLoot) {
            if (loot.isNetLoot()) netLoot.add(loot);

            abundantFishLoot.add(loot);
        }

        netLootGenerator = new NetLootGenerator(netLoot, config.getBiomesSection());
        abundantFishLootGenerator = new AbundantFishLootGenerator(abundantFishLoot, config.getBiomesSection());
    }

    public ItemStack giveReward(boolean netEvent, String biome) {
        if (netEvent) return netLootGenerator.generateLoot(biome);

        return  abundantFishLootGenerator.generateLoot(biome);
    }
}