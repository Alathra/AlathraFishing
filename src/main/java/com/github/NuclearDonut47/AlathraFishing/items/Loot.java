package com.github.NuclearDonut47.AlathraFishing.items;

import org.bukkit.inventory.ItemStack;

public abstract class Loot {
    protected final String identifier;

    protected Loot(String identifierInstance) {
        identifier = identifierInstance;
    }

    public String getIdentifier() {
        return identifier;
    }

    public abstract ItemStack generateLootStack();
}
