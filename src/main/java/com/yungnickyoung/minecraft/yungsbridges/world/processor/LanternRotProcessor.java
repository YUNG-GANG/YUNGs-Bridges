package com.yungnickyoung.minecraft.yungsbridges.world.processor;

import com.yungnickyoung.minecraft.yungsapi.world.BlockSetSelector;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

/**
 * Applies rot (chance of replacing w/ air) to lanterns and torches.
 */
public class LanternRotProcessor implements ITemplateFeatureProcessor {
    private final BlockSetSelector lanternSelector = new BlockSetSelector(AIR)
        .addBlock(Blocks.LANTERN.getDefaultState(), .5f);

    private final BlockSetSelector torchSelector = new BlockSetSelector(AIR)
        .addBlock(Blocks.TORCH.getDefaultState(), .5f);

    private final BlockSetSelector wallTorchSelector = new BlockSetSelector(AIR)
        .addBlock(Blocks.WALL_TORCH.getDefaultState(), .5f);

    @Override
    public void processTemplate(Template template, ISeedReader world, Random rand, BlockPos cornerPos, PlacementSettings placementSettings) {
        // Random chance to replace some lanterns w/ air
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.LANTERN)) {
            world.setBlockState(blockInfo.pos, getLanternBlockWithState(lanternSelector.get(rand), blockInfo.state), 2);
        }

        // Random chance to replace some torches w/ air
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.TORCH)) {
            world.setBlockState(blockInfo.pos, torchSelector.get(rand), 2);
        }

        // Random chance to replace some wall torches w/ air
        for (Template.BlockInfo blockInfo : template.func_215381_a(cornerPos, placementSettings, Blocks.WALL_TORCH)) {
            world.setBlockState(blockInfo.pos, wallTorchSelector.get(rand), 2);
        }
    }
}
