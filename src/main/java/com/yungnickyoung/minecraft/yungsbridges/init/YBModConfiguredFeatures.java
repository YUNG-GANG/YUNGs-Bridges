package com.yungnickyoung.minecraft.yungsbridges.init;

import com.yungnickyoung.minecraft.yungsbridges.YungsBridges;
import com.yungnickyoung.minecraft.yungsbridges.config.YBConfig;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.BridgeFeatureConfig;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.MultipleAttemptSingleRandomFeatureConfig;
import com.yungnickyoung.minecraft.yungsbridges.world.placement.BridgePlacementConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.placement.IPlacementConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class YBModConfiguredFeatures {
    private static final List<Supplier<ConfiguredFeature<?, ?>>> bridges = new ArrayList<>();

    static {
        addLargeBridge("bridge/stone/31_0", new BridgePlacementConfig(31, 5, 4, 28).widthOffset(2).solidBlocks(3));
        addLargeBridge("bridge/wood/27_0",  new BridgePlacementConfig(27, 5, 2, 26).solidBlocks(2));
        addMediumBridge("bridge/stone/24_0", new BridgePlacementConfig(24, 5, 2, 23).solidBlocks(2));
        addMediumBridge("bridge/stone/22_0", new BridgePlacementConfig(22, 5, 2, 21).solidBlocks(2));
        addSmallBridge("bridge/wood/17_0",  new BridgePlacementConfig(17, 4, 2, 16).solidBlocks(2));
        addSmallBridge("bridge/stone/16_0", new BridgePlacementConfig(16, 5, 2, 15).solidBlocks(2));
        addSmallBridge("bridge/stone/15_1", new BridgePlacementConfig(15, 5, 2, 14).widthOffset(1).solidBlocks(2));
        addSmallBridge("bridge/wood/15_0",  new BridgePlacementConfig(15, 3, 3, 13));
        addSmallBridge("bridge/wood/13_0",  new BridgePlacementConfig(13, 3, 3, 11));
    }

    public static final ConfiguredFeature<?, ?> BRIDGE_LIST_FEATURE = YBModFeatures.MULTIPLE_ATTEMPT_SINGLE_RANDOM.get()
        .withConfiguration(new MultipleAttemptSingleRandomFeatureConfig(bridges));

    public static void registerConfiguredFeatures() {
        registerConfiguredFeature(BRIDGE_LIST_FEATURE, "bridge_list");
    }

    private static void registerConfiguredFeature(ConfiguredFeature<?, ?> feature, String id) {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(YungsBridges.MOD_ID, id), feature);
    }

    private static void addLargeBridge(String id, BridgePlacementConfig placementConfig) {
        addBridge(id, placementConfig, YBConfig.spawnRates.largeBridges.get());
    }

    private static void addMediumBridge(String id, BridgePlacementConfig placementConfig) {
        addBridge(id, placementConfig, YBConfig.spawnRates.mediumBridges.get());
    }

    private static void addSmallBridge(String id, BridgePlacementConfig placementConfig) {
        addBridge(id, placementConfig, YBConfig.spawnRates.smallBridges.get());
    }

    private static void addBridge(String id, BridgePlacementConfig placementConfig, int chance) {
        BridgeFeatureConfig featureConfig = new BridgeFeatureConfig(id);

        ConfiguredFeature<?, ?> feature = YBModFeatures.BRIDGE.get()
            .withConfiguration(featureConfig)
            .withPlacement(YBModPlacements.BRIDGE.get().configure(placementConfig))
            .chance(chance)
            .withPlacement(YBModPlacements.RNG_INITIALIZER.get().configure(IPlacementConfig.NO_PLACEMENT_CONFIG));

        ConfiguredFeature<?, ?> rotatedFeature = YBModFeatures.BRIDGE.get()
            .withConfiguration(featureConfig.rotatedCopy())
            .withPlacement(YBModPlacements.BRIDGE.get().configure(placementConfig.rotatedCopy()))
            .chance(chance)
            .withPlacement(YBModPlacements.RNG_INITIALIZER.get().configure(IPlacementConfig.NO_PLACEMENT_CONFIG));

        bridges.add(() -> feature);
        bridges.add(() -> rotatedFeature);
    }
}
