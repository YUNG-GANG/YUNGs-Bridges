package com.yungnickyoung.minecraft.yungsbridges.world.placement;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.yungsbridges.world.BridgeContext;
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
        // Don't generate if we have already generated a bridge for this chunk
        BridgeContext context = BridgeContext.get();
        if (context == null || context.hasSpawned()) {
            return Stream.empty();
        }

        // Mutable that is always at sea level
        BlockPos.Mutable seaLevelMutable = pos.toMutable();
        int seaLevel = helper.func_242895_b() - 1;
        seaLevelMutable.setY(seaLevel);

        // Scan the grid, looking for suitable locations
        for (int x = 1; x < 16; x++) {
            for (int startZ = 0; startZ < 16; startZ++) {
                // Candidate starting position for the bridge
                BlockPos startingPos = new BlockPos(pos.getX() + x, seaLevel, pos.getZ() + startZ);

                // Corresponding ending position for the current candidate starting position.
                BlockPos endingPos = new BlockPos(pos.getX() + x, seaLevel, pos.getZ() + startZ + config.length + 1);

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
                    for (int solidX = 1; solidX <= config.width / 2; solidX++) {
                        int offsetX = direction * solidX;
                        seaLevelMutable.setPos(startingPos.getX() + offsetX, startingPos.getY(), startingPos.getZ());
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
                    for (int solidX = 1; solidX <= config.width / 2; solidX++) {
                        int offsetX = direction * solidX;
                        seaLevelMutable.setPos(endingPos.getX() + offsetX, endingPos.getY(), endingPos.getZ());
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
                for (int waterX = -config.width / 2; waterX <= config.width / 2; waterX++) {
                    for (int waterZ = config.minWaterZ; waterZ <= config.maxWaterZ; waterZ++) {
                        seaLevelMutable.setPos(startingPos.getX() + waterX, seaLevel, startingPos.getZ() + waterZ);
                        if (helper.func_242894_a(seaLevelMutable).getMaterial() != Material.WATER) {
                            isAllWater = false;
                            break;
                        }
                    }
                }
                if (isAllWater) {
                    // Valid position for bridge!
                    context.setSpawned(); // Set context so that no other bridges will spawn in this chunk
                    return Stream.of(new BlockPos(startingPos.getX() - config.width / 2 - config.widthOffset, seaLevel, pos.getZ() + startZ + 1));
                }
            }
        }

        return Stream.empty();
    }
}