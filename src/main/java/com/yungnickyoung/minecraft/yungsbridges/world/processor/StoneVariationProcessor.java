package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import com.yungnickyoung.minecraft.yungsapi.world.BlockSetSelector;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

/**
 * Processor responsible for randomizing stone-based blocks.
 * Adds random variation to each structure it processes.
 */
public class StoneVariationProcessor implements ITemplateFeatureProcessor {
    private final BlockSetSelector stoneBrickSelector = new BlockSetSelector(Blocks.STONE_BRICKS.getDefaultState())
        .addBlock(Blocks.MOSSY_STONE_BRICKS.getDefaultState(), .5f)
        .addBlock(Blocks.CRACKED_STONE_BRICKS.getDefaultState(), .25f);

    private final BlockSetSelector stoneBrickSlabSelector = new BlockSetSelector(Blocks.STONE_BRICK_SLAB.getDefaultState())
        .addBlock(Blocks.MOSSY_STONE_BRICK_SLAB.getDefaultState(), .5f);

    private final BlockSetSelector stoneBrickWallSelector = new BlockSetSelector(Blocks.STONE_BRICK_WALL.getDefaultState())
        .addBlock(Blocks.MOSSY_STONE_BRICK_WALL.getDefaultState(), .5f);

    private final BlockSetSelector stoneBrickStairSelector = new BlockSetSelector(Blocks.STONE_BRICK_STAIRS.getDefaultState())
        .addBlock(Blocks.MOSSY_STONE_BRICK_STAIRS.getDefaultState(), .5f);

    private final BlockSetSelector cobblestoneSelector = new BlockSetSelector(Blocks.COBBLESTONE.getDefaultState())
        .addBlock(Blocks.MOSSY_COBBLESTONE.getDefaultState(), .6f);

    @Override
    public void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings) {
        // Stone brick variation
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.STONE_BRICKS)) {
            world.setBlockState(blockInfo.pos, stoneBrickSelector.get(rand), 2);
        }

        // Stone brick slab variation
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.STONE_BRICK_SLAB)) {
            world.setBlockState(blockInfo.pos, getSlabBlockWithState(stoneBrickSlabSelector.get(rand), blockInfo.state), 2);
        }

        // Stone brick wall variation
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.STONE_BRICK_WALL)) {
            world.setBlockState(blockInfo.pos, getWallBlockWithState(stoneBrickWallSelector.get(rand), blockInfo.state), 2);
        }

        // Stone brick stair variation
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.STONE_BRICK_STAIRS)) {
            world.setBlockState(blockInfo.pos, getStairsBlockWithState(stoneBrickStairSelector.get(rand), blockInfo.state), 2);
        }

        // Cobblestone variation
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.COBBLESTONE)) {
            world.setBlockState(blockInfo.pos, cobblestoneSelector.get(rand), 2);
        }
    }
}