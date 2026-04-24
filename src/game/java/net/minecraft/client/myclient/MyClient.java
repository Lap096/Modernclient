package net.minecraft.client.myclient;

/**
 * MyClient — Main entry point.
 *
 * HOW TO HOOK THIS IN:
 *   In Minecraft.java (or the equivalent EaglercraftX bootstrap),
 *   find the startGame() method and add:
 *
 *       MyClient.init();
 *
 *   In the main game tick (runTick or similar), add:
 *
 *       MyClient.onTick();
 *
 *   In the key-press handler (dispatchKeypresses or similar), add:
 *
 *       MyClient.onKey(keyCode);
 *
 *   In the render loop (renderGameOverlay or EntityRenderer), add:
 *
 *       MyClient.onRender(partialTicks);
 */
public class MyClient {

    public static final String NAME    = "MyClient";
    public static final String VERSION = "1.0.0";
    public static final String AUTHOR  = "you";

    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        initialized = true;

        System.out.println("[" + NAME + "] v" + VERSION + " loading...");

        // Boot all modules
        ModuleManager.init();

        System.out.println("[" + NAME + "] Loaded " +
            ModuleManager.getModules().size() + " modules.");
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
