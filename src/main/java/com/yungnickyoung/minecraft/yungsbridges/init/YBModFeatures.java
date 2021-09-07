package com.yungnickyoung.minecraft.yungsbridges.init;

import com.yungnickyoung.minecraft.yungsbridges.YungsBridges;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.BridgeFeature;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.BridgeFeatureConfig;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.MultipleAttemptSingleRandomFeature;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.MultipleAttemptSingleRandomFeatureConfig;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class YBModFeatures {
    /* Registry for deferred registration */
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, YungsBridges.MOD_ID);

    /* Features */
    public static final RegistryObject<Feature<BridgeFeatureConfig>> BRIDGE = register("bridge", BridgeFeature::new);
    public static final RegistryObject<Feature<MultipleAttemptSingleRandomFeatureConfig>> MULTIPLE_ATTEMPT_SINGLE_RANDOM = register("multiple_attempt_single_random", MultipleAttemptSingleRandomFeature::new);

    public static void init () {
        FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(YBModFeatures::commonSetup);
        MinecraftForge.EVENT_BUS.addListener(YBModFeatures::onBiomeLoad);
    }

    /**
     * Register configured features.
     */
    private static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(YBModConfiguredFeatures::registerConfiguredFeatures);
    }

    /**
     * Adds configured features to appropriate biomes.
     */
    private static void onBiomeLoad(BiomeLoadingEvent event) {
        // Ignore blacklisted biomes
        if (YungsBridges.blacklistedBiomes.contains(event.getName().toString())) return;

        // Add bridges to non-blacklisted river biomes
        if (event.getCategory() == Biome.Category.RIVER) {
            event.getGeneration().getFeatures(GenerationStage.Decoration.SURFACE_STRUCTURES).add(() -> YBModConfiguredFeatures.BRIDGE_LIST_FEATURE);
        }
    }

    private static <T extends Feature<?>> RegistryObject<T> register(String name, Supplier<T> feature) {
        return FEATURES.register(name, feature);
    }
}
