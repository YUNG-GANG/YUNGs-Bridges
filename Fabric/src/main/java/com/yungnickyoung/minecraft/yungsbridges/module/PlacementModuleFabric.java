package com.yungnickyoung.minecraft.yungsbridges.module;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungsbridges.YungsBridgesCommon;
import com.yungnickyoung.minecraft.yungsbridges.world.placement.BridgePlacement;
import com.yungnickyoung.minecraft.yungsbridges.world.placement.RngInitializerPlacement;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class PlacementModuleFabric {
    public static void init() {
        PlacementModule.RNG_INITIALIZER_PLACEMENT = register("rng_initializer", RngInitializerPlacement.CODEC);
        PlacementModule.BRIDGE_PLACEMENT = register("bridge", BridgePlacement.CODEC);
    }

    private static <P extends PlacementModifier> PlacementModifierType<P> register(String name, Codec<P> codec) {
        return Registry.register(Registry.PLACEMENT_MODIFIERS, new ResourceLocation(YungsBridgesCommon.MOD_ID, name), () -> codec);
    }
}
