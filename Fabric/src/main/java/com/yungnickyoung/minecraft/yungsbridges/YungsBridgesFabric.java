package com.yungnickyoung.minecraft.yungsbridges;

import net.fabricmc.api.ModInitializer;

public class YungsBridgesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        YungsBridgesCommon.init();
    }
}
