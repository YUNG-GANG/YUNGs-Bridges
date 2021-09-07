package com.yungnickyoung.minecraft.yungsbridges.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigSpawnRates {
    public final ForgeConfigSpec.ConfigValue<Integer> smallBridges;
    public final ForgeConfigSpec.ConfigValue<Integer> mediumBridges;
    public final ForgeConfigSpec.ConfigValue<Integer> largeBridges;

    public ConfigSpawnRates(final ForgeConfigSpec.Builder BUILDER) {
        BUILDER
            .comment(
                "##########################################################################################################\n" +
                " Spawn Rates.\n" +
                " Note that larger bridges require more space to spawn, and are therefore more rare in vanilla rivers.\n" +
                " Because of this, larger bridges have higher spawn rates.\n" +
                " You may consider changing that if you are using non-vanilla terrain gen.\n" +
                " \n" +
                " The spawn rate of each bridge is 1/N per chunk, where N is the spawn rate value.\n" +
                " E.g. if you set N = 1 for small bridges, then an ATTEMPT will be made to spawn a small bridge every chunk.\n" +
                " Note that it is ONLY an attempt - the bridge will only spawn if the chunk contains fitting river terrain.\n" +
                "##########################################################################################################")
            .push("Spawn Rates");

        smallBridges = BUILDER
            .comment(
                " The spawn rate for small bridges.\n" +
                " Default: 15")
            .worldRestart()
            .defineInRange("Small Bridge Spawn Rate", 15, 1, 100);

        mediumBridges = BUILDER
            .comment(
                " The spawn rate for medium bridges.\n" +
                " Default: 3")
            .worldRestart()
            .defineInRange("Medium Bridge Spawn Rate", 3, 1, 100);

        largeBridges = BUILDER
            .comment(
                " The spawn rate for large bridges.\n" +
                " Default: 1")
            .worldRestart()
            .defineInRange("Large Bridge Spawn Rate", 1, 1, 100);

        BUILDER.pop();
    }
}
