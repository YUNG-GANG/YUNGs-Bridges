package com.yungnickyoung.minecraft.yungsbridges.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungsbridges.YungsBridges;
import com.yungnickyoung.minecraft.yungsbridges.world.processor.ITemplateFeatureProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Convenience class for easily generating features from structure NBT.
 * Includes functionality for applying additional processing after initial generation,
 * similar to Jigsaw structures.
 */
public abstract class AbstractTemplateFeature<C extends IFeatureConfig> extends Feature<C> {
    protected List<ITemplateFeatureProcessor> processors;

    public AbstractTemplateFeature(Codec<C> codec) {
        super(codec);
        this.processors = useProcessors();
    }

    /**
     * Generates the template feature with default placement settings and applies processors.
     * @param id ID of this template feature (i.e. namespaced path to the structure NBT)
     * @param world ISeedReader
     * @param rand Random
     * @param pos The position to generate the feature at. This will be the corner of the feature.
     * @return The generated Template
     */
    protected Template createTemplate(ResourceLocation id, ISeedReader world, Random rand, BlockPos pos) {
        return createTemplateWithPlacement(id, world, rand, pos, new PlacementSettings());
    }

    /**
     * Generates the template feature and applies processors.
     * @param id ID of this template feature (i.e. namespaced path to the structure NBT)
     * @param world ISeedReader
     * @param rand Random
     * @param pos The position to generate the feature at. This will be the corner of the feature.
     * @param placement Placement settings for the feature
     * @return The generated Template
     */
    protected Template createTemplateWithPlacement(
        ResourceLocation id,
        ISeedReader world,
        Random rand,
        BlockPos pos,
        PlacementSettings placement
    ) {
        Template template = world.getWorld().getStructureTemplateManager().getTemplate(id);

        if (template == null) { // Unsuccessful creation. Name is probably invalid.
            YungsBridges.LOGGER.warn("Failed to create invalid feature {}", id);
            return null;
        }

        // Create & place template
        template.func_237144_a_(world, pos, placement, rand);

        // Additional optional processing
        processors.forEach(processor -> processor.processTemplate(template, world, rand, pos, placement));

        return template;
    }

    /**
     * Attaches specified TemplateFeatureProcessors to this feature.
     * Override this method to return whatever processors you want this feature to have.
     */
    protected List<ITemplateFeatureProcessor> useProcessors() {
        return new ArrayList<>();
    }
}
