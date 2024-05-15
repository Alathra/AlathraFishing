package com.github.NuclearDonut47.AlathraFishing.rewards;

import com.github.NuclearDonut47.AlathraFishing.config.Config;
import com.github.NuclearDonut47.AlathraFishing.items.generators.CustomMiscLoot;
import com.github.NuclearDonut47.AlathraFishing.items.Loot;
import com.github.NuclearDonut47.AlathraFishing.items.generators.CustomFish;
import com.github.NuclearDonut47.AlathraFishing.items.generators.CustomTools;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
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

    private final Plugin plugin;
    private final Config config;
    private final CustomTools tools;
    private final ArrayList<String> validFreshwaterBiomes = new ArrayList<>();
    private final ArrayList<String> validSaltwaterBiomes = new ArrayList<>();
    private final ArrayList<Loot> customLoot = new ArrayList<>();
    private final Random random = new Random();

    public RewardGenerator(Plugin plugin, Config config, CustomTools tools, CustomFish customFish,
                           CustomMiscLoot miscLoot) {
        this.plugin = plugin;
        this.config = config;
        this.tools = tools;
        customLoot.addAll(customFish.getFish());
        customLoot.addAll(miscLoot.getMiscLoot());

        String extension = config.getPackExtension();
        List<String> freshwaterStringList = config.getBiomesSection().getStringList("freshwater");
        List<String> saltwaterStringList = config.getBiomesSection().getStringList("saltwater");


        if (config.getDevMode()) {
            extension = "minecraft:";
            freshwaterStringList = config.getVanillaBiomesSection().getStringList("freshwater");
            saltwaterStringList = config.getVanillaBiomesSection().getStringList("saltwater");
        }

        for (String biomeString : freshwaterStringList) {
            validFreshwaterBiomes.add(extension + biomeString);
        }

        for (String biomeString : saltwaterStringList) {
            validSaltwaterBiomes.add(extension + biomeString);
        }
    }

    public ItemStack giveReward(boolean netEvent, String biome, boolean openWater) {
        String rarity = "abundant";
        String biomeType = "inland";

        if (netEvent) rarity = "net";

        if (validFreshwaterBiomes.contains(biome)) biomeType = "freshwater";

        if (validSaltwaterBiomes.contains(biome)) biomeType = "saltwater";

        if (biomeType.equals("inland") && !openWater) return null;

        if (config.getDistributionSection().getConfigurationSection(rarity).getConfigurationSection(biomeType) == null)
            return null;

        int weightTotal = 0;

        for (String identifier : config.getDistributionSection().getConfigurationSection(rarity)
                .getConfigurationSection(biomeType).getKeys(false)) {
            weightTotal += config.getDistributionSection().getConfigurationSection(rarity)
                    .getConfigurationSection(biomeType).getInt(identifier);
        }

        if (weightTotal == 0) return null;

        ArrayList<String> identifiers = new ArrayList<>(config.getDistributionSection().getConfigurationSection(rarity)
                .getConfigurationSection(biomeType).getKeys(false));
        int fishIndex = random.nextInt(0, weightTotal);
        int weightTally = 0;
        String lootIdentifier = null;

        for (String identifier : identifiers) {
            weightTally += config.getDistributionSection().getConfigurationSection(rarity)
                    .getConfigurationSection(biomeType).getInt(identifier);

            if (fishIndex < weightTally) {
                lootIdentifier = identifier;
                break;
            }
        }

        if (lootIdentifier == null) {
            plugin.getLogger().info("RewardGenerator identifier string not assigned. " +
                    "You should not see this message.");
            return null;
        }

        for (Loot loot : customLoot) {
            if (!loot.getIdentifier().equals(lootIdentifier)) continue;

            ItemStack reward = loot.generateLootStack();

            if (reward.getType() == Material.FISHING_ROD) reward = getAssociatedTool(random.nextInt(0, 1));

            if (reward.getType() == Material.POTION) {
                PotionMeta potionMeta = (PotionMeta) reward.getItemMeta();

                potionMeta.setBasePotionType(PotionType.WATER);

                reward.setItemMeta(potionMeta);
            }

            if (reward.getType() == Material.INK_SAC) reward.setAmount(10);

            return reward;
        }

        plugin.getLogger().info("Tried to give reward for loot in the distribution table but not a loot table.");

        return null;
    }

    private ItemStack getAssociatedTool(int index) {
        if (index == 0) return tools.getTool("net");

        return tools.getTool("fishing_rod");
    }
}