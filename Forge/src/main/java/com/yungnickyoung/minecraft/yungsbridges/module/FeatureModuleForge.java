package com.yungnickyoung.minecraft.yungsbridges.module;

import com.yungnickyoung.minecraft.yungsbridges.YungsBridgesCommon;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.BridgeFeature;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.BridgeFeatureConfig;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.MultipleAttemptSingleRandomFeature;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;

public class FeatureModuleForge {
    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Feature.class, FeatureModuleForge::registerFeatures);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(FeatureModuleForge::registerConfiguredFeatures);
        MinecraftForge.EVENT_BUS.addListener(FeatureModuleForge::addFeaturesToBiomes); // We use normal priority since we are both removing and adding stuff
    }

    private static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();
        FeatureModule.BRIDGE = register(registry, "bridge", new BridgeFeature());
        FeatureModule.MULTIPLE_ATTEMPT_SINGLE_RANDOM = register(registry, "multiple_attempt_single_random", new MultipleAttemptSingleRandomFeature());
    }

    private static void registerConfiguredFeatures(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ConfiguredFeatureModule.registerConfiguredFeatures();
            ConfiguredFeatureModule.registerPlacedFeatures();
        });
    }

    private static void addFeaturesToBiomes(BiomeLoadingEvent event) {
        if (YungsBridgesCommon.CONFIG.blacklistedBiomes.contains(event.getName().toString())) return;

        // Add bridges to non-blacklisted river biomes
        if (event.getCategory() == Biome.BiomeCategory.RIVER) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.SURFACE_STRUCTURES).add(Holder.direct(ConfiguredFeatureModule.BRIDGE_LIST_FEATURE_PLACED));
        }
    }

    private static <FC extends FeatureConfiguration> Feature<FC> register(IForgeRegistry<Feature<?>> registry, String name, Feature<FC> feature) {
        feature.setRegistryName(new ResourceLocation(YungsBridgesCommon.MOD_ID, name));
        registry.register(feature);
        return feature;
    }
}
