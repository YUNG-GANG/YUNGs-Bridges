package com.yungnickyoung.minecraft.yungsbridges.services;

import com.yungnickyoung.minecraft.yungsbridges.module.FeatureModuleForge;
import com.yungnickyoung.minecraft.yungsbridges.module.PlacementModifierTypeModuleForge;

public class ForgeModulesLoader implements IModulesLoader {
    @Override
    public void loadModules() {
        IModulesLoader.super.loadModules(); // Load common modules
        PlacementModifierTypeModuleForge.init();
        FeatureModuleForge.init();
    }
}
