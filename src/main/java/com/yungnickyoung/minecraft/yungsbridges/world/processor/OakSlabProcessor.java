package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

/**
 * Processor responsible for replacing oak slabs based on biome.
 */
public class OakSlabProcessor implements ITemplateFeatureProcessor {
    @Override
    public void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings) {
        Biome biome = world.getBiome(cornerPos);

        // Replace wooden slabs for biome variants
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.OAK_SLAB)) {
            world.setBlockState(
                blockInfo.pos,
                getSlabVariant(biome)
                    .with(SlabBlock.TYPE, blockInfo.state.get(SlabBlock.TYPE))
                    .with(SlabBlock.WATERLOGGED, blockInfo.state.get(SlabBlock.WATERLOGGED)),
                2);
        }
    }

    private BlockState getSlabVariant(Biome biome) {
        switch(biome.getCategory()) {
            case MESA: return Blocks.DARK_OAK_SLAB.getDefaultState();
            case TAIGA: return Blocks.SPRUCE_SLAB.getDefaultState();
            case JUNGLE: return Blocks.JUNGLE_SLAB.getDefaultState();
            case SAVANNA: return Blocks.ACACIA_SLAB.getDefaultState();
            default: return Blocks.OAK_SLAB.getDefaultState();
        }
    }
}
