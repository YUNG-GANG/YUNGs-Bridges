package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import com.yungnickyoung.minecraft.yungsapi.world.BlockSetSelector;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Random;

/**
 * Processor responsible for randomizing stone-based blocks.
 * Adds random variation to each structure it processes.
 */
public class StoneVariationProcessor implements ITemplateFeatureProcessor {
    private final BlockSetSelector stoneBrickSelector = new BlockSetSelector(Blocks.STONE_BRICKS.defaultBlockState())
        .addBlock(Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), .5f)
        .addBlock(Blocks.CRACKED_STONE_BRICKS.defaultBlockState(), .25f);

    private final BlockSetSelector stoneBrickSlabSelector = new BlockSetSelector(Blocks.STONE_BRICK_SLAB.defaultBlockState())
        .addBlock(Blocks.MOSSY_STONE_BRICK_SLAB.defaultBlockState(), .5f);

    private final BlockSetSelector stoneBrickWallSelector = new BlockSetSelector(Blocks.STONE_BRICK_WALL.defaultBlockState())
        .addBlock(Blocks.MOSSY_STONE_BRICK_WALL.defaultBlockState(), .5f);

    private final BlockSetSelector stoneBrickStairSelector = new BlockSetSelector(Blocks.STONE_BRICK_STAIRS.defaultBlockState())
        .addBlock(Blocks.MOSSY_STONE_BRICK_STAIRS.defaultBlockState(), .5f);

    private final BlockSetSelector cobblestoneSelector = new BlockSetSelector(Blocks.COBBLESTONE.defaultBlockState())
        .addBlock(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), .6f);

    @Override
    public void processTemplate(StructureTemplate template, WorldGenLevel level, Random rand, BlockPos cornerPos, BlockPos centerPos, StructurePlaceSettings placementSettings) {
        // Stone brick variation
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.STONE_BRICKS)) {
            level.setBlock(blockInfo.pos, stoneBrickSelector.get(rand), 2);
        }

        // Stone brick slab variation
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.STONE_BRICK_SLAB)) {
            level.setBlock(blockInfo.pos, getSlabBlockWithState(stoneBrickSlabSelector.get(rand), blockInfo.state), 2);
        }

        // Stone brick wall variation
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.STONE_BRICK_WALL)) {
            level.setBlock(blockInfo.pos, getWallBlockWithState(stoneBrickWallSelector.get(rand), blockInfo.state), 2);
        }

        // Stone brick stair variation
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.STONE_BRICK_STAIRS)) {
            level.setBlock(blockInfo.pos, getStairsBlockWithState(stoneBrickStairSelector.get(rand), blockInfo.state), 2);
        }

        // Cobblestone variation
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.COBBLESTONE)) {
            level.setBlock(blockInfo.pos, cobblestoneSelector.get(rand), 2);
        }
    }
}