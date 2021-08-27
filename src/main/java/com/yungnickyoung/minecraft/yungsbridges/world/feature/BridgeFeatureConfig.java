package com.yungnickyoung.minecraft.yungsbridges.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.yungnickyoung.minecraft.yungsbridges.YungsBridges;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BridgeFeatureConfig implements IFeatureConfig {
    public static final Codec<BridgeFeatureConfig> CODEC = RecordCodecBuilder.create((codec) ->
        codec.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter((config) -> config.id)
        ).apply(codec, BridgeFeatureConfig::new));

    public final ResourceLocation id;

    public BridgeFeatureConfig(String name) {
        this(new ResourceLocation(YungsBridges.MOD_ID, name));
    }

    public BridgeFeatureConfig(ResourceLocation id) {
        this.id = id;
    }
}
