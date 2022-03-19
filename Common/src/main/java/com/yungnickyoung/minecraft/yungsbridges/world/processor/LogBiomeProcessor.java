package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Random;

/**
 * Processor responsible for replacing logs based on biome.
 * Also randomly replaces some logs with air to simulate rot.
 */
public class LogBiomeProcessor implements ITemplateFeatureProcessor {
    @Override
    public void processTemplate(StructureTemplate template, WorldGenLevel level, Random rand, BlockPos cornerPos, BlockPos centerPos, StructurePlaceSettings placementSettings) {
        Holder<Biome> biome = level.getBiome(cornerPos);

        // Replace wooden logs for biome variants
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.OAK_LOG)) {
            level.setBlock(blockInfo.pos, getLogBlockWithState(getLogBiomeVariant(biome), blockInfo.state), 2);
        }
    }
}
