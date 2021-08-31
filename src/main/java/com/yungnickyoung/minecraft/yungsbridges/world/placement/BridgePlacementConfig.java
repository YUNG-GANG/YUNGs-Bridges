package com.yungnickyoung.minecraft.yungsbridges.world.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class BridgePlacementConfig implements IPlacementConfig {
    public static final Codec<BridgePlacementConfig> CODEC = RecordCodecBuilder.create((codec) ->
        codec.group(
            Codec.INT.fieldOf("length").forGetter((config) -> config.length),
            Codec.INT.fieldOf("width").forGetter((config) -> config.width),
            Codec.INT.fieldOf("minWaterZ").forGetter((config) -> config.minWaterZ),
            Codec.INT.fieldOf("maxWaterZ").forGetter((config) -> config.maxWaterZ),
            Codec.INT.fieldOf("widthOffset").forGetter((config) -> config.widthOffset)
        ).apply(codec, BridgePlacementConfig::new));

    /** Length of the bridge. This is usually the exact length of the bridge NBT structure itself. */
    public final int length;

    /** Width of the bridge. This is usually the exact width of the bridge NBT structure itself. */
    public final int width;

    /** Local minimum z position at which we start requiring blocks at sea level to be water for placement. */
    public final int minWaterZ;

    /** Local maximum z position at which we stop requiring blocks at sea level to be water for placement. */
    public final int maxWaterZ;

    /**
     * Offset in the x-direction from the edge of the structure to the point at which we want to begin water checks.
     * This is useful for bridges that have large side decorations that shouldn't contribute to the width
     * of the bridge itself.
     */
    public int widthOffset;

    public BridgePlacementConfig(int length, int width, int minWaterZ, int maxWaterZ, int widthOffset) {
        this.length = length;
        this.width = width;
        this.minWaterZ = minWaterZ;
        this.maxWaterZ = maxWaterZ;
        this.widthOffset = widthOffset;
    }

    public BridgePlacementConfig(int length, int width, int minWaterZ, int maxWaterZ) {
        this(length, width, minWaterZ, maxWaterZ, 0);
    }
}
