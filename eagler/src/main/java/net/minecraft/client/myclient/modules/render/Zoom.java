package net.minecraft.client.myclient.modules.render;

import net.minecraft.client.myclient.modules.Module;
import org.lwjgl.input.Keyboard;

/**
 * Zoom — hold C to zoom in (reduces FOV).
 * Smooth transition in/out using lerp.
 *
 * Safe: client-side FOV manipulation only.
 */
public class Zoom extends Module {

    private static final int  ZOOM_KEY      = Keyboard.KEY_C;
    private static final float ZOOM_FOV     = 15.0f;  // Zoomed FOV
    private static final float SMOOTH_SPEED = 0.15f;  // Lerp factor per tick

    private float normalFOV   = 70.0f;
    private float currentFOV  = 70.0f;
    private boolean zooming   = false;

    public Zoom() {
        super("Zoom", "Render", "Hold C to zoom in", 0);
        // Always on — it only activates while C is held
        setEnabled(true);
    }

    @Override
    public void onUpdate() {
        if (mc.thePlayer == null || mc.gameSettings == null) return;

        boolean holdingZoom = Keyboard.isKeyDown(ZOOM_KEY)
                              && mc.inGameHasFocus;

        if (holdingZoom && !zooming) {
            // Just started zooming — save the current FOV
            normalFOV = mc.gameSettings.fovSetting;
            zooming   = true;
        } else if (!holdingZoom && zooming) {
            zooming = false;
        }

        float targetFOV = zooming ? ZOOM_FOV : normalFOV;

        // Smooth lerp toward target
        currentFOV += (targetFOV - currentFOV) * SMOOTH_SPEED;

        // Clamp to avoid floating point weirdness
        if (Math.abs(currentFOV - targetFOV) < 0.1f) {
            currentFOV = targetFOV;
        }

        mc.gameSettings.fovSetting = currentFOV;
    }

    @Override
    public void onDisable() {
        // Restore original FOV immediately on disable
        if (mc.gameSettings != null) {
            mc.gameSettings.fovSetting = normalFOV;
        }
        zooming = false;
    }

    public boolean isZooming() { return zooming; }
}
