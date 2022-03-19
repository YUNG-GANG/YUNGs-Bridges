package com.yungnickyoung.minecraft.yungsbridges.module;

import java.util.ArrayList;
import java.util.List;

public class ConfigModule {
    public SpawnRates spawnRates = new SpawnRates();
    public List<String> blacklistedBiomes = new ArrayList<>();

    public static class SpawnRates {
        public int smallBridges = 2;
        public int mediumBridges = 2;
        public int largeBridges = 2;
    }
}
