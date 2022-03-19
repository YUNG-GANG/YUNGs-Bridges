package com.yungnickyoung.minecraft.yungsbridges.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class YBConfigForge {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<String> blacklistedBiomes;

    public static final ConfigSpawnRatesForge spawnRates;

    static {
        BUILDER.push("YUNG's Bridges");

        blacklistedBiomes = BUILDER
            .comment(
                " List of biomes that will NOT have bridges.\n" +
                " Only river biomes are considered for bridge placement, so you need not specify non-river biomes here.\n" +
                " List must be comma-separated values enclosed in square brackets.\n" +
                " Entries must have the mod namespace included.\n" +
                " For example: \"[minecraft:river, minecraft:frozen_river]\"\n" +
                " Default: \"[]\"")
            .worldRestart()
            .define("Blacklisted Biomes", "[]");

        spawnRates = new ConfigSpawnRatesForge(BUILDER);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
