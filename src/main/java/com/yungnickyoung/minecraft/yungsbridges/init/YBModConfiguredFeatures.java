package com.yungnickyoung.minecraft.yungsbridges.init;

import com.yungnickyoung.minecraft.yungsbridges.YungsBridges;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.BridgeFeatureConfig;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.MultipleAttemptSingleRandomFeatureConfig;
import com.yungnickyoung.minecraft.yungsbridges.world.placement.BridgePlacement;
import com.yungnickyoung.minecraft.yungsbridges.world.placement.BridgePlacementConfig;
import com.yungnickyoung.minecraft.yungsbridges.world.placement.RngInitializerPlacement;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class YBModConfiguredFeatures {
    private static final List<Supplier<PlacedFeature>> PLACED_BRIDGES = new ArrayList<>();

    static {
        addLargeBridge("bridge/stone/31_0", new BridgePlacementConfig(31, 5, 4, 28).widthOffset(2).solidBlocks(3));
        addLargeBridge("bridge/wood/27_0",  new BridgePlacementConfig(27, 5, 2, 26).solidBlocks(2));
        addMediumBridge("bridge/stone/24_0", new BridgePlacementConfig(24, 5, 2, 23).solidBlocks(2));
        addMediumBridge("bridge/stone/24_0_broken", new BridgePlacementConfig(24, 5, 2, 23).solidBlocks(2));
        addMediumBridge("bridge/stone/22_0", new BridgePlacementConfig(22, 5, 2, 21).solidBlocks(2));
        addMediumBridge("bridge/stone/22_0_broken", new BridgePlacementConfig(22, 5, 2, 21).solidBlocks(2));
        addSmallBridge("bridge/wood/17_0",  new BridgePlacementConfig(17, 4, 2, 16).solidBlocks(2));
        addSmallBridge("bridge/wood/17_0_broken",  new BridgePlacementConfig(17, 4, 2, 16).solidBlocks(2));
        addSmallBridge("bridge/stone/16_0", new BridgePlacementConfig(16, 5, 2, 15).solidBlocks(2));
        addSmallBridge("bridge/stone/16_0_broken", new BridgePlacementConfig(16, 5, 2, 15).solidBlocks(2));
        addSmallBridge("bridge/stone/15_1", new BridgePlacementConfig(15, 5, 2, 14).widthOffset(1).solidBlocks(2));
    }

    public static final ConfiguredFeature<?, ?> BRIDGE_LIST_FEATURE_CONFIGURED = YBModFeatures.MULTIPLE_ATTEMPT_SINGLE_RANDOM
            .configured(new MultipleAttemptSingleRandomFeatureConfig(PLACED_BRIDGES));

    public static final PlacedFeature BRIDGE_LIST_FEATURE_PLACED = BRIDGE_LIST_FEATURE_CONFIGURED
            .placed(BiomeFilter.biome());

    /**
     * Registration methods.
     */

    public static void registerConfiguredFeatures() {
        registerConfiguredFeature(BRIDGE_LIST_FEATURE_CONFIGURED, "bridge_list");
    }

    public static void registerPlacedFeatures() {
        registerPlacedFeature(BRIDGE_LIST_FEATURE_PLACED, "bridge_list");
    }

    private static void registerConfiguredFeature(ConfiguredFeature<?, ?> feature, String id) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(YungsBridges.MOD_ID, id), feature);
    }

    private static void registerPlacedFeature(PlacedFeature feature, String id) {
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(YungsBridges.MOD_ID, id), feature);
    }

    /**
     * Utility methods fore registering bridge variants.
     */

    private static void addLargeBridge(String id, BridgePlacementConfig placementConfig) {
        addBridge(id, placementConfig, YungsBridges.CONFIG.yungsBridges.spawnRates.largeBridges);
    }

    private static void addMediumBridge(String id, BridgePlacementConfig placementConfig) {
        addBridge(id, placementConfig, YungsBridges.CONFIG.yungsBridges.spawnRates.mediumBridges);
    }

    private static void addSmallBridge(String id, BridgePlacementConfig placementConfig) {
        addBridge(id, placementConfig, YungsBridges.CONFIG.yungsBridges.spawnRates.smallBridges);
    }

    private static void addBridge(String id, BridgePlacementConfig placementConfig, int chance) {
        BridgeFeatureConfig featureConfig = new BridgeFeatureConfig(id);

        ConfiguredFeature<?, ?> configuredFeature = YBModFeatures.BRIDGE.configured(featureConfig);
        PlacedFeature placedFeature = configuredFeature.placed(
                BridgePlacement.of(placementConfig),
                RarityFilter.onAverageOnceEvery(chance),
                RngInitializerPlacement.randomized()
        );

        ConfiguredFeature<?, ?> rotatedConfiguredFeature = YBModFeatures.BRIDGE.configured(featureConfig.rotatedCopy());
        PlacedFeature placedRotatedFeature = rotatedConfiguredFeature.placed(
                BridgePlacement.of(placementConfig.rotatedCopy()),
                RarityFilter.onAverageOnceEvery(chance),
                RngInitializerPlacement.randomized()
        );

        PLACED_BRIDGES.add(() -> placedFeature);
        PLACED_BRIDGES.add(() -> placedRotatedFeature);
    }
}
