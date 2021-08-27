package com.yungnickyoung.minecraft.yungsbridges.world.feature.oak;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.yungsbridges.YungsBridges;
import com.yungnickyoung.minecraft.yungsbridges.init.YBModProcessors;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.AbstractTemplateFeature;
import com.yungnickyoung.minecraft.yungsbridges.world.feature.BridgeFeatureConfig;
import com.yungnickyoung.minecraft.yungsbridges.world.processor.ITemplateFeatureProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.Template;

import java.util.List;
import java.util.Random;

public class OakBridgeFeature extends AbstractTemplateFeature<BridgeFeatureConfig> {
    /**
     * List of processors this template feature will use right after generation.
     */
    private static final List<ITemplateFeatureProcessor> PROCESSORS = Lists.newArrayList(
        YBModProcessors.LEG_PROCESSOR,
        YBModProcessors.LOG_PROCESSOR,
        YBModProcessors.STAIR_PROCESSOR,
        YBModProcessors.FENCE_PROCESSOR,
        YBModProcessors.PLANKS_PROCESSOR,
        YBModProcessors.SLAB_PROCESSOR
    );

    public OakBridgeFeature() {
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

        // Determine valid bridge orientation
//        Direction bridgeDirection = null;
//        for (Direction dir : Direction.Plane.HORIZONTAL) {
//            BlockPos startingPosCandidate = startPos.offset(dir, 5);
//            BlockPos endingPosCandidate = startPos.offset(dir.getOpposite(), 5);
//
//            if (world.getBlockState(startingPosCandidate).isSolid() && world.getBlockState(endingPosCandidate).isSolid()) {
//                Vector3i facingVec = endingPosCandidate.subtract(startingPosCandidate);
//                bridgeDirection = Direction.getFacingFromVector(facingVec.getX(), facingVec.getY(), facingVec.getZ());
//                break;
//            }
//        }
//
//        if (bridgeDirection == null) return false;
//
//        // Determine rotation for placement
//        Rotation featureRotation = null;
//        switch (bridgeDirection) {
//            case NORTH:
//                featureRotation = Rotation.NONE;
//                break;
//            case EAST:
//                featureRotation = Rotation.CLOCKWISE_90;
//                break;
//            case SOUTH:
//                featureRotation = Rotation.CLOCKWISE_180;
//                break;
//            case WEST:
//                featureRotation = Rotation.COUNTERCLOCKWISE_90;
//                break;
//        }
//
//        if (featureRotation == null) return false;
//
        // Generate the feature. Implicitly applies processors after generation.
        Template template = this.createTemplate(config.id, world, rand, startPos);

//        world.setBlockState(pos, Blocks.DIAMOND_BLOCK.getDefaultState(), 2);
//        world.setBlockState(pos.offset(bridgeDirection, 5), Blocks.RED_WOOL.getDefaultState(), 2);
//        world.setBlockState(pos.offset(bridgeDirection.getOpposite(), 5), Blocks.LIME_WOOL.getDefaultState(), 2);

        YungsBridges.LOGGER.info("/tp {} {} {}", pos.getX(), pos.getY(), pos.getZ());

        return template != null;
    }
}
