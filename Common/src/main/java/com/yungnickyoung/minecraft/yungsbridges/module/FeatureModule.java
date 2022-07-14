package com.yungnickyoung.minecraft.yungsbridges.module;

import com.yungnickyoung.minecraft.yungsbridges.YungsBridgesCommon;
import com.yungnickyoung.minecraft.yungsbridges.services.Services;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.BridgeFeature;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.config.BridgeFeatureConfig;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.MultipleAttemptSingleRandomFeature;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.config.MultipleAttemptSingleRandomFeatureConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;

public class FeatureModule {
    public static Feature<BridgeFeatureConfig> BRIDGE = new BridgeFeature();
    public static Feature<MultipleAttemptSingleRandomFeatureConfig> MULTIPLE_ATTEMPT_SINGLE_RANDOM = new MultipleAttemptSingleRandomFeature();

    public static void init() {
        Services.REGISTRY.registerFeature(new ResourceLocation(YungsBridgesCommon.MOD_ID, "bridge"), BRIDGE);
        Services.REGISTRY.registerFeature(new ResourceLocation(YungsBridgesCommon.MOD_ID, "multiple_attempt_single_random"), MULTIPLE_ATTEMPT_SINGLE_RANDOM);
    }
}
