package com.yungnickyoung.minecraft.yungsbridges.mixin;

import com.yungnickyoung.minecraft.yungsbridges.world.BridgeContext;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.structure.StructureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Biome.class)
public class BiomeMixin {
    @Inject(
        method = "Lnet/minecraft/world/biome/Biome;generateFeatures(Lnet/minecraft/world/gen/feature/structure/StructureManager;Lnet/minecraft/world/gen/ChunkGenerator;Lnet/minecraft/world/gen/WorldGenRegion;JLnet/minecraft/util/SharedSeedRandom;Lnet/minecraft/util/math/BlockPos;)V",
        at = @At("HEAD")
    )
    private void initializeBridgeContext(StructureManager structureManager, ChunkGenerator chunkGenerator, WorldGenRegion worldGenRegion, long seed, SharedSeedRandom rand, BlockPos pos, CallbackInfo ci) {
        BridgeContext.initialize();
    }

    @Inject(
        method = "Lnet/minecraft/world/biome/Biome;generateFeatures(Lnet/minecraft/world/gen/feature/structure/StructureManager;Lnet/minecraft/world/gen/ChunkGenerator;Lnet/minecraft/world/gen/WorldGenRegion;JLnet/minecraft/util/SharedSeedRandom;Lnet/minecraft/util/math/BlockPos;)V",
        at = @At("RETURN")
    )
    private void resetBridgeContext(StructureManager structureManager, ChunkGenerator chunkGenerator, WorldGenRegion worldGenRegion, long seed, SharedSeedRandom rand, BlockPos pos, CallbackInfo ci) {
        BridgeContext.clear();
    }
}
