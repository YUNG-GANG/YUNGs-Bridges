package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Random;

/**
 * Processor responsible for replacing fences based on biome.
 * Also randomly replaces some fences with air to simulate rot.
 */
public class FenceBiomeProcessor implements ITemplateFeatureProcessor {
    @Override
    public void processTemplate(StructureTemplate template, WorldGenLevel level, Random rand, BlockPos cornerPos, BlockPos centerPos, StructurePlaceSettings placementSettings) {
        Holder<Biome> biome = level.getBiome(cornerPos);

        // Replace wooden fence for biome variants
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.OAK_FENCE)) {
            if (rand.nextFloat() < .75f || level.getBlockState(blockInfo.pos.above()).canOcclude()) { // Place fence
                BlockState fenceBlock = getFenceBlockWithState(getFenceBiomeVariant(biome), blockInfo.state);

                // Adjust neighboring fences
                if (!level.getBlockState(blockInfo.pos.relative(Direction.NORTH)).canOcclude()) {
                    fenceBlock = fenceBlock.setValue(FenceBlock.NORTH, false);
                }
                if (!level.getBlockState(blockInfo.pos.relative(Direction.WEST)).canOcclude()) {
                    fenceBlock = fenceBlock.setValue(FenceBlock.WEST, false);
                }
                level.setBlock(blockInfo.pos, fenceBlock, 2);
            } else { // Replace fence w/ air
                level.setBlock(blockInfo.pos, Blocks.AIR.defaultBlockState(), 2);
                // Adjust neighboring fences
                BlockPos neighborPos = blockInfo.pos.relative(Direction.NORTH);
                if (level.getBlockState(neighborPos).hasProperty(FenceBlock.SOUTH) && level.getBlockState(neighborPos).getValue(FenceBlock.SOUTH)) {
                    level.setBlock(neighborPos, level.getBlockState(neighborPos).setValue(FenceBlock.SOUTH, false), 2);
                }
                neighborPos = blockInfo.pos.relative(Direction.WEST);
                if (level.getBlockState(neighborPos).hasProperty(FenceBlock.EAST) && level.getBlockState(neighborPos).getValue(FenceBlock.EAST)) {
                    level.setBlock(neighborPos, level.getBlockState(neighborPos).setValue(FenceBlock.EAST, false), 2);
                }
            }
        }
    }
}

