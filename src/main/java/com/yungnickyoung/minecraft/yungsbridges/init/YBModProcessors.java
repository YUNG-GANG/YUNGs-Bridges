package com.yungnickyoung.minecraft.yungsbridges.init;

import com.yungnickyoung.minecraft.yungsbridges.world.processor.*;

import java.util.ArrayList;
import java.util.List;

public class YBModProcessors {
    /* List of all active processors. */
    public static List<ITemplateFeatureProcessor> PROCESSORS = new ArrayList<>();

    /* Processors */
    public static ITemplateFeatureProcessor LEG_PROCESSOR = register(new DynamicLegProcessor());
    public static ITemplateFeatureProcessor LOG_PROCESSOR = register(new OakLogProcessor());
    public static ITemplateFeatureProcessor STAIR_PROCESSOR = register(new OakStairProcessor());
    public static ITemplateFeatureProcessor FENCE_PROCESSOR = register(new OakFenceProcessor());
    public static ITemplateFeatureProcessor PLANKS_PROCESSOR = register(new OakPlanksProcessor());
    public static ITemplateFeatureProcessor SLAB_PROCESSOR = register(new OakSlabProcessor());

    private static ITemplateFeatureProcessor register(ITemplateFeatureProcessor processor) {
        PROCESSORS.add(processor);
        return processor;
    }
}
