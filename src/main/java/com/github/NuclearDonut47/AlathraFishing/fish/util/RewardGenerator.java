package com.github.NuclearDonut47.AlathraFishing.fish.util;

import com.github.NuclearDonut47.AlathraFishing.fish.loot.CommonFishLoot;
import com.github.NuclearDonut47.AlathraFishing.fish.loot.NetLoot;
import com.github.NuclearDonut47.AlathraFishing.fish.loot.UncommonFishLoot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

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

    private ArrayList<LootTable> lootTables;
    private final Random random;

    public RewardGenerator() {
        lootTables.add(new NetLoot());
        lootTables.add(new CommonFishLoot());
        lootTables.add(new UncommonFishLoot());

        random = new Random();
    }

    public ItemStack giveReward() {
        int num = random.nextInt(3);

        return lootTables.get(num).generateLoot();
    }
}