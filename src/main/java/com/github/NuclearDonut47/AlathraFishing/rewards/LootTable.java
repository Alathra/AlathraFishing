package com.github.NuclearDonut47.AlathraFishing.rewards;

import com.github.NuclearDonut47.AlathraFishing.items.Loot;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public abstract class LootTable {
    protected final ArrayList<Loot> loot;
    protected final int weightTotal;
    protected final Random random = new Random();

    public LootTable(ArrayList<Loot> lootList) {
        loot = lootList;
        int weightSum = 0;

        for (Loot thisLoot: loot) {
            if (thisLoot.getWeight() != 0) {
                weightSum += thisLoot.getWeight();
                continue;
            }

            lootList.remove(thisLoot);
        }

        weightTotal = weightSum;
    }

    public abstract ItemStack generateLoot(String biome);
}