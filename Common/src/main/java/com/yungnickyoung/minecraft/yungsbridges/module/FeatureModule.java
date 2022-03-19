package com.yungnickyoung.minecraft.yungsbridges.module;

import com.yungnickyoung.minecraft.yungsbridges.world.feature.BridgeFeatureConfig;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.MultipleAttemptSingleRandomFeatureConfig;
import net.minecraft.world.level.levelgen.feature.Feature;

public class FeatureModule {
    public static Feature<BridgeFeatureConfig> BRIDGE;
    public static Feature<MultipleAttemptSingleRandomFeatureConfig> MULTIPLE_ATTEMPT_SINGLE_RANDOM;
}
