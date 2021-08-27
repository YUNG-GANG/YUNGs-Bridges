package com.yungnickyoung.minecraft.yungsbridges.world.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.yungnickyoung.minecraft.yungsbridges.YungsBridges;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class BridgeSizeConfig implements IPlacementConfig, IFeatureConfig {
    public static final Codec<BridgeSizeConfig> CODEC = RecordCodecBuilder.create((codec) ->
        codec.group(
            Codec.INT.fieldOf("length").forGetter((config) -> config.length),
            Codec.INT.fieldOf("width").forGetter((config) -> config.width),
            Codec.INT.fieldOf("minWaterZ").forGetter((config) -> config.minWaterZ),
            Codec.INT.fieldOf("maxWaterZ").forGetter((config) -> config.maxWaterZ),
            ResourceLocation.CODEC.fieldOf("id").forGetter((config) -> config.id)
        ).apply(codec, BridgeSizeConfig::new));

    public final int length;
    public final int width;
    public final int minWaterZ;
    public final int maxWaterZ;
    public final ResourceLocation id;

    public BridgeSizeConfig(int length, int width, int minWaterZ, int maxWaterZ, String name) {
        this(length, width, minWaterZ, maxWaterZ, new ResourceLocation(YungsBridges.MOD_ID, name));
    }

    public BridgeSizeConfig(int length, int width, int minWaterZ, int maxWaterZ, ResourceLocation id) {
        this.length = length;
        this.width = width;
        this.minWaterZ = minWaterZ;
        this.maxWaterZ = maxWaterZ;
        this.id = id;
    }
}
