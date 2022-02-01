package com.yungnickyoung.minecraft.yungsbridges;

import com.yungnickyoung.minecraft.yungsbridges.config.YBConfig;
import com.yungnickyoung.minecraft.yungsbridges.init.YBModConfig;
import com.yungnickyoung.minecraft.yungsbridges.init.YBModFeatures;
import com.yungnickyoung.minecraft.yungsbridges.init.YBModPlacements;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class YungsBridges implements ModInitializer {
    public static final String MOD_ID = "yungsbridges";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    /** YUNG's Bridges config. Uses AutoConfig. **/
    public static YBConfig CONFIG;

    /**
     * List of blacklisted biomes.
     * Will be reinitialized later w/ values from config.
     */
    public static List<String> blacklistedBiomes = new ArrayList<>();

    public void onInitialize() {
        YBModConfig.init();
        YBModPlacements.init();
        YBModFeatures.init();
    }
}
