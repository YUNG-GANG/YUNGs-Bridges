package com.yungnickyoung.minecraft.yungsbridges.world.processor;

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
    @Override
    public void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings) {
        // Add variation to stone bricks
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.STONE_BRICKS)) {
            world.setBlockState(blockInfo.pos, getStoneBrickVariant(rand), 2);
        }

        // Add variation to stone brick slabs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.STONE_BRICK_SLAB)) {
            float f = rand.nextFloat();
            if (f < .5f) {
                world.setBlockState(
                    blockInfo.pos,
                    Blocks.MOSSY_STONE_BRICK_SLAB.getDefaultState()
                        .with(SlabBlock.TYPE, blockInfo.state.get(SlabBlock.TYPE))
                        .with(SlabBlock.WATERLOGGED, blockInfo.state.get(SlabBlock.WATERLOGGED)),
                    2);
            }
        }

        // Add variation to stone brick walls
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.STONE_BRICK_WALL)) {
            float f = rand.nextFloat();
            if (f < .5f) {
                world.setBlockState(
                    blockInfo.pos,
                    Blocks.MOSSY_STONE_BRICK_WALL.getDefaultState()
                        .with(WallBlock.WALL_HEIGHT_NORTH, blockInfo.state.get(WallBlock.WALL_HEIGHT_NORTH))
                        .with(WallBlock.WALL_HEIGHT_WEST, blockInfo.state.get(WallBlock.WALL_HEIGHT_WEST))
                        .with(WallBlock.WALL_HEIGHT_EAST, blockInfo.state.get(WallBlock.WALL_HEIGHT_EAST))
                        .with(WallBlock.WALL_HEIGHT_SOUTH, blockInfo.state.get(WallBlock.WALL_HEIGHT_SOUTH))
                        .with(WallBlock.WATERLOGGED, blockInfo.state.get(WallBlock.WATERLOGGED))
                        .with(WallBlock.UP, blockInfo.state.get(WallBlock.UP)),
                    2);
            }
        }

        // Add variation to stone brick stairs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.STONE_BRICK_STAIRS)) {
            float f = rand.nextFloat();
            if (f < .5f) {
                world.setBlockState(
                    blockInfo.pos,
                    Blocks.MOSSY_STONE_BRICK_STAIRS.getDefaultState()
                        .with(StairsBlock.FACING, blockInfo.state.get(StairsBlock.FACING))
                        .with(StairsBlock.HALF, blockInfo.state.get(StairsBlock.HALF))
                        .with(StairsBlock.SHAPE, blockInfo.state.get(StairsBlock.SHAPE))
                        .with(StairsBlock.WATERLOGGED, blockInfo.state.get(StairsBlock.WATERLOGGED)),
                    2);
            }
        }

        // Add variation to cobblestone
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.COBBLESTONE)) {
            float f = rand.nextFloat();
            if (f < .6f) {
                world.setBlockState(blockInfo.pos, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 2);
            }
        }
    }

    private BlockState getStoneBrickVariant(Random rand) {
        float f = rand.nextFloat();
        if (f < .5f) return Blocks.MOSSY_STONE_BRICKS.getDefaultState();
        else if (f < .75f) return Blocks.CRACKED_STONE_BRICKS.getDefaultState();
        else return Blocks.STONE_BRICKS.getDefaultState();
    }
}