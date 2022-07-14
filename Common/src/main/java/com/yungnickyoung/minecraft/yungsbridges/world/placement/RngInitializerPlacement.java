package com.yungnickyoung.minecraft.yungsbridges.world.placement;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungsbridges.module.PlacementModifierTypeModule;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.stream.Stream;

/**
 * Properly initializes this placement's Random seed (which MC doesn't do on its own)
 * to maximize variability in feature placement.
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class RngInitializerPlacement extends PlacementModifier {
    private static final RngInitializerPlacement INSTANCE = new RngInitializerPlacement();
    public static final Codec<RngInitializerPlacement> CODEC = Codec.unit(() -> INSTANCE);

    public static RngInitializerPlacement randomized() {
        return INSTANCE;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext placementContext, RandomSource randomSource, BlockPos blockPos) {
        long a = randomSource.nextLong() | 1L;
        long b = randomSource.nextLong() | 1L;
        randomSource.setSeed(((blockPos.getX() * a * 951873395712L + 12132586) * (blockPos.getZ() * b * 132899567841L + 9789717)) ^ 313281234);
        return Stream.of(blockPos);
    }

    @Override
    public PlacementModifierType<?> type() {
        return PlacementModifierTypeModule.RNG_INITIALIZER_PLACEMENT;
    }
}
