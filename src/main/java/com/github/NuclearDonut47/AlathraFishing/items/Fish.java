package com.github.NuclearDonut47.AlathraFishing.items;

import com.github.milkdrinkers.colorparser.ColorParser;
import org.apache.commons.math3.special.Gamma;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fish extends Loot {
    private static Plugin plugin;
    private final String name;
    private final Material item;
    private final String lore;
    private final int minBound;
    private final ArrayList<Double> lengthDist = new ArrayList<>();
    private final int model;
    private final String rarity;
    private final Random random = new Random();

    public Fish(Plugin pluginInstance, String fishIdentifier, ConfigurationSection fishConfig,
                int modelInstance, double fishMinLength, int stretch) {
        super(fishIdentifier);
        plugin = pluginInstance;
        name = fishConfig.getString("name");
        lore = fishConfig.getString("lore");
        model = modelInstance;


        rarity = fishConfig.getString("rarity");

        if (Material.getMaterial(fishConfig.getString("item")) != null) {
            item = Material.getMaterial(fishConfig.getString("item"));
        } else {
            item = Material.SALMON;
        }

        int minLength = evaluateMinLength((int) Math.round(fishMinLength * 10));
        int maxLength = evaluateMaxLength((int) Math.round(fishConfig.getDouble("max_length") * 10), minLength);
        int commonLength = evaluateCommonLength((int) Math.round(fishConfig.getDouble("common_length") * 10),
                maxLength, minLength);
        minBound = evaluateMinBound(commonLength, maxLength, minLength);

        if (stretch <= 0) stretch = 0;

        double probSum = 0;

        for (int a = minBound; a <= maxLength; a++) {
            double prob = alternativeBinomial(stretch, a, commonLength, maxLength);

            lengthDist.add(prob);
            probSum += prob;
        }

        for (int a = 0; a < lengthDist.size(); a++) {
            lengthDist.set(a, lengthDist.get(a) / probSum);
        }
    }

    private int evaluateMinLength(int minLength) {
        if (minLength <= 0) return -1;

        return minLength;
    }

    private int evaluateMaxLength(int maxLength, int minLength) {
        if (maxLength <= 0) return -1;

        return Math.max(maxLength, minLength);

    }

    private int evaluateCommonLength(int commonLength, int maxLength, int minLength) {
        int commonLengthCandidate = maxLength / 2;

        if (commonLengthCandidate < minLength) commonLengthCandidate = minLength;

        if (commonLengthCandidate <= 0) commonLengthCandidate = -1;

        if (commonLength <= 0 || commonLength < minLength || commonLength > maxLength) return commonLengthCandidate;

        return commonLength;
    }

    private int evaluateMinBound(int commonLength, int maxLength, int minLength) {
        return Math.max(minLength, (2 * commonLength) - maxLength);

    }

    private double alternativeBinomial(int stretch, int loc, int center, int max) {
        return Gamma.gamma(stretch + 1) / (Gamma.gamma(((double) stretch / 2) -
                (((((double) stretch / 2) + 1) / (max - center + 1)) * (loc - center)) + 1) *
                Gamma.gamma(((double) stretch / 2) +
                (((((double) stretch / 2) + 1) / (max - center + 1)) * (loc - center)) + 1));
    }

    public ItemStack generateLootStack() {
        ItemStack fish = new ItemStack(item);

        ItemMeta fishMeta = fish.getItemMeta();

        fishMeta.displayName(ColorParser.of(name).build());

        fishMeta.lore(List.of(
                ColorParser.of(lore).build(), ColorParser.of(generateLength() + " cm").build(),
                ColorParser.of("Abundant Fish").build()));

        fishMeta.setCustomModelData(model);

        NamespacedKey idKey = new NamespacedKey(plugin, "identifier");
        NamespacedKey rarityKey = new NamespacedKey(plugin, "rarity");

        fishMeta.getPersistentDataContainer().set(idKey, PersistentDataType.STRING, identifier);
        fishMeta.getPersistentDataContainer().set(rarityKey, PersistentDataType.STRING, rarity);

        fish.setItemMeta(fishMeta);

        return fish;
    }

    public double generateLength() {
        double distLoc = random.nextDouble();
        double tally = 0;
        int loc = 0;

        for (int a = 0; a < lengthDist.size(); a++) {
            tally += lengthDist.get(a);

            if (distLoc <= tally) {
                loc = a;

                break;
            }
        }

        if (loc != (lengthDist.size() - 1)) {
            return (double) ((int) ((((double) minBound / 10) + (0.1 * loc)) * 10)) / 10;
        }

        while (true) {
            if (!random.nextBoolean()) {
                return ((double) minBound / 10) + (0.1 * loc);
            }

            loc++;
        }
    }
}