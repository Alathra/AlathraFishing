package com.github.NuclearDonut47.AlathraFishing.rewards.loot;

import com.github.NuclearDonut47.AlathraFishing.items.Loot;
import com.github.NuclearDonut47.AlathraFishing.rewards.LootTable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class AbundantFishLootGenerator extends LootTable {
    private static final ArrayList<String> validBiomes = new ArrayList<>();

    public AbundantFishLootGenerator(ArrayList<Loot> lootList, ConfigurationSection biomesSection) {
        super(lootList);

        validBiomes.addAll(biomesSection.getStringList("net_biomes"));
    }

    @Override
    public ItemStack generateLoot(String biome) {
        int fishIndex = random.nextInt(0, weightTotal);
        int weightTally = 0;

        for (Loot thisLoot: loot) {
            weightTally += thisLoot.getWeight();

            if (fishIndex < weightTally) return thisLoot.getLootStack();
        }

        return null;
    }
}
