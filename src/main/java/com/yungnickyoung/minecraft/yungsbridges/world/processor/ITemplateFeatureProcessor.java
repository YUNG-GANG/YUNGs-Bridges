package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

/**
 * Applies additional processing to a {@link com.yungnickyoung.minecraft.yungsbridges.world.feature.AbstractTemplateFeature}
 * immediately after generation.
 */
public interface ITemplateFeatureProcessor {
    void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings);
}
