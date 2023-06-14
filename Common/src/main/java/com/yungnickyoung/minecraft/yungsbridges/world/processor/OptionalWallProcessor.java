package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import com.yungnickyoung.minecraft.yungsapi.api.world.randomize.BlockStateRandomizer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class OptionalWallProcessor implements ITemplateFeatureProcessor {
    private final BlockStateRandomizer endStoneBrickWallReplacer = new BlockStateRandomizer(AIR)
        .addBlock(Blocks.STONE_BRICK_WALL.defaultBlockState(), .5f);

    @Override
    public void processTemplate(StructureTemplate template, WorldGenLevel level, RandomSource randomSource, BlockPos cornerPos, BlockPos centerPos, StructurePlaceSettings placementSettings) {
        // Randomly replace end stone brick walls with stone brick walls
        for (StructureTemplate.StructureBlockInfo blockInfo : template.filterBlocks(cornerPos, placementSettings, Blocks.END_STONE_BRICK_WALL)) {
            level.setBlock(blockInfo.pos(), getWallBlockWithState(endStoneBrickWallReplacer.get(randomSource), blockInfo.state()), 2);
        }
    }
}
