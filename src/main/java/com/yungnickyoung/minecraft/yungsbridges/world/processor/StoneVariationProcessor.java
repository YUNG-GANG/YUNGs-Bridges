package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
                world.setBlockState(blockInfo.pos, Blocks.MOSSY_STONE_BRICK_SLAB.getDefaultState(), 2);
            }
        }

        // Add variation to stone brick walls
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.STONE_BRICK_WALL)) {
            float f = rand.nextFloat();
            if (f < .5f) {
                world.setBlockState(blockInfo.pos, Blocks.MOSSY_STONE_BRICK_WALL.getDefaultState(), 2);
            }
        }

        // Add variation to cobblestone
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.COBBLESTONE)) {
            float f = rand.nextFloat();
            if (f < .5f) {
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