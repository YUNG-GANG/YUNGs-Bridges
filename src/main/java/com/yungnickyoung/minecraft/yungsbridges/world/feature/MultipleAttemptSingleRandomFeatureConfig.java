package com.yungnickyoung.minecraft.yungsbridges.world.feature;

import com.mojang.serialization.Codec;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MultipleAttemptSingleRandomFeatureConfig implements IFeatureConfig {
    public static final Codec<MultipleAttemptSingleRandomFeatureConfig> CODEC = ConfiguredFeature.field_242764_c
        .fieldOf("features").xmap(MultipleAttemptSingleRandomFeatureConfig::new, (p_236643_0_) -> p_236643_0_.features)
        .codec();
    public final List<Supplier<ConfiguredFeature<?, ?>>> features;

    public MultipleAttemptSingleRandomFeatureConfig(List<Supplier<ConfiguredFeature<?, ?>>> features) {
        this.features = features;
    }

    @Override
    public Stream<ConfiguredFeature<?, ?>> func_241856_an_() {
        return this.features.stream().flatMap((p_242826_0_) -> p_242826_0_.get().func_242768_d());
    }
}
