package net.minecraft.client.myclient;

public class MyClient {

    public static final String NAME    = "Helix Client";
    public static final String VERSION = "1.0.0";
    public static final String AUTHOR  = "Built by Brad";

    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        initialized = true;
        System.out.println("[Helix Client] v" + VERSION + " loading...");
        ModuleManager.init();
        System.out.println("[Helix Client] Loaded " + ModuleManager.getModules().size() + " modules.");
    }

    public static void onTick() {
        if (!initialized) return;
        ModuleManager.tick();
    }

    public static void onRender(float partialTicks) {
        if (!initialized) return;
        ModuleManager.renderTick(partialTicks);
    }

    public static void onKey(int keyCode) {
        if (!initialized) return;
        ModuleManager.onKey(keyCode);
    }
}
