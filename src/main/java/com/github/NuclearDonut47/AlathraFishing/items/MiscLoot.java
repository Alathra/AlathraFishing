package com.github.NuclearDonut47.AlathraFishing.items;

import org.bukkit.inventory.ItemStack;

public class MiscLoot extends Loot {
    private final boolean netLoot;

    public MiscLoot(int weightInstance, boolean netLootInstance) {
        super(weightInstance);
        netLoot = netLootInstance;
    }

    @Override
    public boolean isNetLoot(){
        return netLoot;
    }

    @Override
    public ItemStack getLootStack() {
        return null;
    }
}
