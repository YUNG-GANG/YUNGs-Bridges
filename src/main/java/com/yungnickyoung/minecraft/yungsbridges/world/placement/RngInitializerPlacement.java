package com.yungnickyoung.minecraft.yungsbridges.world.placement;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungsbridges.init.YBModPlacements;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.Random;
import java.util.stream.Stream;

/**
 * Properly initializes this placement's Random seed (which MC doesn't do on its own)
 * to maximize variability in feature placement.
 */
@MethodsReturnNonnullByDefault
public class RngInitializerPlacement extends PlacementModifier {
    private static final RngInitializerPlacement INSTANCE = new RngInitializerPlacement();
    public static final Codec<RngInitializerPlacement> CODEC = Codec.unit(() -> INSTANCE);

    public static RngInitializerPlacement randomized() {
        return INSTANCE;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext placementContext, Random random, BlockPos blockPos) {
        long a = random.nextLong() | 1L;
        long b = random.nextLong() | 1L;
        random.setSeed(((blockPos.getX() * a * 951873395712L + 12132586) * (blockPos.getZ() * b * 132899567841L + 9789717)) ^ 313281234);
        return Stream.of(blockPos);
    }

    @Override
    public PlacementModifierType<?> type() {
        return YBModPlacements.RNG_INITIALIZER_PLACEMENT;
    }
}
