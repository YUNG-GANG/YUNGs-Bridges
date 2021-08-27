package com.yungnickyoung.minecraft.yungsbridges.init;

import com.yungnickyoung.minecraft.yungsbridges.YungsBridges;
import com.yungnickyoung.minecraft.yungsbridges.world.placement.BridgePlacement;
import com.yungnickyoung.minecraft.yungsbridges.world.placement.BridgePlacementConfig;
import com.yungnickyoung.minecraft.yungsbridges.world.placement.RngInitializerPlacement;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class YBModPlacements {
    /* Registry for deferred registration */
    public static final DeferredRegister<Placement<?>> PLACEMENTS = DeferredRegister.create(ForgeRegistries.DECORATORS, YungsBridges.MOD_ID);

    /* Placements */
    public static final RegistryObject<Placement<NoPlacementConfig>> RNG_INITIALIZER = register("rng_initializer", RngInitializerPlacement::new);
    public static final RegistryObject<Placement<BridgePlacementConfig>> BRIDGE = register("bridge", BridgePlacement::new);

    public static void init() {
        PLACEMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private static <T extends Placement<?>> RegistryObject<T> register(String name, Supplier<T> feature) {
        return PLACEMENTS.register(name, feature);
    }
}
