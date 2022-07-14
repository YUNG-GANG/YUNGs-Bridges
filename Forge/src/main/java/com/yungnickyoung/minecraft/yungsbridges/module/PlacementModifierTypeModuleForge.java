package com.yungnickyoung.minecraft.yungsbridges.module;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import java.util.HashMap;
import java.util.Map;

public class PlacementModifierTypeModuleForge {
    public static Map<ResourceLocation, PlacementModifierType<?>> PLACEMENTS = new HashMap<>();

    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(PlacementModifierTypeModuleForge::registerPlacements);
    }

    private static void registerPlacements(RegisterEvent event) {
        event.register(Registry.PLACEMENT_MODIFIER_REGISTRY, helper -> PLACEMENTS.forEach(helper::register));
    }
}
