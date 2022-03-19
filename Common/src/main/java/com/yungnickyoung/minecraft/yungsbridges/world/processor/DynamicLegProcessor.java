package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import com.yungnickyoung.minecraft.yungsapi.world.BlockStateRandomizer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Random;

/**
 * Processor responsible for all dynamic leg generation below bridges.
 */
public class DynamicLegProcessor implements ITemplateFeatureProcessor {
    private final BlockStateRandomizer stoneBrickSelector = new BlockStateRandomizer(Blocks.STONE_BRICKS.defaultBlockState())
        .addBlock(Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), .5f)
        .addBlock(Blocks.CRACKED_STONE_BRICKS.defaultBlockState(), .25f);

    private final BlockStateRandomizer cobblestoneSelector = new BlockStateRandomizer(Blocks.COBBLESTONE.defaultBlockState())
        .addBlock(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), .6f);

    @Override
    public void processTemplate(StructureTemplate template, WorldGenLevel level, Random rand, BlockPos cornerPos, BlockPos centerPos, StructurePlaceSettings placementSettings) {
        Holder<Biome> biome = level.getBiome(cornerPos);

        // Yellow stained glass for log legs
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.YELLOW_STAINED_GLASS))
            generatePillarDown(level, blockInfo.pos,
                () -> getLogBlockWithState(getLogBiomeVariant(biome), blockInfo.state),
                () -> getLogBlockWithState(getLogBiomeVariant(biome), blockInfo.state));

        // Warped fences for biome fence legs
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.WARPED_FENCE))
            generatePillarDown(level, blockInfo.pos,
                () -> getFenceBlockWithState(getFenceBiomeVariant(biome), blockInfo.state),
                () -> getFenceBlockWithState(getFenceBiomeVariant(biome), blockInfo.state));

        // Pink stained glass for stone bricks legs
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.PINK_STAINED_GLASS))
            generatePillarDown(level, blockInfo.pos, () -> stoneBrickSelector.get(rand), () -> stoneBrickSelector.get(rand));

        // Blue stained glass for polished andesite legs
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.LIGHT_BLUE_STAINED_GLASS))
            generatePillarDown(level, blockInfo.pos, Blocks.POLISHED_ANDESITE::defaultBlockState, Blocks.POLISHED_ANDESITE::defaultBlockState);

        // Magenta stained glass for cobblestone legs
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.MAGENTA_STAINED_GLASS))
            generatePillarDown(level, blockInfo.pos, () -> cobblestoneSelector.get(rand), () -> cobblestoneSelector.get(rand));

        // Gray stained glass for polished andesite replacement w/ stone bricks legs
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.GRAY_STAINED_GLASS))
            generatePillarDown(level, blockInfo.pos, Blocks.POLISHED_ANDESITE::defaultBlockState, () -> stoneBrickSelector.get(rand));

        // Prismarine walls for cobblestone wall legs
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.PRISMARINE_WALL))
            generatePillarDown(level, blockInfo.pos,
                () -> getWallBlockWithState(Blocks.COBBLESTONE_WALL.defaultBlockState(), blockInfo.state),
                () -> getFenceBlockWithState(Blocks.COBBLESTONE_WALL.defaultBlockState(), blockInfo.state));
    }
}
