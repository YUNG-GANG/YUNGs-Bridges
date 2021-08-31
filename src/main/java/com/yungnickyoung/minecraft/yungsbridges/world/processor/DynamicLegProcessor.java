package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Processor responsible for all dynamic leg generation below bridges.
 */
public class DynamicLegProcessor implements ITemplateFeatureProcessor {
    @Override
    public void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings) {
        Biome biome = world.getBiome(cornerPos);

        // Some wood bridges use yellow stained glass for log legs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.YELLOW_STAINED_GLASS))
            generateLeg(world, blockInfo.pos, () -> getLogVariant(biome, blockInfo.state), () -> getLogVariant(biome, blockInfo.state));

        // Some wood bridges use warped fences for fence legs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.WARPED_FENCE))
            generateLeg(world, blockInfo.pos, () -> getFenceVariant(biome, blockInfo.state), () -> getFenceVariant(biome, blockInfo.state));

        // Some stone bridges use pink stained glass for stone bricks legs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.PINK_STAINED_GLASS))
            generateLeg(world, blockInfo.pos, () -> getStoneBrickVariant(rand), () -> getStoneBrickVariant(rand));

        // Some stone bridges use blue stained glass for polished andesite legs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.LIGHT_BLUE_STAINED_GLASS))
            generateLeg(world, blockInfo.pos, Blocks.POLISHED_ANDESITE::getDefaultState, Blocks.POLISHED_ANDESITE::getDefaultState);

        // Some stone bridges use magenta stained glass for cobblestone legs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.MAGENTA_STAINED_GLASS))
            generateLeg(world, blockInfo.pos, () -> getCobblestoneVariant(rand), () -> getCobblestoneVariant(rand));

        // Some stone bridges use gray stained glass for polished andesite replacement, stone bricks legs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.GRAY_STAINED_GLASS))
            generateLeg(world, blockInfo.pos, Blocks.POLISHED_ANDESITE::getDefaultState, () -> getStoneBrickVariant(rand));
    }

    private void generateLeg(ISeedReader world, BlockPos pos, Supplier<BlockState> replacementBlockSupplier, Supplier<BlockState> legBlockSupplier) {
        // Replace the starter block itself
        world.setBlockState(pos, replacementBlockSupplier.get(), 2);

        // Generate vertical pillar down
        BlockPos.Mutable mutable = pos.down().toMutable();
        BlockState currBlock = world.getBlockState(mutable);
        while (mutable.getY() > 0 && (currBlock.getMaterial() == Material.AIR || currBlock.getMaterial() == Material.WATER || currBlock.getMaterial() == Material.LAVA)) {
            world.setBlockState(mutable, legBlockSupplier.get(), 2);
            mutable.move(Direction.DOWN);
            currBlock = world.getBlockState(mutable);
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

    private BlockState getCobblestoneVariant(Random rand) {
        float f = rand.nextFloat();
        if (f < .6f) return Blocks.MOSSY_COBBLESTONE.getDefaultState();
        return Blocks.COBBLESTONE.getDefaultState();
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
