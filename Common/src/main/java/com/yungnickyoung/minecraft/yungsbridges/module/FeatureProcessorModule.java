package com.yungnickyoung.minecraft.yungsbridges.module;

import com.yungnickyoung.minecraft.yungsbridges.world.processor.*;

import java.util.ArrayList;
import java.util.List;

public class FeatureProcessorModule {
    /** List of all active processors. */
    public static List<ITemplateFeatureProcessor> PROCESSORS = new ArrayList<>();

    /* Processors */
    public static ITemplateFeatureProcessor LEG_PROCESSOR = register(new DynamicLegProcessor());
    public static ITemplateFeatureProcessor LOG_BIOME_PROCESSOR = register(new LogBiomeProcessor());
    public static ITemplateFeatureProcessor STAIR_BIOME_PROCESSOR = register(new StairBiomeProcessor());
    public static ITemplateFeatureProcessor FENCE_BIOME_PROCESSOR = register(new FenceBiomeProcessor());
    public static ITemplateFeatureProcessor PLANKS_BIOME_PROCESSOR = register(new PlanksBiomeProcessor());
    public static ITemplateFeatureProcessor SLAB_BIOME_PROCESSOR = register(new SlabBiomeProcessor());
    public static ITemplateFeatureProcessor STONE_VARIATION_PROCESSOR = register(new StoneVariationProcessor());
    public static ITemplateFeatureProcessor LANTERN_ROT_PROCESSOR = register(new LanternRotProcessor());
    public static ITemplateFeatureProcessor OPTIONAL_WALL_PROCESSOR = register(new OptionalWallProcessor());
    public static ITemplateFeatureProcessor OPTIONAL_STONE_BRICK_PROCESSOR = register(new OptionalBlockProcessor());
    public static ITemplateFeatureProcessor OPTIONAL_SLAB_PROCESSOR = register(new OptionalSlabProcessor());
    public static ITemplateFeatureProcessor OPTIONAL_STAIR_PROCESSOR = register(new OptionalStairProcessor());

    private static ITemplateFeatureProcessor register(ITemplateFeatureProcessor processor) {
        PROCESSORS.add(processor);
        return processor;
    }
}
