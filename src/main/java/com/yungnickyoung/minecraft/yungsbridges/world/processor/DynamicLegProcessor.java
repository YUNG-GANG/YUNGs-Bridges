package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import com.yungnickyoung.minecraft.yungsapi.world.BlockSetSelector;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

/**
 * Processor responsible for all dynamic leg generation below bridges.
 */
public class DynamicLegProcessor implements ITemplateFeatureProcessor {
    private final BlockSetSelector stoneBrickSelector = new BlockSetSelector(Blocks.STONE_BRICKS.getDefaultState())
        .addBlock(Blocks.MOSSY_STONE_BRICKS.getDefaultState(), .5f)
        .addBlock(Blocks.CRACKED_STONE_BRICKS.getDefaultState(), .25f);

    private final BlockSetSelector cobblestoneSelector = new BlockSetSelector(Blocks.COBBLESTONE.getDefaultState())
        .addBlock(Blocks.MOSSY_COBBLESTONE.getDefaultState(), .6f);

    @Override
    public void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings) {
        Biome biome = world.getBiome(cornerPos);

        // Yellow stained glass for log legs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.YELLOW_STAINED_GLASS))
            generatePillarDown(world, blockInfo.pos,
                () -> getLogBlockWithState(getLogBiomeVariant(biome), blockInfo.state),
                () -> getLogBlockWithState(getLogBiomeVariant(biome), blockInfo.state));

        // Warped fences for biome fence legs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.WARPED_FENCE))
            generatePillarDown(world, blockInfo.pos,
                () -> getFenceBlockWithState(getFenceBiomeVariant(biome), blockInfo.state),
                () -> getFenceBlockWithState(getFenceBiomeVariant(biome), blockInfo.state));

        // Pink stained glass for stone bricks legs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.PINK_STAINED_GLASS))
            generatePillarDown(world, blockInfo.pos, () -> stoneBrickSelector.get(rand), () -> stoneBrickSelector.get(rand));

        // Blue stained glass for polished andesite legs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.LIGHT_BLUE_STAINED_GLASS))
            generatePillarDown(world, blockInfo.pos, Blocks.POLISHED_ANDESITE::getDefaultState, Blocks.POLISHED_ANDESITE::getDefaultState);

        // Magenta stained glass for cobblestone legs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.MAGENTA_STAINED_GLASS))
            generatePillarDown(world, blockInfo.pos, () -> cobblestoneSelector.get(rand), () -> cobblestoneSelector.get(rand));

        // Gray stained glass for polished andesite replacement w/ stone bricks legs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.GRAY_STAINED_GLASS))
            generatePillarDown(world, blockInfo.pos, Blocks.POLISHED_ANDESITE::getDefaultState, () -> stoneBrickSelector.get(rand));

        // Prismarine walls for cobblestone wall legs
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.PRISMARINE_WALL))
            generatePillarDown(world, blockInfo.pos,
                () -> getWallBlockWithState(Blocks.COBBLESTONE_WALL.getDefaultState(), blockInfo.state),
                () -> getFenceBlockWithState(Blocks.COBBLESTONE_WALL.getDefaultState(), blockInfo.state));
    }
}
