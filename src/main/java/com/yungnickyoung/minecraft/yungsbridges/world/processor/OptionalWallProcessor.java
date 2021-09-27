package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import com.yungnickyoung.minecraft.yungsapi.world.BlockSetSelector;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

public class OptionalWallProcessor implements ITemplateFeatureProcessor {
    private final BlockSetSelector endStoneBrickWallReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.STONE_BRICK_WALL.getDefaultState(), .5f);

    @Override
    public void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings) {
        // Randomly replace end stone brick walls with stone brick walls
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.END_STONE_BRICK_WALL)) {
            world.setBlockState(blockInfo.pos, getWallBlockWithState(endStoneBrickWallReplacer.get(rand), blockInfo.state), 2);
        }
    }
}
