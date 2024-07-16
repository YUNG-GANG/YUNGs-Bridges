package com.yungnickyoung.minecraft.yungsbridges.world.placement;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.yungnickyoung.minecraft.yungsbridges.module.PlacementModifierTypeModule;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

/**
 * Determines valid placement for bridges.
 * Each bridge requires solid land on either side of it,
 * as well as a certain distance of water only between the two ends.
 */
@MethodsReturnNonnullByDefault
public class BridgePlacement extends PlacementModifier {
    public static final MapCodec<BridgePlacement> CODEC = RecordCodecBuilder.mapCodec((codec) ->
        codec.group(
            Codec.INT.fieldOf("length").forGetter((bridgePlacement) -> bridgePlacement.length),
            Codec.INT.fieldOf("width").forGetter((bridgePlacement) -> bridgePlacement.width),
            Codec.INT.fieldOf("min_water_z").forGetter((bridgePlacement) -> bridgePlacement.minWaterZ),
            Codec.INT.fieldOf("max_water_z").forGetter((bridgePlacement) -> bridgePlacement.maxWaterZ),
            Codec.INT.fieldOf("width_offset").forGetter((bridgePlacement) -> bridgePlacement.widthOffset),
            Codec.INT.fieldOf("num_solid_blocks_needed").forGetter((bridgePlacement) -> bridgePlacement.numSolidBlocksNeeded),
            Codec.BOOL.optionalFieldOf("is_z_axis", true).forGetter((bridgePlacement) -> bridgePlacement.isZAxis)
        ).apply(codec, BridgePlacement::new));

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
    public boolean isZAxis;

    private BridgePlacement(int length, int width, int minWaterZ, int maxWaterZ, int widthOffset, int numSolidBlocksNeeded, boolean isZAxis) {
        this.length = length;
        this.width = width;
        this.minWaterZ = minWaterZ;
        this.maxWaterZ = maxWaterZ;
        this.widthOffset = widthOffset;
        this.numSolidBlocksNeeded = numSolidBlocksNeeded;
        this.isZAxis = isZAxis;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext placementContext, RandomSource randomSource, BlockPos blockPos) {
        // Mutable that is always at sea level
        BlockPos.MutableBlockPos seaLevelMutable = blockPos.mutable();
        int seaLevel = placementContext.getLevel().getSeaLevel() - 1;
        seaLevelMutable.setY(seaLevel);

        // DEBUG
//        Set<BlockPos> SOLID_BLOCKS = new HashSet<>();
//        Set<BlockPos> WATER_BLOCKS = new HashSet<>();

        // Scan the grid, looking for suitable locations
        for (int candidateMiddleMinorAxisOffset = width / 2 + widthOffset + 1; candidateMiddleMinorAxisOffset < 16 - width / 2 - widthOffset; candidateMiddleMinorAxisOffset++) {
            for (int candidateStartMajorAxisOffset = 0; candidateStartMajorAxisOffset < 16; candidateStartMajorAxisOffset++) {
//                SOLID_BLOCKS.clear();
//                WATER_BLOCKS.clear();

                // Candidate starting position for the bridge
                BlockPos startingPos = isZAxis
                    ? new BlockPos(blockPos.getX() + candidateMiddleMinorAxisOffset, seaLevel, blockPos.getZ() + candidateStartMajorAxisOffset)
                    : new BlockPos(blockPos.getX() + candidateStartMajorAxisOffset, seaLevel, blockPos.getZ() + candidateMiddleMinorAxisOffset);

                // Corresponding ending position for the current candidate starting position.
                BlockPos endingPos = isZAxis
                    ? new BlockPos(blockPos.getX() + candidateMiddleMinorAxisOffset, seaLevel, blockPos.getZ() + candidateStartMajorAxisOffset + length + 1)
                    : new BlockPos(blockPos.getX() + candidateStartMajorAxisOffset + length + 1, seaLevel, blockPos.getZ() + candidateMiddleMinorAxisOffset);

                // Either side of the bridge must lead to solid land
                if (!
                    (placementContext.getBlockState(startingPos).canOcclude()
                        && placementContext.getBlockState(endingPos).canOcclude()
                        && placementContext.getHeight(Heightmap.Types.WORLD_SURFACE, startingPos.getX(), startingPos.getZ()) <= seaLevel + 1
                        && placementContext.getHeight(Heightmap.Types.WORLD_SURFACE, endingPos.getX(), endingPos.getZ()) <= seaLevel + 1)
                ) {
                    continue;
                }

                // Some bridges, esp. bigger ones, require additional solid blocks on each side of the bridge
                int numSolidBlocks = 1; // We already know the center block is solid

                // Check starting side
                for (int direction : Lists.newArrayList(-1, 1)) {
                    for (int minorAxisSolidDist = 1; minorAxisSolidDist <= width / 2; minorAxisSolidDist++) {
                        int minorAxisSolidOffset = direction * minorAxisSolidDist;

                        // Update mutable position
                        if (isZAxis) {
                            seaLevelMutable.set(startingPos.getX() + minorAxisSolidOffset, startingPos.getY(), startingPos.getZ());
                        } else {
                            seaLevelMutable.set(startingPos.getX(), startingPos.getY(), startingPos.getZ() + minorAxisSolidOffset);
                        }

                        if (placementContext.getBlockState(seaLevelMutable).canOcclude() && placementContext.getHeight(Heightmap.Types.WORLD_SURFACE, seaLevelMutable.getX(), seaLevelMutable.getZ()) <= seaLevel + 1) {
//                            SOLID_BLOCKS.add(seaLevelMutable.immutable());
                            numSolidBlocks++;
                        } else {
                            break;
                        }
                    }
                }

                if (numSolidBlocks < numSolidBlocksNeeded) continue;

                numSolidBlocks = 1; // Reset for other side. We already know the center block is solid

                // Check ending side
                for (int direction : Lists.newArrayList(-1, 1)) {
                    for (int minorAxisSolidDist = 1; minorAxisSolidDist <= width / 2; minorAxisSolidDist++) {
                        int minorAxisSolidOffset = direction * minorAxisSolidDist;

                        // Update mutable position
                        if (isZAxis) {
                            seaLevelMutable.set(endingPos.getX() + minorAxisSolidOffset, endingPos.getY(), endingPos.getZ());
                        } else {
                            seaLevelMutable.set(endingPos.getX(), endingPos.getY(), endingPos.getZ() + minorAxisSolidOffset);
                        }

                        if (placementContext.getBlockState(seaLevelMutable).canOcclude() && placementContext.getHeight(Heightmap.Types.WORLD_SURFACE, seaLevelMutable.getX(), seaLevelMutable.getZ()) <= seaLevel + 1) {
//                            SOLID_BLOCKS.add(seaLevelMutable.immutable());
                            numSolidBlocks++;
                        } else {
                            break;
                        }
                    }
                }

                if (numSolidBlocks < numSolidBlocksNeeded) continue;

                // Middle blocks must all be water, starting with the positions designated by minWaterZ and maxWaterZ
                boolean isAllWater = true;
                for (int minorAxisWaterOffset = -width / 2; minorAxisWaterOffset <= width / 2; minorAxisWaterOffset++) {
                    for (int majorAxisWaterOffset = minWaterZ; majorAxisWaterOffset <= maxWaterZ; majorAxisWaterOffset++) {
                        // Update mutable position
                        if (isZAxis) {
                            seaLevelMutable.set(startingPos.getX() + minorAxisWaterOffset, seaLevel, startingPos.getZ() + majorAxisWaterOffset);
                        } else {
                            seaLevelMutable.set(startingPos.getX() + majorAxisWaterOffset, seaLevel, startingPos.getZ() + minorAxisWaterOffset);
                        }

//                        if (placementContext.getBlockState(seaLevelMutable).getMaterial() == Material.WATER) {
//                            WATER_BLOCKS.add(seaLevelMutable.immutable());
//                        }

                        if (!placementContext.getBlockState(seaLevelMutable).liquid()) {
                            isAllWater = false;
                            break;
                        }
                    }
                }

                // Final check if bridge position is valid
                if (isAllWater) {
                    // DEBUG stuff
//                    SOLID_BLOCKS.forEach(pos -> placementContext.getLevel().setBlock(pos, Blocks.EMERALD_BLOCK.defaultBlockState(), 2));
//                    WATER_BLOCKS.forEach(pos -> placementContext.getLevel().setBlock(pos, Blocks.LAPIS_BLOCK.defaultBlockState(), 2));
//                    placementContext.getLevel().setBlock(startingPos.atY(seaLevel + 10), Blocks.COPPER_BLOCK.defaultBlockState(), 2);
//                    placementContext.getLevel().setBlock(endingPos.atY(seaLevel + 10), Blocks.GLOWSTONE.defaultBlockState(), 2);
//                    if (isZAxis)
//                        placementContext.getLevel().setBlock(new BlockPos(startingPos.getX() - width / 2 - widthOffset, seaLevel + 10, startingPos.getZ() + 1), Blocks.DIAMOND_BLOCK.defaultBlockState(), 2);
//                    else
//                        placementContext.getLevel().setBlock(new BlockPos(startingPos.getX() + 1, seaLevel + 10, startingPos.getZ() + width / 2 + widthOffset), Blocks.GOLD_BLOCK.defaultBlockState(), 2);

                    return isZAxis
                        ? Stream.of(new BlockPos(startingPos.getX() - width / 2 - widthOffset, seaLevel, startingPos.getZ() + 1))
                        : Stream.of(new BlockPos(startingPos.getX() + 1, seaLevel, startingPos.getZ() + width / 2 + widthOffset));
                }
            }
        }

        return Stream.empty();
    }

    @Override
    public PlacementModifierType<?> type() {
        return PlacementModifierTypeModule.BRIDGE_PLACEMENT;
    }
}