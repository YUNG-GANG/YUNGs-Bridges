package com.yungnickyoung.minecraft.yungsbridges.services;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class FabricRegistryHelper implements IRegistryHelper {
    @Override
    public void registerFeature(ResourceLocation resourceLocation, Feature<?> feature) {
        Registry.register(Registry.FEATURE, resourceLocation, feature);
    }

    @Override
    public void registerPlacementModifierType(ResourceLocation resourceLocation, PlacementModifierType<?> placementModifierType) {
        Registry.register(Registry.PLACEMENT_MODIFIERS, resourceLocation, placementModifierType);
    }
}
