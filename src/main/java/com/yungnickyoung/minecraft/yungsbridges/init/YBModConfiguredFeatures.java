package com.yungnickyoung.minecraft.yungsbridges.init;

import com.yungnickyoung.minecraft.yungsbridges.YungsBridges;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.BridgeFeatureConfig;
import com.yungnickyoung.minecraft.yungsbridges.world.placement.BridgePlacementConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.ArrayList;
import java.util.List;

public class YBModConfiguredFeatures {
    public static List<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = new ArrayList<>();

    public static final ConfiguredFeature<?, ?> BRIDGE_STONE_31 = addBridge("bridge/stone/31_0", new BridgePlacementConfig(31, 5, 4, 28, 2, 3), 1);
    public static final ConfiguredFeature<?, ?> BRIDGE_WOOD_27  = addBridge("bridge/wood/27_0", new BridgePlacementConfig(27, 5, 2, 26, 0, 2), 1);
    public static final ConfiguredFeature<?, ?> BRIDGE_STONE_24 = addBridge("bridge/stone/24_0", new BridgePlacementConfig(24, 5, 2, 23, 0, 2), 1);
    public static final ConfiguredFeature<?, ?> BRIDGE_STONE_22 = addBridge("bridge/stone/22_0", new BridgePlacementConfig(22, 5, 2, 21, 0, 2), 1);
    public static final ConfiguredFeature<?, ?> BRIDGE_WOOD_17  = addBridge("bridge/wood/17_0", new BridgePlacementConfig(17, 4, 2, 16, 0, 2), 1);
    public static final ConfiguredFeature<?, ?> BRIDGE_STONE_16 = addBridge("bridge/stone/16_0", new BridgePlacementConfig(16, 5, 2, 15, 0, 2), 1);
    public static final ConfiguredFeature<?, ?> BRIDGE_STONE_15 = addBridge("bridge/stone/15_1", new BridgePlacementConfig(15, 5, 2, 14, 1, 2), 1);
    public static final ConfiguredFeature<?, ?> BRIDGE_WOOD_15  = addBridge("bridge/wood/15_0", new BridgePlacementConfig(15, 3, 3, 13), 1);
    public static final ConfiguredFeature<?, ?> BRIDGE_WOOD_13  = addBridge("bridge/wood/13_0", new BridgePlacementConfig(13, 3, 3, 11), 1);

    public static void registerConfiguredFeatures() {
        registerConfiguredFeature(BRIDGE_STONE_31, "bridge_stone_31");
        registerConfiguredFeature(BRIDGE_WOOD_27, "bridge_wood_27");
        registerConfiguredFeature(BRIDGE_STONE_24, "bridge_stone_24");
        registerConfiguredFeature(BRIDGE_STONE_22, "bridge_stone_22");
        registerConfiguredFeature(BRIDGE_STONE_16, "bridge_stone_16");
        registerConfiguredFeature(BRIDGE_STONE_15, "bridge_stone_15");
        registerConfiguredFeature(BRIDGE_WOOD_17, "bridge_wood_17");
        registerConfiguredFeature(BRIDGE_WOOD_15, "bridge_wood_15");
        registerConfiguredFeature(BRIDGE_WOOD_13, "bridge_wood_13");
    }

    private static ConfiguredFeature<?, ?> addBridge(String id, BridgePlacementConfig placementConfig, int chance) {
        ConfiguredFeature<?, ?> feature = YBModFeatures.BRIDGE.get()
            .withConfiguration(new BridgeFeatureConfig(id))
            .withPlacement(YBModPlacements.BRIDGE.get().configure(placementConfig))
            .chance(chance)
            .withPlacement(YBModConfiguredPlacements.RNG_INITIALIZER);
        CONFIGURED_FEATURES.add(feature);
        return feature;
    }

    private static void registerConfiguredFeature(ConfiguredFeature<?, ?> feature, String id) {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(YungsBridges.MOD_ID, id), feature);
    }
}
