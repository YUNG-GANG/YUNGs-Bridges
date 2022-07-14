package com.yungnickyoung.minecraft.yungsbridges.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class BridgeFeatureConfig implements FeatureConfiguration {
    public static final Codec<BridgeFeatureConfig> CODEC = RecordCodecBuilder.create((codec) -> codec.group(
            ResourceLocation.CODEC.fieldOf("location").forGetter((config) -> config.id),
            Codec.BOOL.optionalFieldOf("is_z_axis", true).forGetter((config) -> config.isZAxis)
        ).apply(codec, BridgeFeatureConfig::new));

    /** ResourceLocation pointing to the structure's NBT */
    public final ResourceLocation id;

    /** Rotation of the bridge. True if bridge should go north-south, false if east-west. */
    public boolean isZAxis;

    public BridgeFeatureConfig(ResourceLocation id, boolean isZAxis) {
        this.id = id;
        this.isZAxis = isZAxis;
    }
}
