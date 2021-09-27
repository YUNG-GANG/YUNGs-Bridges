package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import com.yungnickyoung.minecraft.yungsapi.world.BlockSetSelector;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

public class OptionalBlockProcessor implements ITemplateFeatureProcessor {
    private final BlockSetSelector brownStainedGlassReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.STONE_BRICKS.getDefaultState(), .1875f)
        .addBlock(Blocks.MOSSY_STONE_BRICKS.getDefaultState(), .375f)
        .addBlock(Blocks.CRACKED_STONE_BRICKS.getDefaultState(), .1875f);

    private final BlockSetSelector blueStainedGlassReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.CHISELED_STONE_BRICKS.getDefaultState(), .75f);

    private final BlockSetSelector redStainedGlassReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.COBBLESTONE.getDefaultState(), .75f);

    private final BlockSetSelector endStoneBricksReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.POLISHED_ANDESITE.getDefaultState(), .75f);

    private final BlockSetSelector darkPrismarineReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.COBBLESTONE.getDefaultState(), .375f)
        .addBlock(Blocks.ANDESITE.getDefaultState(), .375f);

    @Override
    public void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings) {
        Biome biome = world.getBiome(cornerPos);

        // Randomly replace brown stained glass with a stone brick variant
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.BROWN_STAINED_GLASS)) {
            world.setBlockState(blockInfo.pos, brownStainedGlassReplacer.get(rand), 2);
        }

        // Randomly replace blue stained glass with chiseled stone bricks
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.BLUE_STAINED_GLASS)) {
            world.setBlockState(blockInfo.pos, blueStainedGlassReplacer.get(rand), 2);
        }

        // Randomly replace red stained glass with cobblestone
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.RED_STAINED_GLASS)) {
            world.setBlockState(blockInfo.pos, redStainedGlassReplacer.get(rand), 2);
        }

        // Randomly replace end stone bricks with polished andesite
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.END_STONE_BRICKS)) {
            world.setBlockState(blockInfo.pos, endStoneBricksReplacer.get(rand), 2);
        }

        // Randomly replace dark prismarine with cobble or andesite
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.DARK_PRISMARINE)) {
            world.setBlockState(blockInfo.pos, darkPrismarineReplacer.get(rand), 2);
        }

        // Randomly replace lime stained glass w/ log block or air
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.LIME_STAINED_GLASS)) {
            if (rand.nextFloat() < .5f) {
                world.setBlockState(blockInfo.pos, getLogBlockWithState(getLogBiomeVariant(biome), blockInfo.state), 2);
            } else {
                world.setBlockState(blockInfo.pos, Blocks.AIR.getDefaultState(), 2);
            }
        }
    }
}
