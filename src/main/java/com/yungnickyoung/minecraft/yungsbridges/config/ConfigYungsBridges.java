package com.yungnickyoung.minecraft.yungsbridges.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class ConfigYungsBridges {
    @ConfigEntry.Category("Spawn Rates")
    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 4)
    public ConfigSpawnRates spawnRates = new ConfigSpawnRates();

    @ConfigEntry.Gui.Tooltip(count = 5)
    public String blacklistedBiomes = "[]";
}
