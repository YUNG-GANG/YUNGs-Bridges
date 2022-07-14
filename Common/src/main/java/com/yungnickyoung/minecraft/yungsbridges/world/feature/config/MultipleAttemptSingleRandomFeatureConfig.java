package com.yungnickyoung.minecraft.yungsbridges.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.stream.Stream;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MultipleAttemptSingleRandomFeatureConfig implements FeatureConfiguration {
    public static final Codec<MultipleAttemptSingleRandomFeatureConfig> CODEC = RecordCodecBuilder.create(builder -> builder
            .group(
                    PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(config -> config.features)
            ).apply(builder, MultipleAttemptSingleRandomFeatureConfig::new));
    public final HolderSet<PlacedFeature> features;

    public MultipleAttemptSingleRandomFeatureConfig(HolderSet<PlacedFeature> features) {
        this.features = features;
    }

    public HolderSet<PlacedFeature> getPlacedFeatures() {
        return this.features;
    }

    @Override
    public Stream<ConfiguredFeature<?, ?>> getFeatures() {
        return this.getPlacedFeatures().stream().flatMap((placedFeatureSupplier) -> placedFeatureSupplier.value().getFeatures());
    }
}
