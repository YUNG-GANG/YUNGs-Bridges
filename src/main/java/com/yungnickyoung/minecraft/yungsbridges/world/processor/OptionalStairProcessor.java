package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import com.yungnickyoung.minecraft.yungsapi.world.BlockSetSelector;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

public class OptionalStairProcessor implements ITemplateFeatureProcessor {
    private final BlockSetSelector purpurStairsReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.STONE_BRICK_STAIRS.getDefaultState(), .75f);

    private final BlockSetSelector darkPrismarineStairsReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.COBBLESTONE_STAIRS.getDefaultState(), .375f)
        .addBlock(Blocks.ANDESITE_STAIRS.getDefaultState(), .375f);

    private final BlockSetSelector warpedStairsReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.COBBLESTONE_STAIRS.getDefaultState(), .75f);

    @Override
    public void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings) {
        Biome biome = world.getBiome(cornerPos);

        // Randomly replace purpur stairs with stone brick stairs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.PURPUR_STAIRS)) {
            world.setBlockState(blockInfo.pos, getStairsBlockWithState(purpurStairsReplacer.get(rand), blockInfo.state), 2);
        }

        // Randomly replace dark prismarine stairs with cobble or andesite stairs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.DARK_PRISMARINE_STAIRS)) {
            world.setBlockState(blockInfo.pos, getStairsBlockWithState(darkPrismarineStairsReplacer.get(rand), blockInfo.state), 2);
        }

        // Randomly replace warped stairs with cobblestone stairs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.WARPED_STAIRS)) {
            world.setBlockState(blockInfo.pos, getStairsBlockWithState(warpedStairsReplacer.get(rand), blockInfo.state), 2);
        }

        // Randomly replace crimson stairs with wood stairs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.CRIMSON_STAIRS)) {
            world.setBlockState(blockInfo.pos, getStairsBlockWithState(getStairsBiomeVariant(biome), blockInfo.state), 2);
        }
    }
}
