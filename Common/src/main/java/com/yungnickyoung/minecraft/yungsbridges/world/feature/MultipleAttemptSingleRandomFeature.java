package com.yungnickyoung.minecraft.yungsbridges.world.feature;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Randomly iterates a list of features, attempting to generate each one.
 * Stops upon successful generation of a feature.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MultipleAttemptSingleRandomFeature extends Feature<MultipleAttemptSingleRandomFeatureConfig> {
    public MultipleAttemptSingleRandomFeature() {
        super(MultipleAttemptSingleRandomFeatureConfig.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<MultipleAttemptSingleRandomFeatureConfig> context) {
        List<PlacedFeature> placedFeatureList = context.config().features.stream().map(Holder::value).collect(Collectors.toList());
        int size = placedFeatureList.size();

        while (size > 0) {
            // Get random feature from placedFeatureList
            int i = context.random().nextInt(placedFeatureList.size());
            PlacedFeature placedFeature = placedFeatureList.get(i);

            // If it successfully generates, return true
            if (placedFeature.place(context.level(), context.chunkGenerator(), context.random(), context.origin())) return true;

            // Else, remove it from the placedFeatureList and continue
            placedFeatureList.remove(i);
            size--;
        }

        return false; // No feature generated
    }
}
