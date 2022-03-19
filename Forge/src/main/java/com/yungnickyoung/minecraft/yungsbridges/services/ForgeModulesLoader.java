package com.yungnickyoung.minecraft.yungsbridges.services;

import com.yungnickyoung.minecraft.yungsbridges.module.ConfigModuleForge;
import com.yungnickyoung.minecraft.yungsbridges.module.FeatureModuleForge;
import com.yungnickyoung.minecraft.yungsbridges.module.PlacementModuleForge;

public class ForgeModulesLoader implements IModulesLoader {
    @Override
    public void loadModules() {
        ConfigModuleForge.init();
        PlacementModuleForge.init();
        FeatureModuleForge.init();
    }
}
