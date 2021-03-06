package com.yungnickyoung.minecraft.yungsbridges.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.yungnickyoung.minecraft.yungsbridges.YungsBridgesCommon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class BridgeFeatureConfig implements FeatureConfiguration {
    public static final Codec<BridgeFeatureConfig> CODEC = RecordCodecBuilder.create((codec) -> codec.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter((config) -> config.id),
            Codec.BOOL.fieldOf("northSouth").forGetter((config) -> config.northSouth)
        ).apply(codec, BridgeFeatureConfig::new));

    /** ResourceLocation pointing to the structure's NBT */
    public final ResourceLocation id;

    /** Rotation of the bridge. True if bridge should go north-south, false if east-west. */
    public boolean northSouth;

    public BridgeFeatureConfig(ResourceLocation id, boolean northSouth) {
        this.id = id;
        this.northSouth = northSouth;
    }

    public BridgeFeatureConfig(ResourceLocation id) {
        this.id = id;
        this.northSouth = true;
    }

    public BridgeFeatureConfig(String name) {
        this(new ResourceLocation(YungsBridgesCommon.MOD_ID, name));
    }

    public BridgeFeatureConfig(BridgeFeatureConfig other) {
        this(other.id, other.northSouth);
    }

    public BridgeFeatureConfig rotatedCopy() {
        BridgeFeatureConfig configCopy = new BridgeFeatureConfig(this);
        configCopy.northSouth = !this.northSouth;
        return configCopy;
    }
}
