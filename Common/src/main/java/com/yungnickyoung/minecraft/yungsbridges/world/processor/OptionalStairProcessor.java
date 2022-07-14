package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import com.yungnickyoung.minecraft.yungsapi.world.BlockStateRandomizer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class OptionalStairProcessor implements ITemplateFeatureProcessor {
    private final BlockStateRandomizer purpurStairsReplacer = new BlockStateRandomizer(AIR)
        .addBlock(Blocks.STONE_BRICK_STAIRS.defaultBlockState(), .75f);

    private final BlockStateRandomizer darkPrismarineStairsReplacer = new BlockStateRandomizer(AIR)
        .addBlock(Blocks.COBBLESTONE_STAIRS.defaultBlockState(), .375f)
        .addBlock(Blocks.ANDESITE_STAIRS.defaultBlockState(), .375f);

    private final BlockStateRandomizer warpedStairsReplacer = new BlockStateRandomizer(AIR)
        .addBlock(Blocks.COBBLESTONE_STAIRS.defaultBlockState(), .75f);

    @Override
    public void processTemplate(StructureTemplate template, WorldGenLevel level, RandomSource randomSource, BlockPos cornerPos, BlockPos centerPos, StructurePlaceSettings placementSettings) {
        Holder<Biome> biome = level.getBiome(cornerPos);

        // Randomly replace purpur stairs with stone brick stairs
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.PURPUR_STAIRS)) {
            level.setBlock(blockInfo.pos, getStairsBlockWithState(purpurStairsReplacer.get(randomSource), blockInfo.state), 2);
        }

        // Randomly replace dark prismarine stairs with cobble or andesite stairs
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.DARK_PRISMARINE_STAIRS)) {
            level.setBlock(blockInfo.pos, getStairsBlockWithState(darkPrismarineStairsReplacer.get(randomSource), blockInfo.state), 2);
        }

        // Randomly replace warped stairs with cobblestone stairs
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.WARPED_STAIRS)) {
            level.setBlock(blockInfo.pos, getStairsBlockWithState(warpedStairsReplacer.get(randomSource), blockInfo.state), 2);
        }

        // Randomly replace crimson stairs with wood stairs
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.CRIMSON_STAIRS)) {
            level.setBlock(blockInfo.pos, getStairsBlockWithState(getStairsBiomeVariant(biome), blockInfo.state), 2);
        }
    }
}
