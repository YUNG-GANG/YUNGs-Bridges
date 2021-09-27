package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.SlabType;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Applies additional processing to a {@link com.yungnickyoung.minecraft.yungsbridges.world.feature.AbstractTemplateFeature}
 * immediately after generation.
 * Includes a variety of functions useful for processing.
 */
public interface ITemplateFeatureProcessor {
    BlockState AIR = Blocks.AIR.getDefaultState();
    BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();

    /**
     * Processes the given template when placing a feature.
     * @param template The feature template to process
     * @param world World instance
     * @param rand Random instance
     * @param cornerPos Position of the lowest x-z corner of the feature
     * @param placementSettings The feature's PlacementSettings
     */
    void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings);

    /**
     * Generates a pillar vertically down from the given starting position.
     * @param world World instance
     * @param pos Starting position of pillar. The pillar will generate below this position
     * @param replacementBlockSupplier Supplier for the block used to replace the block at the starting position
     * @param legBlockSupplier Supplier for the blocks that will make up the pillar generated
     */
    default void generatePillarDown(ISeedReader world, BlockPos pos, Supplier<BlockState> replacementBlockSupplier, Supplier<BlockState> legBlockSupplier) {
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

    /**
     * Attaches stair-related BlockState properties to a given BlockState.
     * @param input The BlockState we want properties attached to.
     * @param source The BlockState we want to extract StairBlock-related properties from.
     * @return The input block with StairBlock-related properties attached to it, extracted from the source.
     */
    default BlockState getStairsBlockWithState(BlockState input, BlockState source) {
        if (input.getBlock() instanceof StairsBlock) {
            return input
                .with(StairsBlock.FACING, source.hasProperty(StairsBlock.FACING) ? source.get(StairsBlock.FACING) : Direction.NORTH)
                .with(StairsBlock.HALF, source.hasProperty(StairsBlock.HALF) ? source.get(StairsBlock.HALF) : Half.BOTTOM)
                .with(StairsBlock.SHAPE, source.hasProperty(StairsBlock.SHAPE) ? source.get(StairsBlock.SHAPE) : StairsShape.STRAIGHT)
                .with(StairsBlock.WATERLOGGED, source.hasProperty(StairsBlock.WATERLOGGED) ? source.get(StairsBlock.WATERLOGGED) : false);
        } else {
            return input;
        }
    }

    /**
     * Attaches slab-related BlockState properties to a given BlockState.
     * @param input The BlockState we want properties attached to.
     * @param source The BlockState we want to extract SlabBlock-related properties from.
     * @return The input block with SlabBlock-related properties attached to it, extracted from the source.
     */
    default BlockState getSlabBlockWithState(BlockState input, BlockState source) {
        if (input.getBlock() instanceof SlabBlock) {
            return input
                .with(SlabBlock.TYPE, source.hasProperty(SlabBlock.TYPE) ? source.get(SlabBlock.TYPE) : SlabType.BOTTOM)
                .with(SlabBlock.WATERLOGGED, source.hasProperty(SlabBlock.WATERLOGGED) ? source.get(SlabBlock.WATERLOGGED) : false);
        } else {
            return input;
        }
    }

    /**
     * Attaches wall-related BlockState properties to a given BlockState.
     * @param input The BlockState we want properties attached to.
     * @param source The BlockState we want to extract WallBlock-related properties from.
     * @return The input block with WallBlock-related properties attached to it, extracted from the source.
     */
    default BlockState getWallBlockWithState(BlockState input, BlockState source) {
        if (input.getBlock() instanceof WallBlock && source.getBlock() instanceof WallBlock) {
            return input
                .with(WallBlock.WALL_HEIGHT_NORTH, source.hasProperty(WallBlock.WALL_HEIGHT_NORTH) ? source.get(WallBlock.WALL_HEIGHT_NORTH) : WallHeight.NONE)
                .with(WallBlock.WALL_HEIGHT_EAST, source.hasProperty(WallBlock.WALL_HEIGHT_EAST) ? source.get(WallBlock.WALL_HEIGHT_EAST) : WallHeight.NONE)
                .with(WallBlock.WALL_HEIGHT_SOUTH, source.hasProperty(WallBlock.WALL_HEIGHT_SOUTH) ? source.get(WallBlock.WALL_HEIGHT_SOUTH) : WallHeight.NONE)
                .with(WallBlock.WALL_HEIGHT_WEST, source.hasProperty(WallBlock.WALL_HEIGHT_WEST) ? source.get(WallBlock.WALL_HEIGHT_WEST) : WallHeight.NONE)
                .with(WallBlock.UP, source.hasProperty(WallBlock.UP) ? source.get(WallBlock.UP) : true)
                .with(WallBlock.WATERLOGGED, source.hasProperty(WallBlock.WATERLOGGED) ? source.get(WallBlock.WATERLOGGED) : false);
        } else {
            return input;
        }
    }

    /**
     * Attaches fence-related BlockState properties to a given BlockState.
     * @param input The BlockState we want properties attached to.
     * @param source The BlockState we want to extract FenceBlock-related properties from.
     * @return The input block with FenceBlock-related properties attached to it, extracted from the source.
     */
    default BlockState getFenceBlockWithState(BlockState input, BlockState source) {
        if (input.getBlock() instanceof FenceBlock) {
            return input
                .with(FenceBlock.NORTH, source.hasProperty(FenceBlock.NORTH) ? source.get(FenceBlock.NORTH) : false)
                .with(FenceBlock.EAST, source.hasProperty(FenceBlock.EAST) ? source.get(FenceBlock.EAST) : false)
                .with(FenceBlock.SOUTH, source.hasProperty(FenceBlock.SOUTH) ? source.get(FenceBlock.SOUTH) : false)
                .with(FenceBlock.WEST, source.hasProperty(FenceBlock.WEST) ? source.get(FenceBlock.WEST) : false)
                .with(FenceBlock.WATERLOGGED, source.hasProperty(FenceBlock.WATERLOGGED) ? source.get(FenceBlock.WATERLOGGED) : false);
        } else {
            return input;
        }
    }

    /**
     * Attaches log-related BlockState properties to a given BlockState.
     * @param input The BlockState we want properties attached to.
     * @param source The BlockState we want to extract log-block-related properties from.
     * @return The input block with log-block-related properties attached to it, extracted from the source.
     */
    default BlockState getLogBlockWithState(BlockState input, BlockState source) {
        if (input.getBlock() instanceof RotatedPillarBlock) {
            return input
                .with(RotatedPillarBlock.AXIS, source.hasProperty(RotatedPillarBlock.AXIS) ? source.get(RotatedPillarBlock.AXIS) : Direction.Axis.Y);
        } else {
            return input;
        }
    }

    /**
     * Attaches lantern-related BlockState properties to a given BlockState.
     * @param input The BlockState we want properties attached to.
     * @param source The BlockState we want to extract LanternBlock-related properties from.
     * @return The input block with LanternBlock-related properties attached to it, extracted from the source.
     */
    default BlockState getLanternBlockWithState(BlockState input, BlockState source) {
        if (input.getBlock() instanceof LanternBlock) {
            return input
                .with(LanternBlock.HANGING, source.hasProperty(LanternBlock.HANGING) ? source.get(LanternBlock.HANGING) : true)
                .with(LanternBlock.WATERLOGGED, source.hasProperty(LanternBlock.WATERLOGGED) ? source.get(LanternBlock.WATERLOGGED) : true);
        } else {
            return input;
        }
    }

    /**
     * Returns the default state of the proper fence BlockState for the given biome.
     */
    default BlockState getFenceBiomeVariant(Biome biome) {
        switch (biome.getCategory()) {
            case MESA: return Blocks.DARK_OAK_FENCE.getDefaultState();
            case TAIGA: return Blocks.SPRUCE_FENCE.getDefaultState();
            case JUNGLE: return Blocks.JUNGLE_FENCE.getDefaultState();
            case SAVANNA: return Blocks.ACACIA_FENCE.getDefaultState();
            default: return Blocks.OAK_FENCE.getDefaultState();
        }
    }

    /**
     * Returns the default state of the proper log BlockState for the given biome.
     */
    default BlockState getLogBiomeVariant(Biome biome) {
        switch (biome.getCategory()) {
            case MESA: return Blocks.DARK_OAK_LOG.getDefaultState();
            case TAIGA: return Blocks.SPRUCE_LOG.getDefaultState();
            case JUNGLE: return Blocks.JUNGLE_LOG.getDefaultState();
            case SAVANNA: return Blocks.ACACIA_LOG.getDefaultState();
            default: return Blocks.OAK_LOG.getDefaultState();
        }
    }

    /**
     * Returns the default state of the proper planks BlockState for the given biome.
     */
    default BlockState getPlanksBiomeVariant(Biome biome) {
        switch (biome.getCategory()) {
            case MESA: return Blocks.DARK_OAK_PLANKS.getDefaultState();
            case TAIGA: return Blocks.SPRUCE_PLANKS.getDefaultState();
            case JUNGLE: return Blocks.JUNGLE_PLANKS.getDefaultState();
            case SAVANNA: return Blocks.ACACIA_PLANKS.getDefaultState();
            default: return Blocks.OAK_PLANKS.getDefaultState();
        }
    }

    /**
     * Returns the default state of the proper slab BlockState for the given biome.
     */
    default BlockState getSlabBiomeVariant(Biome biome) {
        switch (biome.getCategory()) {
            case MESA: return Blocks.DARK_OAK_SLAB.getDefaultState();
            case TAIGA: return Blocks.SPRUCE_SLAB.getDefaultState();
            case JUNGLE: return Blocks.JUNGLE_SLAB.getDefaultState();
            case SAVANNA: return Blocks.ACACIA_SLAB.getDefaultState();
            default: return Blocks.OAK_SLAB.getDefaultState();
        }
    }

    /**
     * Returns the default state of the proper stairs BlockState for the given biome.
     */
    default BlockState getStairsBiomeVariant(Biome biome) {
        switch (biome.getCategory()) {
            case MESA: return Blocks.DARK_OAK_STAIRS.getDefaultState();
            case TAIGA: return Blocks.SPRUCE_STAIRS.getDefaultState();
            case JUNGLE: return Blocks.JUNGLE_STAIRS.getDefaultState();
            case SAVANNA: return Blocks.ACACIA_STAIRS.getDefaultState();
            default: return Blocks.OAK_STAIRS.getDefaultState();
        }
    }
}
