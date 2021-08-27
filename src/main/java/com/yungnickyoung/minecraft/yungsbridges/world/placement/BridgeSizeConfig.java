package com.yungnickyoung.minecraft.yungsbridges.world.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.CaveEdgeConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class BridgeSizeConfig implements IPlacementConfig, IFeatureConfig {
    public static final Codec<BridgeSizeConfig> CODEC = RecordCodecBuilder.create((codec) ->
        codec.group(
            Codec.INT.fieldOf("size").forGetter((config) -> config.size),
            Codec.INT.fieldOf("minWaterZ").forGetter((config) -> config.minWaterZ),
            Codec.INT.fieldOf("maxWaterZ").forGetter((config) -> config.maxWaterZ)
        ).apply(codec, BridgeSizeConfig::new));

    public final int size;
    public final int minWaterZ;
    public final int maxWaterZ;

    public BridgeSizeConfig(int size, int minWaterZ, int maxWaterZ) {
        this.size = size;
        this.minWaterZ = minWaterZ;
        this.maxWaterZ = maxWaterZ;
    }
}
