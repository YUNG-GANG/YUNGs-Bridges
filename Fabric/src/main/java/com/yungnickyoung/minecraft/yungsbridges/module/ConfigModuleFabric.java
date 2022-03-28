package com.yungnickyoung.minecraft.yungsbridges.module;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.yungsbridges.YungsBridgesCommon;
import com.yungnickyoung.minecraft.yungsbridges.config.YBConfigFabric;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.world.InteractionResult;

import java.util.ArrayList;
import java.util.List;

public class ConfigModuleFabric {
    public static void init() {
        AutoConfig.register(YBConfigFabric.class, Toml4jConfigSerializer::new);
        AutoConfig.getConfigHolder(YBConfigFabric.class).registerSaveListener(ConfigModuleFabric::bakeConfig);
        AutoConfig.getConfigHolder(YBConfigFabric.class).registerLoadListener(ConfigModuleFabric::bakeConfig);
        bakeConfig(AutoConfig.getConfigHolder(YBConfigFabric.class).get());
    }

    private static InteractionResult bakeConfig(ConfigHolder<YBConfigFabric> configHolder, YBConfigFabric configFabric) {
        bakeConfig(configFabric);
        return InteractionResult.SUCCESS;
    }

    private static void bakeConfig(YBConfigFabric configFabric) {
        YungsBridgesCommon.CONFIG.spawnRates.smallBridges = configFabric.spawnRates.smallBridges;
        YungsBridgesCommon.CONFIG.spawnRates.mediumBridges = configFabric.spawnRates.mediumBridges;
        YungsBridgesCommon.CONFIG.spawnRates.largeBridges = configFabric.spawnRates.largeBridges;
        YungsBridgesCommon.CONFIG.blacklistedBiomes = parseList(configFabric.blacklistedBiomes, "Blacklisted Biomes");
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
