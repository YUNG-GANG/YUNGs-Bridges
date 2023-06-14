package com.yungnickyoung.minecraft.yungsbridges.world.feature;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.yungsbridges.YungsBridgesCommon;
import com.yungnickyoung.minecraft.yungsbridges.module.FeatureProcessorModule;
import com.yungnickyoung.minecraft.yungsbridges.services.Services;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.config.BridgeFeatureConfig;
import com.yungnickyoung.minecraft.yungsbridges.world.processor.ITemplateFeatureProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.List;

public class BridgeFeature extends AbstractTemplateFeature<BridgeFeatureConfig> {
    /**
     * List of processors this template feature will use right after generation.
     */
    private static final List<ITemplateFeatureProcessor> PROCESSORS = Lists.newArrayList(
            FeatureProcessorModule.LEG_PROCESSOR,
            FeatureProcessorModule.LOG_BIOME_PROCESSOR,
            FeatureProcessorModule.STAIR_BIOME_PROCESSOR,
            FeatureProcessorModule.PLANKS_BIOME_PROCESSOR,
            FeatureProcessorModule.SLAB_BIOME_PROCESSOR,
            FeatureProcessorModule.STONE_VARIATION_PROCESSOR,
            FeatureProcessorModule.LANTERN_ROT_PROCESSOR,
            FeatureProcessorModule.OPTIONAL_WALL_PROCESSOR,
            FeatureProcessorModule.OPTIONAL_STONE_BRICK_PROCESSOR,
            FeatureProcessorModule.OPTIONAL_SLAB_PROCESSOR,
            FeatureProcessorModule.OPTIONAL_STAIR_PROCESSOR,
            FeatureProcessorModule.FENCE_BIOME_PROCESSOR
    );

    public BridgeFeature() {
        super(BridgeFeatureConfig.CODEC);
    }

    /**
     * We override this method to supply the processors specific to this template feature.
     */
    @Override
    protected List<ITemplateFeatureProcessor> useProcessors() {
        return PROCESSORS;
    }

    @Override
    public boolean place(FeaturePlaceContext<BridgeFeatureConfig> context) {
        BlockPos.MutableBlockPos startPos = context.origin().mutable();
        startPos.setY(context.level().getSeaLevel()); // Starting pos will be center of bridge

        // Generate the feature. Implicitly applies processors after generation.
        StructurePlaceSettings placementSettings = new StructurePlaceSettings();
        if (!context.config().isZAxis) placementSettings.setRotation(Rotation.COUNTERCLOCKWISE_90);
        StructureTemplate template = this.createTemplateWithPlacement(context.config().id, context.level(), context.random(), startPos, placementSettings);

        if (Services.PLATFORM.isDevelopmentEnvironment()) {
            YungsBridgesCommon.LOGGER.info("Bridge at {} {} {} - {}, {}", context.origin().getX(), context.origin().getY(), context.origin().getZ(), context.config().id, context.config().isZAxis);
        }

        return template != null;
    }
}
