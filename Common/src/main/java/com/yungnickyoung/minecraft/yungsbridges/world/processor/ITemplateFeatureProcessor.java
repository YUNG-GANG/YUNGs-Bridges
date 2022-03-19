package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.Material;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Applies additional processing to a {@link com.yungnickyoung.minecraft.yungsbridges.world.feature.AbstractTemplateFeature}
 * immediately after generation.
 * Includes a variety of functions useful for processing.
 */
public interface ITemplateFeatureProcessor {
    BlockState AIR = Blocks.AIR.defaultBlockState();
    BlockState CAVE_AIR = Blocks.CAVE_AIR.defaultBlockState();

    /**
     * Processes the given template when placing a feature.
     * @param template The feature template to process
     * @param level World instance
     * @param rand Random instance
     * @param cornerPos Position of the lowest x-z corner of the feature
     * @param placementSettings The feature's PlacementSettings
     */
    void processTemplate(StructureTemplate template, WorldGenLevel level, Random rand, BlockPos cornerPos, BlockPos centerPos, StructurePlaceSettings placementSettings);

    /**
     * Generates a pillar vertically down from the given starting position.
     * @param level World instance
     * @param pos Starting position of pillar. The pillar will generate below this position
     * @param replacementBlockSupplier Supplier for the block used to replace the block at the starting position
     * @param legBlockSupplier Supplier for the blocks that will make up the pillar generated
     */
    default void generatePillarDown(WorldGenLevel level, BlockPos pos, Supplier<BlockState> replacementBlockSupplier, Supplier<BlockState> legBlockSupplier) {
        // Replace the starter block itself
        level.setBlock(pos, replacementBlockSupplier.get(), 2);

        // Generate vertical pillar down
        BlockPos.MutableBlockPos mutable = pos.below().mutable();
        BlockState currBlock = level.getBlockState(mutable);
        while (mutable.getY() > 0 && (currBlock.getMaterial() == Material.AIR || currBlock.getMaterial() == Material.WATER || currBlock.getMaterial() == Material.LAVA)) {
            level.setBlock(mutable, legBlockSupplier.get(), 2);
            mutable.move(Direction.DOWN);
            currBlock = level.getBlockState(mutable);
        }
    }

    /**
     * Attaches stair-related BlockState properties to a given BlockState.
     * @param input The BlockState we want properties attached to.
     * @param source The BlockState we want to extract StairBlock-related properties from.
     * @return The input block with StairBlock-related properties attached to it, extracted from the source.
     */
    default BlockState getStairsBlockWithState(BlockState input, BlockState source) {
        if (input.getBlock() instanceof StairBlock) {
            return input
                    .setValue(StairBlock.FACING, source.hasProperty(StairBlock.FACING) ? source.getValue(StairBlock.FACING) : Direction.NORTH)
                    .setValue(StairBlock.HALF, source.hasProperty(StairBlock.HALF) ? source.getValue(StairBlock.HALF) : Half.BOTTOM)
                    .setValue(StairBlock.SHAPE, source.hasProperty(StairBlock.SHAPE) ? source.getValue(StairBlock.SHAPE) : StairsShape.STRAIGHT)
                    .setValue(StairBlock.WATERLOGGED, source.hasProperty(StairBlock.WATERLOGGED) ? source.getValue(StairBlock.WATERLOGGED) : false);
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
                    .setValue(SlabBlock.TYPE, source.hasProperty(SlabBlock.TYPE) ? source.getValue(SlabBlock.TYPE) : SlabType.BOTTOM)
                    .setValue(SlabBlock.WATERLOGGED, source.hasProperty(SlabBlock.WATERLOGGED) ? source.getValue(SlabBlock.WATERLOGGED) : false);
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
                    .setValue(WallBlock.NORTH_WALL, source.hasProperty(WallBlock.NORTH_WALL) ? source.getValue(WallBlock.NORTH_WALL) : WallSide.NONE)
                    .setValue(WallBlock.EAST_WALL, source.hasProperty(WallBlock.EAST_WALL) ? source.getValue(WallBlock.EAST_WALL) : WallSide.NONE)
                    .setValue(WallBlock.SOUTH_WALL, source.hasProperty(WallBlock.SOUTH_WALL) ? source.getValue(WallBlock.SOUTH_WALL) : WallSide.NONE)
                    .setValue(WallBlock.WEST_WALL, source.hasProperty(WallBlock.WEST_WALL) ? source.getValue(WallBlock.WEST_WALL) : WallSide.NONE)
                    .setValue(WallBlock.UP, source.hasProperty(WallBlock.UP) ? source.getValue(WallBlock.UP) : true)
                    .setValue(WallBlock.WATERLOGGED, source.hasProperty(WallBlock.WATERLOGGED) ? source.getValue(WallBlock.WATERLOGGED) : false);
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
                    .setValue(FenceBlock.NORTH, source.hasProperty(FenceBlock.NORTH) ? source.getValue(FenceBlock.NORTH) : false)
                    .setValue(FenceBlock.EAST, source.hasProperty(FenceBlock.EAST) ? source.getValue(FenceBlock.EAST) : false)
                    .setValue(FenceBlock.SOUTH, source.hasProperty(FenceBlock.SOUTH) ? source.getValue(FenceBlock.SOUTH) : false)
                    .setValue(FenceBlock.WEST, source.hasProperty(FenceBlock.WEST) ? source.getValue(FenceBlock.WEST) : false)
                    .setValue(FenceBlock.WATERLOGGED, source.hasProperty(FenceBlock.WATERLOGGED) ? source.getValue(FenceBlock.WATERLOGGED) : false);
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
                    .setValue(RotatedPillarBlock.AXIS, source.hasProperty(RotatedPillarBlock.AXIS) ? source.getValue(RotatedPillarBlock.AXIS) : Direction.Axis.Y);
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
                    .setValue(LanternBlock.HANGING, source.hasProperty(LanternBlock.HANGING) ? source.getValue(LanternBlock.HANGING) : true)
                    .setValue(LanternBlock.WATERLOGGED, source.hasProperty(LanternBlock.WATERLOGGED) ? source.getValue(LanternBlock.WATERLOGGED) : true);
        } else {
            return input;
        }
    }

    /**
     * Returns the default state of the proper fence BlockState for the given biome.
     */
    default BlockState getFenceBiomeVariant(Holder<Biome> biome) {
        switch (Biome.getBiomeCategory(biome)) {
            case MESA: return Blocks.DARK_OAK_FENCE.defaultBlockState();
            case TAIGA: return Blocks.SPRUCE_FENCE.defaultBlockState();
            case JUNGLE: return Blocks.JUNGLE_FENCE.defaultBlockState();
            case SAVANNA: return Blocks.ACACIA_FENCE.defaultBlockState();
            default: return Blocks.OAK_FENCE.defaultBlockState();
        }
    }

    /**
     * Returns the default state of the proper log BlockState for the given biome.
     */
    default BlockState getLogBiomeVariant(Holder<Biome> biome) {
        switch (Biome.getBiomeCategory(biome)) {
            case MESA: return Blocks.DARK_OAK_LOG.defaultBlockState();
            case TAIGA: return Blocks.SPRUCE_LOG.defaultBlockState();
            case JUNGLE: return Blocks.JUNGLE_LOG.defaultBlockState();
            case SAVANNA: return Blocks.ACACIA_LOG.defaultBlockState();
            default: return Blocks.OAK_LOG.defaultBlockState();
        }
    }

    /**
     * Returns the default state of the proper planks BlockState for the given biome.
     */
    default BlockState getPlanksBiomeVariant(Holder<Biome> biome) {
        switch (Biome.getBiomeCategory(biome)) {
            case MESA: return Blocks.DARK_OAK_PLANKS.defaultBlockState();
            case TAIGA: return Blocks.SPRUCE_PLANKS.defaultBlockState();
            case JUNGLE: return Blocks.JUNGLE_PLANKS.defaultBlockState();
            case SAVANNA: return Blocks.ACACIA_PLANKS.defaultBlockState();
            default: return Blocks.OAK_PLANKS.defaultBlockState();
        }
    }

    /**
     * Returns the default state of the proper slab BlockState for the given biome.
     */
    default BlockState getSlabBiomeVariant(Holder<Biome> biome) {
        switch (Biome.getBiomeCategory(biome)) {
            case MESA: return Blocks.DARK_OAK_SLAB.defaultBlockState();
            case TAIGA: return Blocks.SPRUCE_SLAB.defaultBlockState();
            case JUNGLE: return Blocks.JUNGLE_SLAB.defaultBlockState();
            case SAVANNA: return Blocks.ACACIA_SLAB.defaultBlockState();
            default: return Blocks.OAK_SLAB.defaultBlockState();
        }
    }

    /**
     * Returns the default state of the proper stairs BlockState for the given biome.
     */
    default BlockState getStairsBiomeVariant(Holder<Biome> biome) {
        switch (Biome.getBiomeCategory(biome)) {
            case MESA: return Blocks.DARK_OAK_STAIRS.defaultBlockState();
            case TAIGA: return Blocks.SPRUCE_STAIRS.defaultBlockState();
            case JUNGLE: return Blocks.JUNGLE_STAIRS.defaultBlockState();
            case SAVANNA: return Blocks.ACACIA_STAIRS.defaultBlockState();
            default: return Blocks.OAK_STAIRS.defaultBlockState();
        }
    }
}
