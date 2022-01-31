package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Random;

/**
 * Processor responsible for replacing planks based on biome.
 */
public class PlanksBiomeProcessor implements ITemplateFeatureProcessor {
    @Override
    public void processTemplate(StructureTemplate template, WorldGenLevel level, Random rand, BlockPos cornerPos, BlockPos centerPos, StructurePlaceSettings placementSettings) {
        Biome biome = level.getBiome(cornerPos);

        // Replace wooden planks for biome variants
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.OAK_PLANKS)) {
            level.setBlock(blockInfo.pos, getPlanksBiomeVariant(biome), 2);
        }
    }
}
