package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

/**
 * Processor responsible for replacing logs based on biome.
 * Also randomly replaces some logs with air to simulate rot.
 */
public class LogBiomeProcessor implements ITemplateFeatureProcessor {
    @Override
    public void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings) {
        Biome biome = world.getBiome(cornerPos);

        // Replace wooden logs for biome variants
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.OAK_LOG)) {
            world.setBlockState(blockInfo.pos, getLogBlockWithState(getLogBiomeVariant(biome), blockInfo.state), 2);
        }
    }
}
