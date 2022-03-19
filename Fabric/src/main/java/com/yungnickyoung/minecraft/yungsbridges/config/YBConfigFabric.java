package com.yungnickyoung.minecraft.yungsbridges.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name="yungsbridges-fabric-1_18")
public class YBConfigFabric implements ConfigData {
    @ConfigEntry.Gui.Tooltip(count = 5)
    public String blacklistedBiomes = "[]";

    @ConfigEntry.Category("Spawn Rates")
    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 5)
    public ConfigSpawnRatesFabric spawnRates = new ConfigSpawnRatesFabric();
}
