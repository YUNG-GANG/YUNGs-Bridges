package com.yungnickyoung.minecraft.yungsbridges.module;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.yungsbridges.YungsBridgesCommon;
import com.yungnickyoung.minecraft.yungsbridges.config.YBConfigForge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;
import java.util.List;

public class ConfigModuleForge {
    public static void init() {
        // Register mod config with Forge
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, YBConfigForge.SPEC, "YungsBridges-forge-1_18.toml");
        MinecraftForge.EVENT_BUS.addListener(ConfigModuleForge::onWorldLoad);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ConfigModuleForge::onConfigChange);
    }

    private static void onWorldLoad(WorldEvent.Load event) {
        bakeConfig();
    }

    private static void onConfigChange(ModConfigEvent event) {
        if (event.getConfig().getSpec() == YBConfigForge.SPEC) {
            bakeConfig();
        }
    }

    private static void bakeConfig() {
        YungsBridgesCommon.CONFIG.spawnRates.smallBridges = YBConfigForge.spawnRates.smallBridges.get();
        YungsBridgesCommon.CONFIG.spawnRates.mediumBridges = YBConfigForge.spawnRates.mediumBridges.get();
        YungsBridgesCommon.CONFIG.spawnRates.largeBridges = YBConfigForge.spawnRates.largeBridges.get();
        YungsBridgesCommon.CONFIG.blacklistedBiomes = parseList(YBConfigForge.blacklistedBiomes.get(), "Blacklisted Biomes");
    }

    private static List<String> parseList(String rawStringOfList, String settingName) {
        int strLen = rawStringOfList.length();

        // Validate the string's format
        if (strLen < 2 || rawStringOfList.charAt(0) != '[' || rawStringOfList.charAt(strLen - 1) != ']') {
            YungsBridgesCommon.LOGGER.error("INVALID VALUE FOR SETTING '" + settingName + "'. Using empty list instead...");
            return new ArrayList<>();
        }

        return Lists.newArrayList(rawStringOfList.substring(1, strLen - 1).split(",\\s*"));
    }
}
