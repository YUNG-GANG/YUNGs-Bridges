package com.yungnickyoung.minecraft.yungsbridges.world.feature;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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
        List<Supplier<PlacedFeature>> list = new ArrayList<>(context.config().features);
        int size = list.size();

        while (size > 0) {
            // Get random feature from list
            int i = context.random().nextInt(list.size());
            PlacedFeature placedFeature = list.get(i).get();

            // If it successfully generates, return true
            if (placedFeature.place(context.level(), context.chunkGenerator(), context.random(), context.origin())) return true;

            // Else, remove it from the list and continue
            list.remove(i);
            size--;
        }

        return false; // No feature generated
    }
}
