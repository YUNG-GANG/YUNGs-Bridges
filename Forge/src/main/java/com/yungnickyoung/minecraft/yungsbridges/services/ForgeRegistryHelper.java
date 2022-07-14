package com.yungnickyoung.minecraft.yungsbridges.services;

import com.yungnickyoung.minecraft.yungsbridges.module.FeatureModuleForge;
import com.yungnickyoung.minecraft.yungsbridges.module.PlacementModifierTypeModuleForge;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class ForgeRegistryHelper implements IRegistryHelper {
    @Override
    public void registerFeature(ResourceLocation resourceLocation, Feature<?> feature) {
        FeatureModuleForge.FEATURES.put(resourceLocation, feature);
    }

    @Override
    public void registerPlacementModifierType(ResourceLocation resourceLocation, PlacementModifierType<?> placementModifierType) {
        PlacementModifierTypeModuleForge.PLACEMENTS.put(resourceLocation, placementModifierType);
    }
}
