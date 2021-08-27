package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

public class OakStairProcessor implements ITemplateFeatureProcessor {
    @Override
    public void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings) {
        Biome biome = world.getBiome(cornerPos);

        // Replace wooden log for biome variants
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.OAK_STAIRS)) {
            world.setBlockState(
                blockInfo.pos,
                getStairVariant(biome)
                    .with(StairsBlock.FACING, blockInfo.state.get(StairsBlock.FACING))
                    .with(StairsBlock.HALF, blockInfo.state.get(StairsBlock.HALF))
                    .with(StairsBlock.SHAPE, blockInfo.state.get(StairsBlock.SHAPE))
                    .with(StairsBlock.WATERLOGGED, blockInfo.state.get(StairsBlock.WATERLOGGED)),
                2);
        }
    }

    private BlockState getStairVariant(Biome biome) {
        switch(biome.getCategory()) {
            case MESA: return Blocks.DARK_OAK_STAIRS.getDefaultState();
            case TAIGA: return Blocks.SPRUCE_STAIRS.getDefaultState();
            case JUNGLE: return Blocks.JUNGLE_STAIRS.getDefaultState();
            case SAVANNA: return Blocks.ACACIA_STAIRS.getDefaultState();
            default: return Blocks.OAK_STAIRS.getDefaultState();
        }
    }
}

