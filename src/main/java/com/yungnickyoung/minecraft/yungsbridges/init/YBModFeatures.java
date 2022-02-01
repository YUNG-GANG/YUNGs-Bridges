package com.yungnickyoung.minecraft.yungsbridges.init;

import com.yungnickyoung.minecraft.yungsbridges.YungsBridges;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.BridgeFeature;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.BridgeFeatureConfig;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.MultipleAttemptSingleRandomFeature;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.MultipleAttemptSingleRandomFeatureConfig;
import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;

public class YBModFeatures {
    public static final Feature<BridgeFeatureConfig> BRIDGE = new BridgeFeature();
    public static final Feature<MultipleAttemptSingleRandomFeatureConfig> MULTIPLE_ATTEMPT_SINGLE_RANDOM = new MultipleAttemptSingleRandomFeature();

    public static void init () {
        register("bridge", BRIDGE);
        register("multiple_attempt_single_random", MULTIPLE_ATTEMPT_SINGLE_RANDOM);
        YBModConfiguredFeatures.registerConfiguredFeatures();
        YBModConfiguredFeatures.registerPlacedFeatures();
        addFeaturesToBiomes();
    }

    private static void register(String name, Feature<?> feature) {
        Registry.register(Registry.FEATURE, new ResourceLocation(YungsBridges.MOD_ID, name), feature);
    }

    /**
     * Adds configured features to appropriate biomes.
     */
    private static void addFeaturesToBiomes() {
        BiomeModifications.create(new ResourceLocation(YungsBridges.MOD_ID, "bridge_addition"))
                .add(ModificationPhase.ADDITIONS,
                        YBModFeatures::selectBiomes,
                        YBModFeatures::modifyBiomes);
    }

    private static boolean selectBiomes(BiomeSelectionContext context) {
        String biomeName = context.getBiomeKey().location().toString();
        if (YungsBridges.blacklistedBiomes.contains(biomeName)) return false;
        return context.getBiome().getBiomeCategory() == Biome.BiomeCategory.RIVER;
    }

    private static void modifyBiomes(BiomeModificationContext context) {
        context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, YBModConfiguredFeatures.BRIDGE_LIST_FEATURE_PLACED);
    }
}
