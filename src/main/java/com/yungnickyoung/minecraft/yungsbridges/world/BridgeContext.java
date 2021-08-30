package com.yungnickyoung.minecraft.yungsbridges.world;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;

/**
 * Caches information during feature generation.
 */
public class BridgeContext {
    private static final ThreadLocal<BridgeContext> CONTEXT = new ThreadLocal<>();

    private WeakReference<Boolean> spawned;

    public BridgeContext() {
        this.spawned = new WeakReference<>(false);
    }

    public boolean hasSpawned() {
        Boolean value = spawned.get();
        return value != null && value;
    }

    public void setSpawned() {
        spawned.clear();
        spawned = new WeakReference<>(true);
    }

    /**
     * Consume the currently held BridgeContext.
     * A null value means the context has already been consumed.
     */
    @Nullable
    public static BridgeContext pop() {
        BridgeContext context = CONTEXT.get();
        CONTEXT.set(null);
        return context;
    }

    /**
     * Peek the currently held BridgeContext without consuming it.
     * A null value means the context has already been consumed.
     */
    @Nullable
    public static BridgeContext get() {
        return CONTEXT.get();
    }

    public static void initialize() {
        CONTEXT.set(new BridgeContext());
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
