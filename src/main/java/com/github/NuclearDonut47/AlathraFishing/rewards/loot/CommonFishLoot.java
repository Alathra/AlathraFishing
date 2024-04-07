package com.github.NuclearDonut47.AlathraFishing.rewards.loot;

import com.github.NuclearDonut47.AlathraFishing.items.Fish;
import com.github.NuclearDonut47.AlathraFishing.rewards.LootTable;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CommonFishLoot extends LootTable {
    // Handles generating loot for common catches via angling.
    // Any logic concerning logic for what can and cannot be caught as a commmon angling reward, when, goes here.
    public CommonFishLoot(ArrayList<Fish> fishList) {
        super(fishList);
    }

    @Override
    public ItemStack generateLoot() {
        return null;
    }
}
