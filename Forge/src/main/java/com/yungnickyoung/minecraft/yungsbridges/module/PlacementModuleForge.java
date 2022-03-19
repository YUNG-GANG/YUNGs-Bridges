package com.yungnickyoung.minecraft.yungsbridges.module;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungsbridges.YungsBridgesCommon;
import com.yungnickyoung.minecraft.yungsbridges.world.placement.BridgePlacement;
import com.yungnickyoung.minecraft.yungsbridges.world.placement.RngInitializerPlacement;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class PlacementModuleForge {
    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(PlacementModuleForge::commonSetup);
    }

    private static void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PlacementModule.RNG_INITIALIZER_PLACEMENT = register("rng_initializer", RngInitializerPlacement.CODEC);
            PlacementModule.BRIDGE_PLACEMENT = register("bridge", BridgePlacement.CODEC);
        });
    }

    private static <T extends PlacementModifier> PlacementModifierType<T> register(String name, Codec<T> codec) {
        return Registry.register(Registry.PLACEMENT_MODIFIERS, new ResourceLocation(YungsBridgesCommon.MOD_ID, name), () -> codec);
    }
}
