package com.yungnickyoung.minecraft.yungsbridges;

import com.yungnickyoung.minecraft.yungsbridges.init.YBModConfig;
import com.yungnickyoung.minecraft.yungsbridges.init.YBModFeatures;
import com.yungnickyoung.minecraft.yungsbridges.init.YBModPlacements;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod(YungsBridges.MOD_ID)
public class YungsBridges {
    public static final String MOD_ID = "yungsbridges";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    /**
     * List of blacklisted biomes.
     * Will be reinitialized later w/ values from config.
     */
    public static List<String> blacklistedBiomes = new ArrayList<>();

    public YungsBridges() {
        init();
    }

    private void init() {
        YBModConfig.init();
        YBModPlacements.init();
        YBModFeatures.init();
    }
}
