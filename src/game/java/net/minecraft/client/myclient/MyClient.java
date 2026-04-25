package net.minecraft.client.myclient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.myclient.gui.HelixMainMenu;

public class MyClient {

    public static final String NAME    = "Helix Client";
    public static final String VERSION = "1.0.0";
    public static final String AUTHOR  = "Built by Brad";

    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        initialized = true;
        System.out.println("[Helix Client] v" + VERSION + " loading...");
        try {
            ModuleManager.init();
            System.out.println("[Helix Client] Loaded " + ModuleManager.getModules().size() + " modules.");
        } catch (Exception e) {
            System.out.println("[Helix Client] ModuleManager failed!");
            e.printStackTrace();
        }
    }

    public static void onTick() {
        if (!initialized) return;
        try { ModuleManager.tick(); } catch (Exception e) { e.printStackTrace(); }
    }

    public static void onRender(float partialTicks) {
        if (!initialized) return;
        try { ModuleManager.renderTick(partialTicks); } catch (Exception e) { e.printStackTrace(); }
    }

    public static void onKey(int keyCode) {
        if (!initialized) return;
        try { ModuleManager.onKey(keyCode); } catch (Exception e) { e.printStackTrace(); }
    }

    public static boolean isInitialized() { return initialized; }
}
