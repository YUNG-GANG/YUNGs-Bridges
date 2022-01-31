package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import com.yungnickyoung.minecraft.yungsapi.world.BlockSetSelector;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Random;

/**
 * Applies rot (chance of replacing w/ air) to lanterns and torches.
 */
public class LanternRotProcessor implements ITemplateFeatureProcessor {
    private final BlockSetSelector lanternSelector = new BlockSetSelector(AIR)
        .addBlock(Blocks.LANTERN.defaultBlockState(), .5f);

    private final BlockSetSelector torchSelector = new BlockSetSelector(AIR)
        .addBlock(Blocks.TORCH.defaultBlockState(), .5f);

    private final BlockSetSelector wallTorchSelector = new BlockSetSelector(AIR)
        .addBlock(Blocks.WALL_TORCH.defaultBlockState(), .5f);

    @Override
    public void processTemplate(StructureTemplate template, WorldGenLevel level, Random rand, BlockPos cornerPos, BlockPos centerPos, StructurePlaceSettings placementSettings) {
        // Random chance to replace some lanterns w/ air
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.LANTERN)) {
            level.setBlock(blockInfo.pos, getLanternBlockWithState(lanternSelector.get(rand), blockInfo.state), 2);
        }

        // Random chance to replace some torches w/ air
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.TORCH)) {
            level.setBlock(blockInfo.pos, torchSelector.get(rand), 2);
        }

        // Random chance to replace some wall torches w/ air
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.WALL_TORCH)) {
            level.setBlock(blockInfo.pos, wallTorchSelector.get(rand), 2);
        }
    }
}
