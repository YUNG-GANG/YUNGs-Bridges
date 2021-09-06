package com.yungnickyoung.minecraft.yungsbridges.world.placement;

import com.google.common.collect.Lists;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.Placement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Determines valid placement for bridges.
 * Each bridge requires solid land on either side of it,
 * as well as a certain distance of water only between the two ends.
 * This distance and the bridge's general size are determined by the {@link BridgePlacementConfig}.
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BridgePlacement extends Placement<BridgePlacementConfig> {
    public BridgePlacement() {
        super(BridgePlacementConfig.CODEC);
    }

    @Override
    public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, BridgePlacementConfig config, BlockPos pos) {
        // Mutable that is always at sea level
        BlockPos.Mutable seaLevelMutable = pos.toMutable();
        int seaLevel = helper.func_242895_b() - 1;
        seaLevelMutable.setY(seaLevel);

        // Scan the grid, looking for suitable locations
        for (int candidateMiddleMinorAxisOffset = config.width / 2 + config.widthOffset + 1; candidateMiddleMinorAxisOffset < 16; candidateMiddleMinorAxisOffset++) {
            for (int candidateStartMajorAxisOffset = 0; candidateStartMajorAxisOffset < 16; candidateStartMajorAxisOffset++) {
                // Candidate starting position for the bridge
                BlockPos startingPos = config.northSouth
                    ? new BlockPos(pos.getX() + candidateMiddleMinorAxisOffset, seaLevel, pos.getZ() + candidateStartMajorAxisOffset)
                    : new BlockPos(pos.getX() + candidateStartMajorAxisOffset, seaLevel, pos.getZ() + candidateMiddleMinorAxisOffset);

                // Corresponding ending position for the current candidate starting position.
                BlockPos endingPos = config.northSouth
                    ? new BlockPos(pos.getX() + candidateMiddleMinorAxisOffset, seaLevel, pos.getZ() + candidateStartMajorAxisOffset + config.length + 1)
                    : new BlockPos(pos.getX() + candidateStartMajorAxisOffset + config.length + 1, seaLevel, pos.getZ() + candidateMiddleMinorAxisOffset);

                // Either side of the bridge must lead to solid land
                if (!
                    (helper.func_242894_a(startingPos).isSolid()
                        && helper.func_242894_a(endingPos).isSolid()
                        && helper.func_242893_a(Heightmap.Type.WORLD_SURFACE, startingPos.getX(), startingPos.getZ()) <= seaLevel + 1
                        && helper.func_242893_a(Heightmap.Type.WORLD_SURFACE, endingPos.getX(), endingPos.getZ()) <= seaLevel + 1)
                ) {
                    continue;
                }

                // Some bridges, esp. bigger ones, require additional solid blocks on each side of the bridge
                int numSolidBlocks = 1; // We already know the center block is solid

                // Check starting side
                for (int direction : Lists.newArrayList(-1, 1)) {
                    for (int minorAxisSolidDist = 1; minorAxisSolidDist <= config.width / 2; minorAxisSolidDist++) {
                        int minorAxisSolidOffset = direction * minorAxisSolidDist;

                        // Update mutable position
                        if (config.northSouth) {
                            seaLevelMutable.setPos(startingPos.getX() + minorAxisSolidOffset, startingPos.getY(), startingPos.getZ());
                        } else {
                            seaLevelMutable.setPos(startingPos.getX(), startingPos.getY(), startingPos.getZ() + minorAxisSolidOffset);
                        }

                        if (helper.func_242894_a(seaLevelMutable).isSolid() && helper.func_242893_a(Heightmap.Type.WORLD_SURFACE, seaLevelMutable.getX(), seaLevelMutable.getZ()) <= seaLevel + 1) {
                            numSolidBlocks++;
                        } else {
                            break;
                        }
                    }
                }

                if (numSolidBlocks < config.numSolidBlocksNeeded) continue;

                numSolidBlocks = 1; // Reset for other side. We already know the center block is solid

                // Check ending side
                for (int direction : Lists.newArrayList(-1, 1)) {
                    for (int minorAxisSolidDist = 1; minorAxisSolidDist <= config.width / 2; minorAxisSolidDist++) {
                        int minorAxisSolidOffset = direction * minorAxisSolidDist;

                        // Update mutable position
                        if (config.northSouth) {
                            seaLevelMutable.setPos(endingPos.getX() + minorAxisSolidOffset, endingPos.getY(), endingPos.getZ());
                        } else {
                            seaLevelMutable.setPos(endingPos.getX(), endingPos.getY(), endingPos.getZ() + minorAxisSolidOffset);
                        }

                        if (helper.func_242894_a(seaLevelMutable).isSolid() && helper.func_242893_a(Heightmap.Type.WORLD_SURFACE, seaLevelMutable.getX(), seaLevelMutable.getZ()) <= seaLevel + 1) {
                            numSolidBlocks++;
                        } else {
                            break;
                        }
                    }
                }

                if (numSolidBlocks < config.numSolidBlocksNeeded) continue;

                // Middle blocks must all be water, starting with the positions designated by minWaterZ and maxWaterZ from the config
                boolean isAllWater = true;
                for (int minorAxisWaterOffset = -config.width / 2; minorAxisWaterOffset <= config.width / 2; minorAxisWaterOffset++) {
                    for (int majorAxisWaterOffset = config.minWaterZ; majorAxisWaterOffset <= config.maxWaterZ; majorAxisWaterOffset++) {
                        // Update mutable position
                        if (config.northSouth) {
                            seaLevelMutable.setPos(startingPos.getX() + minorAxisWaterOffset, seaLevel, startingPos.getZ() + majorAxisWaterOffset);
                        } else {
                            seaLevelMutable.setPos(startingPos.getX() + majorAxisWaterOffset, seaLevel, startingPos.getZ() + minorAxisWaterOffset);
                        }

                        if (helper.func_242894_a(seaLevelMutable).getMaterial() != Material.WATER) {
                            isAllWater = false;
                            break;
                        }
                    }
                }
                if (isAllWater) {
                    // Valid position for bridge!
                    return config.northSouth
                        ? Stream.of(new BlockPos(startingPos.getX() - config.width / 2 - config.widthOffset, seaLevel, startingPos.getZ() + 1))
                        : Stream.of(new BlockPos(startingPos.getX() + 1, seaLevel, startingPos.getZ() + config.width / 2 + config.widthOffset));
                }
            }
        }

        return Stream.empty();
    }
}