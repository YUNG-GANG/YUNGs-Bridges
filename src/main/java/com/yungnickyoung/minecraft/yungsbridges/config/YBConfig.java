package com.yungnickyoung.minecraft.yungsbridges.config;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.yungsbridges.YungsBridges;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;

@Config(name="yungsbridges-fabric-1_18")
public class YBConfig implements ConfigData {
    @ConfigEntry.Category("YUNG's Bridges")
    @ConfigEntry.Gui.TransitiveObject
    public ConfigYungsBridges yungsBridges = new ConfigYungsBridges();

    /**
     * Parses the blacklisted biomes string and updates the stored value.
     */
    @Override
    public void validatePostLoad() {
        // Biome blacklisting
        int strLen = yungsBridges.blacklistedBiomes.length();

        // Validate the string's format
        if (strLen < 2 || yungsBridges.blacklistedBiomes.charAt(0) != '[' || yungsBridges.blacklistedBiomes.charAt(strLen - 1) != ']') {
            YungsBridges.LOGGER.error("INVALID VALUE FOR SETTING 'Blacklisted Biomes'. Using default instead...");
            YungsBridges.blacklistedBiomes = new ArrayList<>();
            return;
        }

        // Parse string to list
        YungsBridges.blacklistedBiomes = Lists.newArrayList(yungsBridges.blacklistedBiomes.substring(1, strLen - 1).split(",\\s*"));
    }
}
