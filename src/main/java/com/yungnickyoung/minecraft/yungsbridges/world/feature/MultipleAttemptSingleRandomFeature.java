package com.yungnickyoung.minecraft.yungsbridges.world.feature;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, MultipleAttemptSingleRandomFeatureConfig config) {
        List<Supplier<ConfiguredFeature<?, ?>>> list = new ArrayList<>(config.features);
        int size = list.size();

        while (size > 0) {
            // Get random feature from list
            int i = rand.nextInt(list.size());
            ConfiguredFeature<?, ?> configuredFeature = list.get(i).get();

            // If it successfully generates, return true
            if (configuredFeature.generate(reader, generator, rand, pos)) return true;

            // Else, remove it from the list and continue
            list.remove(i);
            size--;
        }

        return false; // No feature generated
    }
}
