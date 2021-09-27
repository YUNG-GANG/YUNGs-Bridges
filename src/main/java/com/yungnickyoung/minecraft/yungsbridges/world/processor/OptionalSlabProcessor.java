package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import com.yungnickyoung.minecraft.yungsapi.world.BlockSetSelector;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

public class OptionalSlabProcessor implements ITemplateFeatureProcessor {
    private final BlockSetSelector purpurSlabReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.STONE_BRICK_SLAB.getDefaultState(), .75f);

    private final BlockSetSelector endStoneBrickSlabReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.POLISHED_ANDESITE_SLAB.getDefaultState(), .75f);

    private final BlockSetSelector darkPrismarineSlabReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.COBBLESTONE_SLAB.getDefaultState(), .45f)
        .addBlock(Blocks.ANDESITE_SLAB.getDefaultState(), .45f);

    private final BlockSetSelector prismarineSlabReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.SMOOTH_STONE_SLAB.getDefaultState(), .9f);

    private final BlockSetSelector warpedSlabReplacer = new BlockSetSelector(AIR)
        .addBlock(Blocks.COBBLESTONE_SLAB.getDefaultState(), .9f);

    @Override
    public void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings) {
        Biome biome = world.getBiome(cornerPos);

        // Randomly replace purpur slabs with stone brick slabs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.PURPUR_SLAB)) {
            world.setBlockState(blockInfo.pos, getSlabBlockWithState(purpurSlabReplacer.get(rand), blockInfo.state), 2);
        }

        // Randomly replace end stone brick slabs with polished andesite slabs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.END_STONE_BRICK_SLAB)) {
            world.setBlockState(blockInfo.pos, getSlabBlockWithState(endStoneBrickSlabReplacer.get(rand), blockInfo.state), 2);
        }

        // Randomly replace dark prismarine slabs with cobble or andesite slabs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.DARK_PRISMARINE_SLAB)) {
            world.setBlockState(blockInfo.pos, getSlabBlockWithState(darkPrismarineSlabReplacer.get(rand), blockInfo.state), 2);
        }

        // Randomly replace prismarine brick slabs with smooth stone slabs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.PRISMARINE_BRICK_SLAB)) {
            world.setBlockState(blockInfo.pos, getSlabBlockWithState(prismarineSlabReplacer.get(rand), blockInfo.state), 2);
        }

        // Randomly replace crimson slabs w/ biome slab block or air
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.CRIMSON_SLAB)) {
            if (rand.nextFloat() < .5f) {
                world.setBlockState(blockInfo.pos, getSlabBlockWithState(getSlabBiomeVariant(biome), blockInfo.state), 2);
            } else {
                world.setBlockState(blockInfo.pos, Blocks.AIR.getDefaultState(), 2);
            }
        }

        // Randomly replace warped slabs with cobblestone slabs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.WARPED_SLAB)) {
            world.setBlockState(blockInfo.pos, getSlabBlockWithState(warpedSlabReplacer.get(rand), blockInfo.state), 2);
        }
    }
}