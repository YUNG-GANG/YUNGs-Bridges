package com.yungnickyoung.minecraft.yungsbridges.module;

import com.yungnickyoung.minecraft.yungsbridges.YungsBridgesCommon;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.BridgeFeature;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.MultipleAttemptSingleRandomFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class FeatureModuleFabric {
    public static void init() {
        registerFeatures();
        ConfiguredFeatureModule.registerConfiguredFeatures();
        ConfiguredFeatureModule.registerPlacedFeatures();
        addFeaturesToBiomes();
    }

    private static void addFeaturesToBiomes() {
        BiomeModifications.create(new ResourceLocation(YungsBridgesCommon.MOD_ID, "bridge_addition"))
                .add(ModificationPhase.ADDITIONS,
                        FeatureModuleFabric::selectBiomes,
                        FeatureModuleFabric::modifyBiomes);
    }

    private static boolean selectBiomes(BiomeSelectionContext context) {
        String biomeName = context.getBiomeKey().location().toString();
        if (YungsBridgesCommon.CONFIG.blacklistedBiomes.contains(biomeName)) return false;
        return Biome.getBiomeCategory(Holder.direct(context.getBiome())) == Biome.BiomeCategory.RIVER;
    }

    private static void modifyBiomes(BiomeModificationContext context) {
        context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ConfiguredFeatureModule.BRIDGE_LIST_FEATURE_PLACED);
    }

    private static void registerFeatures() {
        FeatureModule.BRIDGE = register("bridge", new BridgeFeature());
        FeatureModule.MULTIPLE_ATTEMPT_SINGLE_RANDOM = register("multiple_attempt_single_random", new MultipleAttemptSingleRandomFeature());
    }

    private static <FC extends FeatureConfiguration> Feature<FC> register(String name, Feature<FC> feature) {
        return Registry.register(Registry.FEATURE, new ResourceLocation(YungsBridgesCommon.MOD_ID, name), feature);
    }
}
