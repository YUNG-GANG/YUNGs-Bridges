package com.yungnickyoung.minecraft.yungsbridges.init;

import com.yungnickyoung.minecraft.yungsbridges.YungsBridges;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.BridgeFeatureConfig;
import com.yungnickyoung.minecraft.yungsbridges.world.placement.BridgePlacementConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class YBModConfiguredFeatures {
    public static final ConfiguredFeature<?, ?> BRIDGE_OAK_13 = YBModFeatures.BRIDGE_OAK.get()
        .withConfiguration(new BridgeFeatureConfig("bridge/oak/13_0"))
        .withPlacement(YBModPlacements.BRIDGE.get().configure(new BridgePlacementConfig(13, 3, 3, 11)))
        .chance(1)
        .withPlacement(YBModConfiguredPlacements.RNG_INITIALIZER);

    public static final ConfiguredFeature<?, ?> BRIDGE_OAK_15 = YBModFeatures.BRIDGE_OAK.get()
        .withConfiguration(new BridgeFeatureConfig("bridge/oak/15_0"))
        .withPlacement(YBModPlacements.BRIDGE.get().configure(new BridgePlacementConfig(15, 3, 3, 13)))
        .chance(1)
        .withPlacement(YBModConfiguredPlacements.RNG_INITIALIZER);

    public static final ConfiguredFeature<?, ?> BRIDGE_STONE_24 = YBModFeatures.BRIDGE_OAK.get()
        .withConfiguration(new BridgeFeatureConfig("bridge/stone/24_0"))
        .withPlacement(YBModPlacements.BRIDGE.get().configure(new BridgePlacementConfig(24, 5, 6, 19)))
        .chance(1)
        .withPlacement(YBModConfiguredPlacements.RNG_INITIALIZER);

    public static final ConfiguredFeature<?, ?> BRIDGE_STONE_22 = YBModFeatures.BRIDGE_OAK.get()
        .withConfiguration(new BridgeFeatureConfig("bridge/stone/22_0"))
        .withPlacement(YBModPlacements.BRIDGE.get().configure(new BridgePlacementConfig(22, 5, 6, 17)))
        .chance(1)
        .withPlacement(YBModConfiguredPlacements.RNG_INITIALIZER);

    public static final ConfiguredFeature<?, ?> BRIDGE_STONE_16 = YBModFeatures.BRIDGE_OAK.get()
        .withConfiguration(new BridgeFeatureConfig("bridge/stone/16_0"))
        .withPlacement(YBModPlacements.BRIDGE.get().configure(new BridgePlacementConfig(16, 5, 4, 13)))
        .chance(1)
        .withPlacement(YBModConfiguredPlacements.RNG_INITIALIZER);

    public static final ConfiguredFeature<?, ?> BRIDGE_STONE_15 = YBModFeatures.BRIDGE_OAK.get()
        .withConfiguration(new BridgeFeatureConfig("bridge/stone/15_1"))
        .withPlacement(YBModPlacements.BRIDGE.get().configure(new BridgePlacementConfig(15, 5, 5, 11, 1)))
        .chance(1)
        .withPlacement(YBModConfiguredPlacements.RNG_INITIALIZER);

    public static void registerConfiguredFeatures() {
        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        Registry.register(registry, new ResourceLocation(YungsBridges.MOD_ID, "bridge_oak_5"), BRIDGE_OAK_13);
        Registry.register(registry, new ResourceLocation(YungsBridges.MOD_ID, "bridge_oak_7"), BRIDGE_OAK_15);
        Registry.register(registry, new ResourceLocation(YungsBridges.MOD_ID, "bridge_stone_24"), BRIDGE_STONE_24);
        Registry.register(registry, new ResourceLocation(YungsBridges.MOD_ID, "bridge_stone_22"), BRIDGE_STONE_22);
        Registry.register(registry, new ResourceLocation(YungsBridges.MOD_ID, "bridge_stone_16"), BRIDGE_STONE_16);
        Registry.register(registry, new ResourceLocation(YungsBridges.MOD_ID, "bridge_stone_15"), BRIDGE_STONE_15);
    }
}
