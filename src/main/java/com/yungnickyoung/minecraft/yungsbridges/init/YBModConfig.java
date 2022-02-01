package com.yungnickyoung.minecraft.yungsbridges.init;

import com.yungnickyoung.minecraft.yungsbridges.YungsBridges;
import com.yungnickyoung.minecraft.yungsbridges.config.YBConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

public class YBModConfig {
    public static void init() {
        // Register mod config with AutoConfig
        AutoConfig.register(YBConfig.class, Toml4jConfigSerializer::new);
        YungsBridges.CONFIG = AutoConfig.getConfigHolder(YBConfig.class).getConfig();
    }
}
