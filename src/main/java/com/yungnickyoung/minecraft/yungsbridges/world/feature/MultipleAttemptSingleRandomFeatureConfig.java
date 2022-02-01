package com.yungnickyoung.minecraft.yungsbridges.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@MethodsReturnNonnullByDefault
public class MultipleAttemptSingleRandomFeatureConfig implements FeatureConfiguration {
    public static final Codec<MultipleAttemptSingleRandomFeatureConfig> CODEC = PlacedFeature.LIST_CODEC
        .fieldOf("features").xmap(MultipleAttemptSingleRandomFeatureConfig::new, (config) -> config.features)
        .codec();
    public final List<Supplier<PlacedFeature>> features;

    public MultipleAttemptSingleRandomFeatureConfig(List<Supplier<PlacedFeature>> features) {
        this.features = features;
    }

    @Override
    public Stream<ConfiguredFeature<?, ?>> getFeatures() {
        return this.features.stream().flatMap((placedFeatureSupplier) -> placedFeatureSupplier.get().getFeatures());
    }
}
