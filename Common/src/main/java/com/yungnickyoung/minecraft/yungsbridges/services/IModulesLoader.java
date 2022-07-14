package com.yungnickyoung.minecraft.yungsbridges.services;

import com.yungnickyoung.minecraft.yungsbridges.module.FeatureModule;
import com.yungnickyoung.minecraft.yungsbridges.module.PlacementModifierTypeModule;

public interface IModulesLoader {
    default void loadModules() {
        FeatureModule.init();
        PlacementModifierTypeModule.init();
    }
}
