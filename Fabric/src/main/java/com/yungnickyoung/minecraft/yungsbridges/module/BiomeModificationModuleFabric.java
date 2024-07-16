package com.yungnickyoung.minecraft.yungsbridges.module;

import com.yungnickyoung.minecraft.yungsbridges.YungsBridgesCommon;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.GenerationStep;

public class BiomeModificationModuleFabric {
    public static void init() {
        addFeaturesToBiomes();
    }

    private static void addFeaturesToBiomes() {
        BiomeModifications.create(ResourceLocation.fromNamespaceAndPath(YungsBridgesCommon.MOD_ID, "bridge_addition"))
                .add(ModificationPhase.ADDITIONS,
                        context -> context.hasTag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(YungsBridgesCommon.MOD_ID, "has_structure/bridge"))),
                        context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(YungsBridgesCommon.MOD_ID, "bridge_list"))));
    }
}
