package com.yungnickyoung.minecraft.yungsbridges.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class ConfigSpawnRatesFabric {
    @ConfigEntry.Gui.NoTooltip
    public int smallBridges = 2;

    @ConfigEntry.Gui.NoTooltip
    public int mediumBridges = 2;

    @ConfigEntry.Gui.NoTooltip
    public int largeBridges = 2;
}
