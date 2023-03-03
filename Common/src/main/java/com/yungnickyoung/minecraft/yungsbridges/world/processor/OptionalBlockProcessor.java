package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import com.yungnickyoung.minecraft.yungsapi.api.world.randomize.BlockStateRandomizer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class OptionalBlockProcessor implements ITemplateFeatureProcessor {
    private final BlockStateRandomizer brownStainedGlassReplacer = new BlockStateRandomizer(AIR)
        .addBlock(Blocks.STONE_BRICKS.defaultBlockState(), .1875f)
        .addBlock(Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), .375f)
        .addBlock(Blocks.CRACKED_STONE_BRICKS.defaultBlockState(), .1875f);

    private final BlockStateRandomizer blueStainedGlassReplacer = new BlockStateRandomizer(AIR)
        .addBlock(Blocks.CHISELED_STONE_BRICKS.defaultBlockState(), .75f);

    private final BlockStateRandomizer redStainedGlassReplacer = new BlockStateRandomizer(AIR)
        .addBlock(Blocks.COBBLESTONE.defaultBlockState(), .75f);

    private final BlockStateRandomizer endStoneBricksReplacer = new BlockStateRandomizer(AIR)
        .addBlock(Blocks.POLISHED_ANDESITE.defaultBlockState(), .75f);

    private final BlockStateRandomizer darkPrismarineReplacer = new BlockStateRandomizer(AIR)
        .addBlock(Blocks.COBBLESTONE.defaultBlockState(), .375f)
        .addBlock(Blocks.ANDESITE.defaultBlockState(), .375f);

    @Override
    public void processTemplate(StructureTemplate template, WorldGenLevel level, RandomSource randomSource, BlockPos cornerPos, BlockPos centerPos, StructurePlaceSettings placementSettings) {
        Holder<Biome> biome = level.getBiome(cornerPos);

        // Randomly replace brown stained glass with a stone brick variant
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.BROWN_STAINED_GLASS)) {
            level.setBlock(blockInfo.pos, brownStainedGlassReplacer.get(randomSource), 2);
        }

        // Randomly replace blue stained glass with chiseled stone bricks
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.BLUE_STAINED_GLASS)) {
            level.setBlock(blockInfo.pos, blueStainedGlassReplacer.get(randomSource), 2);
        }

        // Randomly replace red stained glass with cobblestone
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.RED_STAINED_GLASS)) {
            level.setBlock(blockInfo.pos, redStainedGlassReplacer.get(randomSource), 2);
        }

        // Randomly replace end stone bricks with polished andesite
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.END_STONE_BRICKS)) {
            level.setBlock(blockInfo.pos, endStoneBricksReplacer.get(randomSource), 2);
        }

        // Randomly replace dark prismarine with cobble or andesite
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.DARK_PRISMARINE)) {
            level.setBlock(blockInfo.pos, darkPrismarineReplacer.get(randomSource), 2);
        }

        // Randomly replace lime stained glass w/ log block or air
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.LIME_STAINED_GLASS)) {
            if (randomSource.nextFloat() < .5f) {
                level.setBlock(blockInfo.pos, getLogBlockWithState(getLogBiomeVariant(biome), blockInfo.state), 2);
            } else {
                level.setBlock(blockInfo.pos, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }
}
