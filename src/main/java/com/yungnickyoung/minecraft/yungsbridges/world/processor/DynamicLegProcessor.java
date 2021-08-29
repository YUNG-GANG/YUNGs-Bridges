package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

public class DynamicLegProcessor implements ITemplateFeatureProcessor {
    @Override
    public void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings) {
        Biome biome = world.getBiome(cornerPos);

        // Oak bridges use yellow stained glass for legs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.YELLOW_STAINED_GLASS)) {
            // Always replace stained glass w/ log
            world.setBlockState(blockInfo.pos, getLogVariant(biome, blockInfo.state), 2);

            // Generate vertical pillar down
            BlockPos.Mutable mutable = blockInfo.pos.down().toMutable();
            BlockState currBlock = world.getBlockState(mutable);
            while (mutable.getY() > 0 && (currBlock.getMaterial() == Material.AIR || currBlock.getMaterial() == Material.WATER || currBlock.getMaterial() == Material.LAVA)) {
                world.setBlockState(mutable, getLogVariant(biome, blockInfo.state), 2);
                mutable.move(Direction.DOWN);
                currBlock = world.getBlockState(mutable);
            }
        }

        // Some stone bridges use pink stained glass for stone bricks legs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.PINK_STAINED_GLASS)) {
            // Always replace stained glass w/ stone brick variant
            world.setBlockState(blockInfo.pos, getStoneBrickVariant(rand), 2);

            // Generate vertical pillar down
            BlockPos.Mutable mutable = blockInfo.pos.down().toMutable();
            BlockState currBlock = world.getBlockState(mutable);
            while (mutable.getY() > 0 && (currBlock.getMaterial() == Material.AIR || currBlock.getMaterial() == Material.WATER || currBlock.getMaterial() == Material.LAVA)) {
                world.setBlockState(mutable, getStoneBrickVariant(rand), 2);
                mutable.move(Direction.DOWN);
                currBlock = world.getBlockState(mutable);
            }
        }

        // Some stone bridges use blue stained glass for polished andesite legs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.LIGHT_BLUE_STAINED_GLASS)) {
            // Always replace stained glass w/ stone brick variant
            world.setBlockState(blockInfo.pos, Blocks.POLISHED_ANDESITE.getDefaultState(), 2);

            // Generate vertical pillar down
            BlockPos.Mutable mutable = blockInfo.pos.down().toMutable();
            BlockState currBlock = world.getBlockState(mutable);
            while (mutable.getY() > 0 && (currBlock.getMaterial() == Material.AIR || currBlock.getMaterial() == Material.WATER || currBlock.getMaterial() == Material.LAVA)) {
                world.setBlockState(mutable, Blocks.POLISHED_ANDESITE.getDefaultState(), 2);
                mutable.move(Direction.DOWN);
                currBlock = world.getBlockState(mutable);
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

    private BlockState getStoneBrickVariant(Random rand) {
        float f = rand.nextFloat();
        if (f < .5f) return Blocks.MOSSY_STONE_BRICKS.getDefaultState();
        else if (f < .75f) return Blocks.CRACKED_STONE_BRICKS.getDefaultState();
        else return Blocks.STONE_BRICKS.getDefaultState();
    }
}
