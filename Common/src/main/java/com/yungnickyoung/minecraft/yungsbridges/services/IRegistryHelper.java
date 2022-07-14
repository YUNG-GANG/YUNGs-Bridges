package com.yungnickyoung.minecraft.yungsbridges.services;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public interface IRegistryHelper {
    void registerFeature(ResourceLocation resourceLocation, Feature<?> feature);

    void registerPlacementModifierType(ResourceLocation resourceLocation, PlacementModifierType<?> placementModifierType);
}
