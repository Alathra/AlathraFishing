package com.github.NuclearDonut47.AlathraFishing.fish;

import com.github.NuclearDonut47.AlathraFishing.items.Fish;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public abstract class LootTable {
    protected final ArrayList<Fish> fish;
    protected final int weightTotal;
    protected final Random random = new Random();

    public LootTable(ArrayList<Fish> fishList) {
        fish = fishList;
        int weightSum = 0;

        for (Fish thisFish: fish) {
            weightSum += thisFish.getWeight();
        }

        weightTotal = weightSum;
    }

    public abstract ItemStack generateLoot();
}