package com.yungnickyoung.minecraft.yungsbridges.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungsbridges.YungsBridgesCommon;
import com.yungnickyoung.minecraft.yungsbridges.world.processor.ITemplateFeatureProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Convenience class for easily generating features from structure NBT.
 * Includes functionality for applying additional processing after initial generation,
 * similar to Jigsaw structures.
 */
public abstract class AbstractTemplateFeature<C extends FeatureConfiguration> extends Feature<C> {
    protected List<ITemplateFeatureProcessor> processors;

    public AbstractTemplateFeature(Codec<C> codec) {
        super(codec);
        this.processors = useProcessors();
    }

    /**
     * Generates the template feature with default placement settings and applies processors.
     * @param id ID of this template feature (i.e. namespaced path to the structure NBT)
     * @param level WorldGenLevel
     * @param rand Random
     * @param pos The position to generate the feature at. This will be the corner of the feature.
     * @return The generated Template
     */
    protected StructureTemplate createTemplate(ResourceLocation id, WorldGenLevel level, Random rand, BlockPos pos) {
        return createTemplateWithPlacement(id, level, rand, pos, new StructurePlaceSettings());
    }

    /**
     * Generates the template feature and applies processors.
     * @param id ID of this template feature (i.e. namespaced path to the structure NBT)
     * @param level WorldGenLevel
     * @param rand Random
     * @param cornerPos The position to generate the feature at. This will be the corner of the feature.
     * @param placement Placement settings for the feature
     * @return The generated Template
     */
    protected StructureTemplate createTemplateWithPlacement(
        ResourceLocation id,
        WorldGenLevel level,
        Random rand,
        BlockPos cornerPos,
        StructurePlaceSettings placement
    ) {
        Optional<StructureTemplate> templateOptional = level.getLevel().getStructureManager().get(id);

        if (templateOptional.isEmpty()) { // Unsuccessful creation. Name is probably invalid.
            YungsBridgesCommon.LOGGER.warn("Failed to create invalid feature {}", id);
            return null;
        }

        StructureTemplate template = templateOptional.get();


        // Create & place template
        BlockPos centerPos = cornerPos.offset(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
        template.placeInWorld(level, cornerPos, centerPos, placement, rand, 2);

        // Additional optional processing
        processors.forEach(processor -> processor.processTemplate(template, level, rand, cornerPos, centerPos, placement));

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
