package com.github.NuclearDonut47.AlathraFishing.rewards.loot;

import com.github.NuclearDonut47.AlathraFishing.items.Fish;
import com.github.NuclearDonut47.AlathraFishing.rewards.LootTable;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class NetLoot extends LootTable {
    // Handles generating loot for net.
    // Any logic concerning logic for what can and cannot be caught for a net at any given time goes here.
    public NetLoot(ArrayList<Fish> fishList) {
        super(fishList);
    }

    @Override
    public ItemStack generateLoot() {
        return null;
    }
}
