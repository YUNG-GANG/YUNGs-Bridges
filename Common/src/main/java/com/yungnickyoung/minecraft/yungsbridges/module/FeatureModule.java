package com.yungnickyoung.minecraft.yungsbridges.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsbridges.YungsBridgesCommon;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.BridgeFeature;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.MultipleAttemptSingleRandomFeature;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.config.BridgeFeatureConfig;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.config.MultipleAttemptSingleRandomFeatureConfig;
import net.minecraft.world.level.levelgen.feature.Feature;

@AutoRegister(YungsBridgesCommon.MOD_ID)
public class FeatureModule {
    @AutoRegister("bridge")
    public static Feature<BridgeFeatureConfig> BRIDGE = new BridgeFeature();

    @AutoRegister("multiple_attempt_single_random")
    public static Feature<MultipleAttemptSingleRandomFeatureConfig> MULTIPLE_ATTEMPT_SINGLE_RANDOM = new MultipleAttemptSingleRandomFeature();
}
