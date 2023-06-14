package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import com.yungnickyoung.minecraft.yungsapi.api.world.randomize.BlockStateRandomizer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/**
 * Applies rot (chance of replacing w/ air) to lanterns and torches.
 */
public class LanternRotProcessor implements ITemplateFeatureProcessor {
    private final BlockStateRandomizer lanternSelector = new BlockStateRandomizer(AIR)
        .addBlock(Blocks.LANTERN.defaultBlockState(), .5f);

    private final BlockStateRandomizer torchSelector = new BlockStateRandomizer(AIR)
        .addBlock(Blocks.TORCH.defaultBlockState(), .5f);

    private final BlockStateRandomizer wallTorchSelector = new BlockStateRandomizer(AIR)
        .addBlock(Blocks.WALL_TORCH.defaultBlockState(), .5f);

    @Override
    public void processTemplate(StructureTemplate template, WorldGenLevel level, RandomSource randomSource, BlockPos cornerPos, BlockPos centerPos, StructurePlaceSettings placementSettings) {
        // Random chance to replace some lanterns w/ air
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.LANTERN)) {
            level.setBlock(blockInfo.pos(), getLanternBlockWithState(lanternSelector.get(randomSource), blockInfo.state()), 2);
        }

        // Random chance to replace some torches w/ air
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.TORCH)) {
            level.setBlock(blockInfo.pos(), torchSelector.get(randomSource), 2);
        }

        // Random chance to replace some wall torches w/ air
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.WALL_TORCH)) {
            level.setBlock(blockInfo.pos(), wallTorchSelector.get(randomSource), 2);
        }
    }
}
