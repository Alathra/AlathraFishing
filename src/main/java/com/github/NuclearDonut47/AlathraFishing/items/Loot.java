package com.github.NuclearDonut47.AlathraFishing.items;

import org.bukkit.inventory.ItemStack;

public abstract class Loot {
    private final int weight;

    protected Loot(int weightInstance) {
        if (weightInstance < 1) weightInstance = 0;

        weight = weightInstance;
    }

    public int getWeight() {
        return weight;
    }

    public abstract boolean isNetLoot();

    public abstract ItemStack getLootStack();
}
