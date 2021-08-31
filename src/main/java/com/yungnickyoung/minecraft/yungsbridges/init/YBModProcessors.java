package com.yungnickyoung.minecraft.yungsbridges.init;

import com.yungnickyoung.minecraft.yungsbridges.world.processor.*;

import java.util.ArrayList;
import java.util.List;

public class YBModProcessors {
    /** List of all active processors. */
    public static List<ITemplateFeatureProcessor> PROCESSORS = new ArrayList<>();

    /* Processors */
    public static ITemplateFeatureProcessor LEG_PROCESSOR = register(new DynamicLegProcessor());
    public static ITemplateFeatureProcessor LOG_BIOME_PROCESSOR = register(new OakLogBiomeProcessor());
    public static ITemplateFeatureProcessor STAIR_BIOME_PROCESSOR = register(new OakStairBiomeProcessor());
    public static ITemplateFeatureProcessor FENCE_BIOME_PROCESSOR = register(new OakFenceBiomeProcessor());
    public static ITemplateFeatureProcessor PLANKS_BIOME_PROCESSOR = register(new OakPlanksBiomeProcessor());
    public static ITemplateFeatureProcessor SLAB_BIOME_PROCESSOR = register(new OakSlabProcessor());
    public static ITemplateFeatureProcessor STONE_VARIATION_PROCESSOR = register(new StoneVariationProcessor());

    private static ITemplateFeatureProcessor register(ITemplateFeatureProcessor processor) {
        PROCESSORS.add(processor);
        return processor;
    }
}
