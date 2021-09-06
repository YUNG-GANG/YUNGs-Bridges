package com.yungnickyoung.minecraft.yungsbridges.world.feature;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.yungsbridges.YungsBridges;
import com.yungnickyoung.minecraft.yungsbridges.init.YBModProcessors;
import com.yungnickyoung.minecraft.yungsbridges.world.processor.ITemplateFeatureProcessor;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.List;
import java.util.Random;

public class BridgeFeature extends AbstractTemplateFeature<BridgeFeatureConfig> {
    /**
     * List of processors this template feature will use right after generation.
     */
    private static final List<ITemplateFeatureProcessor> PROCESSORS = Lists.newArrayList(
        YBModProcessors.LEG_PROCESSOR,
        YBModProcessors.LOG_BIOME_PROCESSOR,
        YBModProcessors.STAIR_BIOME_PROCESSOR,
        YBModProcessors.FENCE_BIOME_PROCESSOR,
        YBModProcessors.PLANKS_BIOME_PROCESSOR,
        YBModProcessors.SLAB_BIOME_PROCESSOR,
        YBModProcessors.STONE_VARIATION_PROCESSOR,
        YBModProcessors.LANTERN_ROT_PROCESSOR
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
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos pos, BridgeFeatureConfig config) {
        BlockPos.Mutable startPos = pos.toMutable();
        startPos.setY(world.getSeaLevel()); // Starting pos will be center of bridge

        // Generate the feature. Implicitly applies processors after generation.
        PlacementSettings placementSettings = new PlacementSettings();
        if (!config.northSouth) placementSettings.setRotation(Rotation.COUNTERCLOCKWISE_90);
        Template template = this.createTemplateWithPlacement(config.id, world, rand, startPos, placementSettings);

//        world.setBlockState(pos, Blocks.DIAMOND_BLOCK.getDefaultState(), 2);
//        BlockPos endingPos = config.northSouth
//            ? new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1)
//            : new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
//        world.setBlockState(endingPos, Blocks.EMERALD_BLOCK.getDefaultState(), 2);

        YungsBridges.LOGGER.info("/tp {} {} {}", pos.getX(), pos.getY(), pos.getZ());

        return template != null;
    }
}
