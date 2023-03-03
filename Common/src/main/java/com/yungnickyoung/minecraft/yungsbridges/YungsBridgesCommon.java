package com.yungnickyoung.minecraft.yungsbridges;

import com.yungnickyoung.minecraft.yungsapi.api.YungAutoRegister;
import com.yungnickyoung.minecraft.yungsbridges.module.ConfigModule;
import com.yungnickyoung.minecraft.yungsbridges.services.Services;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class YungsBridgesCommon {
    public static final String MOD_ID = "yungsbridges";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final ConfigModule CONFIG = new ConfigModule();

    public static void init() {
        YungAutoRegister.scanPackageForAnnotations("com.yungnickyoung.minecraft.yungsbridges.module");
        Services.MODULES.loadModules();
    }
}
