package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import com.yungnickyoung.minecraft.yungsapi.world.BlockSetSelector;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Random;

public class OptionalSlabProcessor implements ITemplateFeatureProcessor {
    private final BlockSetSelector purpurSlabReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.STONE_BRICK_SLAB.defaultBlockState(), .75f);

    private final BlockSetSelector endStoneBrickSlabReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.POLISHED_ANDESITE_SLAB.defaultBlockState(), .75f);

    private final BlockSetSelector darkPrismarineSlabReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.COBBLESTONE_SLAB.defaultBlockState(), .45f)
        .addBlock(Blocks.ANDESITE_SLAB.defaultBlockState(), .45f);

    private final BlockSetSelector prismarineSlabReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), .9f);

    private final BlockSetSelector warpedSlabReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.COBBLESTONE_SLAB.defaultBlockState(), .9f);

    @Override
    public void processTemplate(StructureTemplate template, WorldGenLevel level, Random rand, BlockPos cornerPos, BlockPos centerPos, StructurePlaceSettings placementSettings) {
        Holder<Biome> biome = level.getBiome(cornerPos);

        // Randomly replace purpur slabs with stone brick slabs
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.PURPUR_SLAB)) {
            level.setBlock(blockInfo.pos, getSlabBlockWithState(purpurSlabReplacer.get(rand), blockInfo.state), 2);
        }

        // Randomly replace end stone brick slabs with polished andesite slabs
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.END_STONE_BRICK_SLAB)) {
            level.setBlock(blockInfo.pos, getSlabBlockWithState(endStoneBrickSlabReplacer.get(rand), blockInfo.state), 2);
        }

        // Randomly replace dark prismarine slabs with cobble or andesite slabs
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.DARK_PRISMARINE_SLAB)) {
            level.setBlock(blockInfo.pos, getSlabBlockWithState(darkPrismarineSlabReplacer.get(rand), blockInfo.state), 2);
        }

        // Randomly replace prismarine brick slabs with smooth stone slabs
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.PRISMARINE_BRICK_SLAB)) {
            level.setBlock(blockInfo.pos, getSlabBlockWithState(prismarineSlabReplacer.get(rand), blockInfo.state), 2);
        }

        // Randomly replace crimson slabs w/ biome slab block or air
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.CRIMSON_SLAB)) {
            if (rand.nextFloat() < .5f) {
                level.setBlock(blockInfo.pos, getSlabBlockWithState(getSlabBiomeVariant(biome), blockInfo.state), 2);
            } else {
                level.setBlock(blockInfo.pos, Blocks.AIR.defaultBlockState(), 2);
            }
        }

        // Randomly replace warped slabs with cobblestone slabs
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.WARPED_SLAB)) {
            level.setBlock(blockInfo.pos, getSlabBlockWithState(warpedSlabReplacer.get(rand), blockInfo.state), 2);
        }
    }
}