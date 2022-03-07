package com.yungnickyoung.minecraft.yungsbridges.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MultipleAttemptSingleRandomFeatureConfig implements FeatureConfiguration {
    public static final Codec<MultipleAttemptSingleRandomFeatureConfig> CODEC = PlacedFeature.LIST_CODEC
        .fieldOf("features").xmap(MultipleAttemptSingleRandomFeatureConfig::new, (config) -> config.features)
        .codec();
    public final HolderSet<PlacedFeature> features;

    public MultipleAttemptSingleRandomFeatureConfig(HolderSet<PlacedFeature> features) {
        this.features = features;
    }

    public MultipleAttemptSingleRandomFeatureConfig(List<PlacedFeature> features) {
        this.features = HolderSet.direct(features.stream().map(Holder::direct).collect(Collectors.toList()));
    }

    @Override
    public Stream<ConfiguredFeature<?, ?>> getFeatures() {
        return this.features.stream().flatMap((placedFeatureSupplier) -> placedFeatureSupplier.value().getFeatures());
    }
}
