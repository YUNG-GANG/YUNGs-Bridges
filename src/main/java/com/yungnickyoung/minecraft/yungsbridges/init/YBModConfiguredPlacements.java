package com.yungnickyoung.minecraft.yungsbridges.init;

import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;

public class YBModConfiguredPlacements {
    public static final ConfiguredPlacement<NoPlacementConfig> RNG_INITIALIZER = YBModPlacements.RNG_INITIALIZER.get()
        .configure(IPlacementConfig.NO_PLACEMENT_CONFIG);
}