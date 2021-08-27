package com.yungnickyoung.minecraft.yungsbridges.init;

import com.yungnickyoung.minecraft.yungsbridges.YungsBridges;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.BridgeFeatureConfig;
import com.yungnickyoung.minecraft.yungsbridges.world.placement.BridgePlacementConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class YBModConfiguredFeatures {
    public static final ConfiguredFeature<?, ?> BRIDGE_OAK_5 = YBModFeatures.BRIDGE_OAK.get()
        .withConfiguration(new BridgeFeatureConfig("bridge/oak/5_0"))
        .withPlacement(YBModPlacements.BRIDGE.get().configure(new BridgePlacementConfig(9, 3, 3, 7)))
        .chance(1)
        .withPlacement(YBModConfiguredPlacements.RNG_INITIALIZER);

    public static final ConfiguredFeature<?, ?> BRIDGE_OAK_7 = YBModFeatures.BRIDGE_OAK.get()
        .withConfiguration(new BridgeFeatureConfig("bridge/oak/7_0"))
        .withPlacement(YBModPlacements.BRIDGE.get().configure(new BridgePlacementConfig(11, 3, 3, 9)))
        .chance(1)
        .withPlacement(YBModConfiguredPlacements.RNG_INITIALIZER);

    public static void registerConfiguredFeatures() {
        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        Registry.register(registry, new ResourceLocation(YungsBridges.MOD_ID, "bridge_oak_5"), BRIDGE_OAK_5);
        Registry.register(registry, new ResourceLocation(YungsBridges.MOD_ID, "bridge_oak_7"), BRIDGE_OAK_7);
    }
}
