package com.yungnickyoung.minecraft.yungsbridges.services;

import com.yungnickyoung.minecraft.yungsbridges.module.BiomeModificationModuleFabric;

public class FabricModulesLoader implements IModulesLoader {
    @Override
    public void loadModules() {
        IModulesLoader.super.loadModules(); // Load common modules
        BiomeModificationModuleFabric.init();
    }
}
