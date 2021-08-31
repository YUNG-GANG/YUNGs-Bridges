package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

/**
 * Processor responsible for replacing oak logs based on biome.
 * Also randomly replaces some logs with air to simulate rot.
 */
public class OakLogBiomeProcessor implements ITemplateFeatureProcessor {
    @Override
    public void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings) {
        Biome biome = world.getBiome(cornerPos);

        // Replace wooden log for biome variants
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.OAK_LOG)) {
            world.setBlockState(blockInfo.pos, getLogVariant(biome, blockInfo.state), 2);
        }

        // Randomly replace lime stained glass w/ log block or air
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.LIME_STAINED_GLASS)) {
            if (rand.nextFloat() < .5f) {
                world.setBlockState(blockInfo.pos, getLogVariant(biome, blockInfo.state), 2);
            } else {
                world.setBlockState(blockInfo.pos, Blocks.AIR.getDefaultState(), 2);
            }
        }
    }

    private BlockState getLogVariant(Biome biome, BlockState blockState) {
        switch(biome.getCategory()) {
            case MESA: return Blocks.DARK_OAK_LOG.getDefaultState().with(RotatedPillarBlock.AXIS, blockState.hasProperty(RotatedPillarBlock.AXIS) ? blockState.get(RotatedPillarBlock.AXIS) : Direction.Axis.Y);
            case TAIGA: return Blocks.SPRUCE_LOG.getDefaultState().with(RotatedPillarBlock.AXIS, blockState.hasProperty(RotatedPillarBlock.AXIS) ? blockState.get(RotatedPillarBlock.AXIS) : Direction.Axis.Y);
            case JUNGLE: return Blocks.JUNGLE_LOG.getDefaultState().with(RotatedPillarBlock.AXIS, blockState.hasProperty(RotatedPillarBlock.AXIS) ? blockState.get(RotatedPillarBlock.AXIS) : Direction.Axis.Y);
            case SAVANNA: return Blocks.ACACIA_LOG.getDefaultState().with(RotatedPillarBlock.AXIS, blockState.hasProperty(RotatedPillarBlock.AXIS) ? blockState.get(RotatedPillarBlock.AXIS) : Direction.Axis.Y);
            default: return Blocks.OAK_LOG.getDefaultState().with(RotatedPillarBlock.AXIS, blockState.hasProperty(RotatedPillarBlock.AXIS) ? blockState.get(RotatedPillarBlock.AXIS) : Direction.Axis.Y);
        }
    }
}