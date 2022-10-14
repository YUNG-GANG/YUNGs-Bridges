package com.yungnickyoung.minecraft.yungsbridges.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsbridges.YungsBridgesCommon;
import com.yungnickyoung.minecraft.yungsbridges.world.placement.BridgePlacement;
import com.yungnickyoung.minecraft.yungsbridges.world.placement.RngInitializerPlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

@AutoRegister(YungsBridgesCommon.MOD_ID)
public class PlacementModifierTypeModule {
    @AutoRegister("rng_initializer")
    public static PlacementModifierType<RngInitializerPlacement> RNG_INITIALIZER_PLACEMENT = () -> RngInitializerPlacement.CODEC;

    @AutoRegister("bridge")
    public static PlacementModifierType<BridgePlacement> BRIDGE_PLACEMENT = () -> BridgePlacement.CODEC;
}
