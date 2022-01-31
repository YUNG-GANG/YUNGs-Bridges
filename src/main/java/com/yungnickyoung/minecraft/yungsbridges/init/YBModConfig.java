package com.yungnickyoung.minecraft.yungsbridges.init;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.yungsbridges.YungsBridges;
import com.yungnickyoung.minecraft.yungsbridges.config.YBConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;

public class YBModConfig {
    public static void init() {
        // Register mod config with Forge
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, YBConfig.SPEC, "yungsbridges-forge-1_18.toml");
        FMLJavaModLoadingContext.get().getModEventBus().addListener(YBModConfig::configChanged);
    }

    /**
     * Parses the whitelisted dimensions & blacklisted biomes strings and updates the stored values.
     */
    public static void configChanged(ModConfigEvent event) {
        ModConfig config = event.getConfig();

        if (config.getSpec() == YBConfig.SPEC) {
            // Biome blacklisting
            String rawStringList = YBConfig.blacklistedBiomes.get();
            int strLen = rawStringList.length();

            // Validate the string's format
            if (strLen < 2 || rawStringList.charAt(0) != '[' || rawStringList.charAt(strLen - 1) != ']') {
                YungsBridges.LOGGER.error("INVALID VALUE FOR SETTING 'Blacklisted Biomes'. Using default instead...");
                YungsBridges.blacklistedBiomes = new ArrayList<>();
                return;
            }

            // Parse string to list
            YungsBridges.blacklistedBiomes = Lists.newArrayList(rawStringList.substring(1, strLen - 1).split(",\\s*"));
        }
    }
}
