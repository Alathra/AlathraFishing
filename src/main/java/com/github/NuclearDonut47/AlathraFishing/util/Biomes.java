package com.github.NuclearDonut47.AlathraFishing.util;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.NuclearDonut47.AlathraFishing.config.Config;
import com.github.ipecter.rtu.biomelib.RTUBiomeLib;

@SuppressWarnings("unused")
public class Biomes {
	private static String packExtension;

	public Biomes(Config config) {
		packExtension = config.getPackExtension();
		init(config);
	}

	private final static ArrayList<String> biomes = new ArrayList<>();
	private final static ArrayList<String> oceanBiomes = new ArrayList<>();

	public void init(Config config) {
		// all biomes
		biomes.add(packExtension + "active_volcano_base");
		biomes.add(packExtension + "active_volcano_base_edge");
		biomes.add(packExtension + "active_volcano_pit");
		biomes.add(packExtension + "active_volcano_pit_edge");
		biomes.add(packExtension + "badlands_buttes");
		biomes.add(packExtension + "bamboo_jungle_hills");
		biomes.add(packExtension + "bamboo_jungle_mountains");
		biomes.add(packExtension + "bamboo_ponds");
		biomes.add(packExtension + "birch_forest_hills");
		biomes.add(packExtension + "caldera_volcano_base");
		biomes.add(packExtension + "caldera_volcano_base_edge");
		biomes.add(packExtension + "caldera_volcano_pit");
		biomes.add(packExtension + "caldera_volcano_pit_edge");
		biomes.add(packExtension + "cerros_de_mavecure");
		biomes.add(packExtension + "chaparral");
		biomes.add(packExtension + "cold_deep_ocean");
		biomes.add(packExtension + "cold_ocean");
		biomes.add(packExtension + "coral_ocean");
		biomes.add(packExtension + "dark_forest");
		biomes.add(packExtension + "deep_ocean");
		biomes.add(packExtension + "deep_ocean_trench");
		biomes.add(packExtension + "desert");
		biomes.add(packExtension + "dry_rocky_bumpy_mountains");
		biomes.add(packExtension + "dry_temperate_mountains");
		biomes.add(packExtension + "dry_temperate_white_mountains");
		biomes.add(packExtension + "dry_temperate_white_mountains_river");
		biomes.add(packExtension + "dry_wild_highlands");
		biomes.add(packExtension + "eucalyptus_forest");
		biomes.add(packExtension + "evergreen_forest");
		biomes.add(packExtension + "flowering_forest");
		biomes.add(packExtension + "flowering_forest_hills");
		biomes.add(packExtension + "forest");
		biomes.add(packExtension + "forest_hills");
		biomes.add(packExtension + "frozen_beach");
		biomes.add(packExtension + "frozen_deep_ocean");
		biomes.add(packExtension + "frozen_marsh");
		biomes.add(packExtension + "frozen_wetlands");
		biomes.add(packExtension + "highlands");
		biomes.add(packExtension + "ice_spikes");
		biomes.add(packExtension + "iceberg_ocean");
		biomes.add(packExtension + "jungle_hills");
		biomes.add(packExtension + "jungle_mountains");
		biomes.add(packExtension + "large_monsoon_mountains");
		biomes.add(packExtension + "mangrove_swamp");
		biomes.add(packExtension + "moorland");
		biomes.add(packExtension + "mountains");
		biomes.add(packExtension + "mountains_river");
		biomes.add(packExtension + "mushroom_hills");
		biomes.add(packExtension + "oak_savanna");
		biomes.add(packExtension + "ocean");
		biomes.add(packExtension + "palm_beach");
		biomes.add(packExtension + "palm_forest");
		biomes.add(packExtension + "plains");
		biomes.add(packExtension + "prairie");
		biomes.add(packExtension + "rainforest_hills");
		biomes.add(packExtension + "redwood_forest_hills");
		biomes.add(packExtension + "river");
		biomes.add(packExtension + "rocky_bumpy_mountains");
		biomes.add(packExtension + "rocky_sea_caves");
		biomes.add(packExtension + "sakura_mountains");
		biomes.add(packExtension + "salt_flats");
		biomes.add(packExtension + "shrublands");
		biomes.add(packExtension + "snowy_meadow");
		biomes.add(packExtension + "snowy_plains");
		biomes.add(packExtension + "snowy_terraced_mountains");
		biomes.add(packExtension + "snowy_terraced_mountains_river");
		biomes.add(packExtension + "steppe");
		biomes.add(packExtension + "subtropical_ocean");
		biomes.add(packExtension + "sunflower_plains");
		biomes.add(packExtension + "swamp");
		biomes.add(packExtension + "taiga");
		biomes.add(packExtension + "taiga_hills");
		biomes.add(packExtension + "temperate_mountains");
		biomes.add(packExtension + "temperate_mountains_river");
		biomes.add(packExtension + "tropical_deep_ocean");
		biomes.add(packExtension + "tropical_ocean");
		biomes.add(packExtension + "tundra_hills");
		biomes.add(packExtension + "wild_bumpy_mountains");
		biomes.add(packExtension + "wild_highlands");
		biomes.add(packExtension + "wooded_buttes");
		biomes.add(packExtension + "xeric_low_hills");
		biomes.add(packExtension + "xeric_mountains ");
		
		// ocean biomes
		oceanBiomes.add(packExtension + "cold_deep_ocean");
		oceanBiomes.add(packExtension + "cold_ocean");
		oceanBiomes.add(packExtension + "coral_ocean");
		oceanBiomes.add(packExtension + "deep_ocean");
		oceanBiomes.add(packExtension + "deep_ocean_trench");
		oceanBiomes.add(packExtension + "ocean");
		oceanBiomes.add(packExtension + "subtropical_ocean");
		oceanBiomes.add(packExtension + "tropical_ocean");

	}

	public static String getBiomeName(Location location) {
		return RTUBiomeLib.getInterface().getBiomeName(location);
	}

	public static String getBiomeName(Player player) {
		return RTUBiomeLib.getInterface().getBiomeName(player.getLocation());
	}
	
	public static boolean isSaltWater(Location location) {
		String biomeName = RTUBiomeLib.getInterface().getBiomeName(location);
        return oceanBiomes.contains(biomeName);
    }

	public static ArrayList<String> getBiomes() {
		return biomes;
	}

}
