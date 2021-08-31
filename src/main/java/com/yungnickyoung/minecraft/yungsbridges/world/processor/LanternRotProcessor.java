package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

public class LanternRotProcessor implements ITemplateFeatureProcessor {
    @Override
    public void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings) {
        // Random chance to replace some lanterns w/ air
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.LANTERN)) {
            if (rand.nextFloat() < .5f) {
                world.setBlockState(blockInfo.pos, Blocks.AIR.getDefaultState(), 2);
            }
        }
    }
}
