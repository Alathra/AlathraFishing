package com.github.NuclearDonut47.AlathraFishing.util;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.NuclearDonut47.AlathraFishing.config.Config;
import com.github.ipecter.rtu.biomelib.RTUBiomeLib;

public class BiomeUtil {

	private static ArrayList<String> biomes = new ArrayList<String>();
	private static ArrayList<String> oceanBiomes = new ArrayList<String>();

	public static void init() {
		// all biomes
		biomes.add(Config.packExtension + "active_volcano_base");
		biomes.add(Config.packExtension + "active_volcano_base_edge");
		biomes.add(Config.packExtension + "active_volcano_pit");
		biomes.add(Config.packExtension + "active_volcano_pit_edge");
		biomes.add(Config.packExtension + "badlands_buttes");
		biomes.add(Config.packExtension + "bamboo_jungle_hills");
		biomes.add(Config.packExtension + "bamboo_jungle_mountains");
		biomes.add(Config.packExtension + "bamboo_ponds");
		biomes.add(Config.packExtension + "birch_forest_hills");
		biomes.add(Config.packExtension + "caldera_volcano_base");
		biomes.add(Config.packExtension + "caldera_volcano_base_edge");
		biomes.add(Config.packExtension + "caldera_volcano_pit");
		biomes.add(Config.packExtension + "caldera_volcano_pit_edge");
		biomes.add(Config.packExtension + "cerros_de_mavecure");
		biomes.add(Config.packExtension + "chaparral");
		biomes.add(Config.packExtension + "cold_deep_ocean");
		biomes.add(Config.packExtension + "cold_ocean");
		biomes.add(Config.packExtension + "coral_ocean");
		biomes.add(Config.packExtension + "dark_forest");
		biomes.add(Config.packExtension + "deep_ocean");
		biomes.add(Config.packExtension + "deep_ocean_trench");
		biomes.add(Config.packExtension + "desert");
		biomes.add(Config.packExtension + "dry_rocky_bumpy_mountains");
		biomes.add(Config.packExtension + "dry_temperate_mountains");
		biomes.add(Config.packExtension + "dry_temperate_white_mountains");
		biomes.add(Config.packExtension + "dry_temperate_white_mountains_river");
		biomes.add(Config.packExtension + "dry_wild_highlands");
		biomes.add(Config.packExtension + "eucalyptus_forest");
		biomes.add(Config.packExtension + "evergreen_forest");
		biomes.add(Config.packExtension + "flowering_forest");
		biomes.add(Config.packExtension + "flowering_forest_hills");
		biomes.add(Config.packExtension + "forest");
		biomes.add(Config.packExtension + "forest_hills");
		biomes.add(Config.packExtension + "frozen_beach");
		biomes.add(Config.packExtension + "frozen_deep_ocean");
		biomes.add(Config.packExtension + "frozen_marsh");
		biomes.add(Config.packExtension + "frozen_wetlands");
		biomes.add(Config.packExtension + "highlands");
		biomes.add(Config.packExtension + "ice_spikes");
		biomes.add(Config.packExtension + "iceberg_ocean");
		biomes.add(Config.packExtension + "jungle_hills");
		biomes.add(Config.packExtension + "jungle_mountains");
		biomes.add(Config.packExtension + "large_monsoon_mountains");
		biomes.add(Config.packExtension + "mangrove_swamp");
		biomes.add(Config.packExtension + "moorland");
		biomes.add(Config.packExtension + "mountains");
		biomes.add(Config.packExtension + "mountains_river");
		biomes.add(Config.packExtension + "mushroom_hills");
		biomes.add(Config.packExtension + "oak_savanna");
		biomes.add(Config.packExtension + "ocean");
		biomes.add(Config.packExtension + "palm_beach");
		biomes.add(Config.packExtension + "palm_forest");
		biomes.add(Config.packExtension + "plains");
		biomes.add(Config.packExtension + "prairie");
		biomes.add(Config.packExtension + "rainforest_hills");
		biomes.add(Config.packExtension + "redwood_forest_hills");
		biomes.add(Config.packExtension + "river");
		biomes.add(Config.packExtension + "rocky_bumpy_mountains");
		biomes.add(Config.packExtension + "rocky_sea_caves");
		biomes.add(Config.packExtension + "sakura_mountains");
		biomes.add(Config.packExtension + "salt_flats");
		biomes.add(Config.packExtension + "shrublands");
		biomes.add(Config.packExtension + "snowy_meadow");
		biomes.add(Config.packExtension + "snowy_plains");
		biomes.add(Config.packExtension + "snowy_terraced_mountains");
		biomes.add(Config.packExtension + "snowy_terraced_mountains_river");
		biomes.add(Config.packExtension + "steppe");
		biomes.add(Config.packExtension + "subtropical_ocean");
		biomes.add(Config.packExtension + "sunflower_plains");
		biomes.add(Config.packExtension + "swamp");
		biomes.add(Config.packExtension + "taiga");
		biomes.add(Config.packExtension + "taiga_hills");
		biomes.add(Config.packExtension + "temperate_mountains");
		biomes.add(Config.packExtension + "temperate_mountains_river");
		biomes.add(Config.packExtension + "tropical_deep_ocean");
		biomes.add(Config.packExtension + "tropical_ocean");
		biomes.add(Config.packExtension + "tundra_hills");
		biomes.add(Config.packExtension + "wild_bumpy_mountains");
		biomes.add(Config.packExtension + "wild_highlands");
		biomes.add(Config.packExtension + "wooded_buttes");
		biomes.add(Config.packExtension + "xeric_low_hills");
		biomes.add(Config.packExtension + "xeric_mountains ");
		
		// ocean biomes
		oceanBiomes.add(Config.packExtension + "cold_deep_ocean");
		oceanBiomes.add(Config.packExtension + "cold_ocean");
		oceanBiomes.add(Config.packExtension + "coral_ocean");
		oceanBiomes.add(Config.packExtension + "deep_ocean");
		oceanBiomes.add(Config.packExtension + "deep_ocean_trench");
		oceanBiomes.add(Config.packExtension + "ocean");
		oceanBiomes.add(Config.packExtension + "subtropical_ocean");
		oceanBiomes.add(Config.packExtension + "tropical_ocean");

	}

	public static String getBiomeName(Location location) {
		return RTUBiomeLib.getInterface().getBiomeName(location);
	}

	public static String getBiomeName(Player player) {
		return RTUBiomeLib.getInterface().getBiomeName(player.getLocation());
	}
	
	public static boolean isSaltWater(Location location) {
		String biomeName = RTUBiomeLib.getInterface().getBiomeName(location);
		if (oceanBiomes.contains(biomeName)) {
			return true;
		}
		return false;
	}

	public static ArrayList<String> getBiomes() {
		return biomes;
	}

}
