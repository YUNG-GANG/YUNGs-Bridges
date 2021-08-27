package com.yungnickyoung.minecraft.yungsbridges.world.placement;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.Placement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;
import java.util.stream.Stream;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BridgePlacement extends Placement<BridgeSizeConfig> {
    public BridgePlacement() {
        super(BridgeSizeConfig.CODEC);
    }

    @Override
    public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, BridgeSizeConfig config, BlockPos pos) {
        // Mutable that always is at sea level
        BlockPos.Mutable seaLevelMutable = pos.toMutable();
        int seaLevel = helper.func_242895_b() - 1;
        seaLevelMutable.setY(seaLevel);

        for (int x = 0; x < 16; x++) {
            for (int startZ = 0; startZ < 16 - (config.size * 2); startZ++) {
                BlockPos startingPos = new BlockPos(pos.getX() + x, seaLevel, pos.getZ() + startZ);
                BlockPos endingPos = new BlockPos(pos.getX() + x, seaLevel, pos.getZ() + startZ + config.size * 2);

                if (helper.func_242894_a(startingPos).isSolid() && helper.func_242894_a(endingPos).isSolid()) {
                    // Middle blocks must all be water
                    boolean isAllWater = true;
                    for (int waterZ = config.minWaterZ; waterZ <= config.maxWaterZ; waterZ++) {
                        seaLevelMutable.setPos(startingPos.getX(), seaLevel, startingPos.getZ() + waterZ);
                        if (helper.func_242894_a(seaLevelMutable).getMaterial() != Material.WATER) {
                            isAllWater = false;
                            break;
                        }
                    }
                    if (isAllWater) {
                        // Valid position for bridge!
                        return Stream.of(new BlockPos(startingPos.getX(), seaLevel, pos.getZ() + startZ + config.size));
                    }
                }
            }
        }

        return Stream.empty();
    }
}