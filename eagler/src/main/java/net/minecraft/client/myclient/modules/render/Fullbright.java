package net.minecraft.client.myclient.modules.render;

import net.minecraft.client.myclient.modules.Module;

/**
 * Fullbright — sets gamma to maximum so everything is fully lit.
 * Reverts gamma on disable.
 *
 * Safe: purely a client-side graphics setting.
 */
public class Fullbright extends Module {

    private float savedGamma = 1.0f;

    public Fullbright() {
        super("Fullbright", "Render",
              "Makes everything fully bright", 0);
    }

    @Override
    public void onEnable() {
        savedGamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 10000.0f; // Effectively max brightness
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = savedGamma;
    }

    @Override
    public void onUpdate() {
        // Keep overriding in case other code resets gamma
        if (mc.gameSettings.gammaSetting < 10000.0f) {
            mc.gameSettings.gammaSetting = 10000.0f;
        }
    }
}
