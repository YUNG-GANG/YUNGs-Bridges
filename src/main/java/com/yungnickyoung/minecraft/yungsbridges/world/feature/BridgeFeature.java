package com.yungnickyoung.minecraft.yungsbridges.world.feature;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.yungsbridges.YungsBridges;
import com.yungnickyoung.minecraft.yungsbridges.init.YBModProcessors;
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
            YBModProcessors.LEG_PROCESSOR,
            YBModProcessors.LOG_BIOME_PROCESSOR,
            YBModProcessors.STAIR_BIOME_PROCESSOR,
            YBModProcessors.PLANKS_BIOME_PROCESSOR,
            YBModProcessors.SLAB_BIOME_PROCESSOR,
            YBModProcessors.STONE_VARIATION_PROCESSOR,
            YBModProcessors.LANTERN_ROT_PROCESSOR,
            YBModProcessors.OPTIONAL_WALL_PROCESSOR,
            YBModProcessors.OPTIONAL_STONE_BRICK_PROCESSOR,
            YBModProcessors.OPTIONAL_SLAB_PROCESSOR,
            YBModProcessors.OPTIONAL_STAIR_PROCESSOR,
            YBModProcessors.FENCE_BIOME_PROCESSOR
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
        if (!context.config().northSouth) placementSettings.setRotation(Rotation.COUNTERCLOCKWISE_90);
        StructureTemplate template = this.createTemplateWithPlacement(context.config().id, context.level(), context.random(), startPos, placementSettings);

        YungsBridges.LOGGER.info("Bridge at {} {} {}", context.origin().getX(), context.origin().getY(), context.origin().getZ());

        return template != null;
    }
}
