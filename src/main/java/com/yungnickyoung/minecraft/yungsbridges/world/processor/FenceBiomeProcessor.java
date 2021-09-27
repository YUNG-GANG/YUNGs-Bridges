package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

/**
 * Processor responsible for replacing fences based on biome.
 * Also randomly replaces some fences with air to simulate rot.
 */
public class FenceBiomeProcessor implements ITemplateFeatureProcessor {
    @Override
    public void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings) {
        Biome biome = world.getBiome(cornerPos);

        // Replace wooden fence for biome variants
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.OAK_FENCE)) {
            if (rand.nextFloat() < .75f || world.getBlockState(blockInfo.pos.up()).isSolid()) { // Place fence
                BlockState fenceBlock = getFenceBlockWithState(getFenceBiomeVariant(biome), blockInfo.state);

                // Adjust neighboring fences
                if (!world.getBlockState(blockInfo.pos.offset(Direction.NORTH)).isSolid()) {
                    fenceBlock = fenceBlock.with(FenceBlock.NORTH, false);
                }
                if (!world.getBlockState(blockInfo.pos.offset(Direction.WEST)).isSolid()) {
                    fenceBlock = fenceBlock.with(FenceBlock.WEST, false);
                }
                world.setBlockState(blockInfo.pos, fenceBlock, 2);
            } else { // Replace fence w/ air
                world.setBlockState(blockInfo.pos, Blocks.AIR.getDefaultState(), 2);
                // Adjust neighboring fences
                BlockPos neighborPos = blockInfo.pos.offset(Direction.NORTH);
                if (world.getBlockState(neighborPos).hasProperty(FenceBlock.SOUTH) && world.getBlockState(neighborPos).get(FenceBlock.SOUTH)) {
                    world.setBlockState(neighborPos, world.getBlockState(neighborPos).with(FenceBlock.SOUTH, false), 2);
                }
                neighborPos = blockInfo.pos.offset(Direction.WEST);
                if (world.getBlockState(neighborPos).hasProperty(FenceBlock.EAST) && world.getBlockState(neighborPos).get(FenceBlock.EAST)) {
                    world.setBlockState(neighborPos, world.getBlockState(neighborPos).with(FenceBlock.EAST, false), 2);
                }
            }
        }
    }
}

