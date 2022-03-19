package com.yungnickyoung.minecraft.yungsbridges.services;

import com.yungnickyoung.minecraft.yungsbridges.module.ConfigModuleFabric;
import com.yungnickyoung.minecraft.yungsbridges.module.FeatureModuleFabric;
import com.yungnickyoung.minecraft.yungsbridges.module.PlacementModuleFabric;

public class FabricModulesLoader implements IModulesLoader {
    @Override
    public void loadModules() {
        ConfigModuleFabric.init();
        PlacementModuleFabric.init();
        FeatureModuleFabric.init();
    }
}
