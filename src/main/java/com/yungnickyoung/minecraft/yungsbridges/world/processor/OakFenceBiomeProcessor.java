package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

/**
 * Processor responsible for replacing oak fences based on biome.
 * Also randomly replaces some fences with air to simulate rot.
 */
public class OakFenceBiomeProcessor implements ITemplateFeatureProcessor {
    @Override
    public void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings) {
        Biome biome = world.getBiome(cornerPos);

        // Replace wooden fence for biome variants
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.OAK_FENCE)) {
            if (rand.nextFloat() < .75f || world.getBlockState(blockInfo.pos.up()).isSolid()) { // Place fence
                BlockState fenceBlock = getFenceVariant(biome, blockInfo.state);

                // Adjust neighboring fences
                BlockPos neighborPos = blockInfo.pos.offset(Direction.NORTH);
                if (!world.getBlockState(neighborPos).isSolid()) {
                    fenceBlock = fenceBlock.with(FenceBlock.NORTH, false);
                }
                world.setBlockState(blockInfo.pos, fenceBlock, 2);
            } else { // Replace fence w/ air
                world.setBlockState(blockInfo.pos, Blocks.AIR.getDefaultState(), 2);
                // Adjust neighboring fences
                BlockPos neighborPos = blockInfo.pos.offset(Direction.NORTH);
                if (world.getBlockState(neighborPos).hasProperty(FenceBlock.SOUTH) && world.getBlockState(neighborPos).get(FenceBlock.SOUTH)) {
                    world.setBlockState(neighborPos, world.getBlockState(neighborPos).with(FenceBlock.SOUTH, false), 2);
                }
            }
        }
    }

    private BlockState getFenceVariant(Biome biome, BlockState blockState) {
        switch(biome.getCategory()) {
            case MESA: return fenceBlockWithProperties(Blocks.DARK_OAK_FENCE.getDefaultState(), blockState);
            case TAIGA: return fenceBlockWithProperties(Blocks.SPRUCE_FENCE.getDefaultState(), blockState);
            case JUNGLE: return fenceBlockWithProperties(Blocks.JUNGLE_FENCE.getDefaultState(), blockState);
            case SAVANNA: return fenceBlockWithProperties(Blocks.ACACIA_FENCE.getDefaultState(), blockState);
            default: return fenceBlockWithProperties(Blocks.OAK_FENCE.getDefaultState(), blockState);
        }
    }

    private BlockState fenceBlockWithProperties(BlockState block, BlockState state) {
        return block.with(FenceBlock.NORTH, state.get(FenceBlock.NORTH))
            .with(FenceBlock.SOUTH, state.get(FenceBlock.SOUTH))
            .with(FenceBlock.EAST, state.get(FenceBlock.EAST))
            .with(FenceBlock.WEST, state.get(FenceBlock.WEST))
            .with(FenceBlock.WATERLOGGED, state.get(FenceBlock.WATERLOGGED));
    }
}

