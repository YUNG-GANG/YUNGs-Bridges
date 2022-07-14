package com.yungnickyoung.minecraft.yungsbridges.module;

import com.yungnickyoung.minecraft.yungsbridges.YungsBridgesCommon;
import com.yungnickyoung.minecraft.yungsbridges.services.Services;
import com.yungnickyoung.minecraft.yungsbridges.world.placement.BridgePlacement;
import com.yungnickyoung.minecraft.yungsbridges.world.placement.RngInitializerPlacement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class PlacementModifierTypeModule {
    public static PlacementModifierType<RngInitializerPlacement> RNG_INITIALIZER_PLACEMENT = () -> RngInitializerPlacement.CODEC;
    public static PlacementModifierType<BridgePlacement> BRIDGE_PLACEMENT = () -> BridgePlacement.CODEC;

    public static void init() {
        Services.REGISTRY.registerPlacementModifierType(new ResourceLocation(YungsBridgesCommon.MOD_ID, "rng_initializer"), RNG_INITIALIZER_PLACEMENT);
        Services.REGISTRY.registerPlacementModifierType(new ResourceLocation(YungsBridgesCommon.MOD_ID, "bridge"), BRIDGE_PLACEMENT);
    }
}
