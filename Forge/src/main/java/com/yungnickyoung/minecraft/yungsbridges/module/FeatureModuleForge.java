package com.yungnickyoung.minecraft.yungsbridges.module;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import java.util.HashMap;
import java.util.Map;

public class FeatureModuleForge {
    public static Map<ResourceLocation, Feature<?>> FEATURES = new HashMap<>();

    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(FeatureModuleForge::registerFeatures);
    }

    private static void registerFeatures(RegisterEvent event) {
        event.register(Registry.FEATURE_REGISTRY, helper -> FEATURES.forEach(helper::register));
    }
}
