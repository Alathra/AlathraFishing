package com.github.NuclearDonut47.AlathraFishing.util;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.NuclearDonut47.AlathraFishing.config.Config;
import com.github.ipecter.rtu.biomelib.RTUBiomeLib;

@SuppressWarnings("unused")
public class BiomeUtil {

	private final static ArrayList<String> biomes = new ArrayList<>();
	private final static ArrayList<String> oceanBiomes = new ArrayList<>();

	public static void init(Config config) {
		// all biomes
		biomes.add(config.getPackExtension() + "active_volcano_base");
		biomes.add(config.getPackExtension() + "active_volcano_base_edge");
		biomes.add(config.getPackExtension() + "active_volcano_pit");
		biomes.add(config.getPackExtension() + "active_volcano_pit_edge");
		biomes.add(config.getPackExtension() + "badlands_buttes");
		biomes.add(config.getPackExtension() + "bamboo_jungle_hills");
		biomes.add(config.getPackExtension() + "bamboo_jungle_mountains");
		biomes.add(config.getPackExtension() + "bamboo_ponds");
		biomes.add(config.getPackExtension() + "birch_forest_hills");
		biomes.add(config.getPackExtension() + "caldera_volcano_base");
		biomes.add(config.getPackExtension() + "caldera_volcano_base_edge");
		biomes.add(config.getPackExtension() + "caldera_volcano_pit");
		biomes.add(config.getPackExtension() + "caldera_volcano_pit_edge");
		biomes.add(config.getPackExtension() + "cerros_de_mavecure");
		biomes.add(config.getPackExtension() + "chaparral");
		biomes.add(config.getPackExtension() + "cold_deep_ocean");
		biomes.add(config.getPackExtension() + "cold_ocean");
		biomes.add(config.getPackExtension() + "coral_ocean");
		biomes.add(config.getPackExtension() + "dark_forest");
		biomes.add(config.getPackExtension() + "deep_ocean");
		biomes.add(config.getPackExtension() + "deep_ocean_trench");
		biomes.add(config.getPackExtension() + "desert");
		biomes.add(config.getPackExtension() + "dry_rocky_bumpy_mountains");
		biomes.add(config.getPackExtension() + "dry_temperate_mountains");
		biomes.add(config.getPackExtension() + "dry_temperate_white_mountains");
		biomes.add(config.getPackExtension() + "dry_temperate_white_mountains_river");
		biomes.add(config.getPackExtension() + "dry_wild_highlands");
		biomes.add(config.getPackExtension() + "eucalyptus_forest");
		biomes.add(config.getPackExtension() + "evergreen_forest");
		biomes.add(config.getPackExtension() + "flowering_forest");
		biomes.add(config.getPackExtension() + "flowering_forest_hills");
		biomes.add(config.getPackExtension() + "forest");
		biomes.add(config.getPackExtension() + "forest_hills");
		biomes.add(config.getPackExtension() + "frozen_beach");
		biomes.add(config.getPackExtension() + "frozen_deep_ocean");
		biomes.add(config.getPackExtension() + "frozen_marsh");
		biomes.add(config.getPackExtension() + "frozen_wetlands");
		biomes.add(config.getPackExtension() + "highlands");
		biomes.add(config.getPackExtension() + "ice_spikes");
		biomes.add(config.getPackExtension() + "iceberg_ocean");
		biomes.add(config.getPackExtension() + "jungle_hills");
		biomes.add(config.getPackExtension() + "jungle_mountains");
		biomes.add(config.getPackExtension() + "large_monsoon_mountains");
		biomes.add(config.getPackExtension() + "mangrove_swamp");
		biomes.add(config.getPackExtension() + "moorland");
		biomes.add(config.getPackExtension() + "mountains");
		biomes.add(config.getPackExtension() + "mountains_river");
		biomes.add(config.getPackExtension() + "mushroom_hills");
		biomes.add(config.getPackExtension() + "oak_savanna");
		biomes.add(config.getPackExtension() + "ocean");
		biomes.add(config.getPackExtension() + "palm_beach");
		biomes.add(config.getPackExtension() + "palm_forest");
		biomes.add(config.getPackExtension() + "plains");
		biomes.add(config.getPackExtension() + "prairie");
		biomes.add(config.getPackExtension() + "rainforest_hills");
		biomes.add(config.getPackExtension() + "redwood_forest_hills");
		biomes.add(config.getPackExtension() + "river");
		biomes.add(config.getPackExtension() + "rocky_bumpy_mountains");
		biomes.add(config.getPackExtension() + "rocky_sea_caves");
		biomes.add(config.getPackExtension() + "sakura_mountains");
		biomes.add(config.getPackExtension() + "salt_flats");
		biomes.add(config.getPackExtension() + "shrublands");
		biomes.add(config.getPackExtension() + "snowy_meadow");
		biomes.add(config.getPackExtension() + "snowy_plains");
		biomes.add(config.getPackExtension() + "snowy_terraced_mountains");
		biomes.add(config.getPackExtension() + "snowy_terraced_mountains_river");
		biomes.add(config.getPackExtension() + "steppe");
		biomes.add(config.getPackExtension() + "subtropical_ocean");
		biomes.add(config.getPackExtension() + "sunflower_plains");
		biomes.add(config.getPackExtension() + "swamp");
		biomes.add(config.getPackExtension() + "taiga");
		biomes.add(config.getPackExtension() + "taiga_hills");
		biomes.add(config.getPackExtension() + "temperate_mountains");
		biomes.add(config.getPackExtension() + "temperate_mountains_river");
		biomes.add(config.getPackExtension() + "tropical_deep_ocean");
		biomes.add(config.getPackExtension() + "tropical_ocean");
		biomes.add(config.getPackExtension() + "tundra_hills");
		biomes.add(config.getPackExtension() + "wild_bumpy_mountains");
		biomes.add(config.getPackExtension() + "wild_highlands");
		biomes.add(config.getPackExtension() + "wooded_buttes");
		biomes.add(config.getPackExtension() + "xeric_low_hills");
		biomes.add(config.getPackExtension() + "xeric_mountains ");
		
		// ocean biomes
		oceanBiomes.add(config.getPackExtension() + "cold_deep_ocean");
		oceanBiomes.add(config.getPackExtension() + "cold_ocean");
		oceanBiomes.add(config.getPackExtension() + "coral_ocean");
		oceanBiomes.add(config.getPackExtension() + "deep_ocean");
		oceanBiomes.add(config.getPackExtension() + "deep_ocean_trench");
		oceanBiomes.add(config.getPackExtension() + "ocean");
		oceanBiomes.add(config.getPackExtension() + "subtropical_ocean");
		oceanBiomes.add(config.getPackExtension() + "tropical_ocean");

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
