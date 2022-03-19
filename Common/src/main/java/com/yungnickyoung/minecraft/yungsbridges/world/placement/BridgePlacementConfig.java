package com.yungnickyoung.minecraft.yungsbridges.world.placement;

public class BridgePlacementConfig  {
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

    /** The number of sea-level blocks on each end of the bridge that must be solid */
    public int numSolidBlocksNeeded;

    /** Rotation of the bridge. True if bridge should go north-south, false if east-west. */
    public boolean northSouth;

    public BridgePlacementConfig(int length, int width, int minWaterZ, int maxWaterZ, int widthOffset, int numSolidBlocksNeeded, boolean northSouth) {
        this.length = length;
        this.width = width;
        this.minWaterZ = minWaterZ;
        this.maxWaterZ = maxWaterZ;
        this.widthOffset = widthOffset;
        this.numSolidBlocksNeeded = numSolidBlocksNeeded;
        this.northSouth = northSouth;
    }

    public BridgePlacementConfig(int length, int width, int minWaterZ, int maxWaterZ) {
        this(length, width, minWaterZ, maxWaterZ, 0, 1, true);
    }

    public BridgePlacementConfig(BridgePlacementConfig other) {
        this(other.length, other.width, other.minWaterZ, other.maxWaterZ, other.widthOffset, other.numSolidBlocksNeeded, other.northSouth);
    }

    public BridgePlacementConfig widthOffset(int widthOffset) {
        this.widthOffset = widthOffset;
        return this;
    }

    public BridgePlacementConfig solidBlocks(int numSolidBlocksNeeded) {
        this.numSolidBlocksNeeded = numSolidBlocksNeeded;
        return this;
    }

    public BridgePlacementConfig rotatedCopy() {
        BridgePlacementConfig configCopy = new BridgePlacementConfig(this);
        configCopy.northSouth = !this.northSouth;
        return configCopy;
    }
}
