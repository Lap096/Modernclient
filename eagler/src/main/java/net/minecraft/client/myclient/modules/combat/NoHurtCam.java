package net.minecraft.client.myclient.modules.combat;

import net.minecraft.client.myclient.modules.Module;

/**
 * NoHurtCam — disables the screen shake/tilt when taking damage.
 *
 * HOW TO HOOK:
 *   In EntityRenderer.java, find the hurtCameraEffect (or similar)
 *   method that applies the hurt tilt. Add at the top:
 *
 *       if (NoHurtCam.isActive()) return;
 *
 *   Or use the static helper below.
 *
 * Safe: purely cosmetic, no packet manipulation.
 */
public class NoHurtCam extends Module {

    public NoHurtCam() {
        super("NoHurtCam", "Combat",
              "Disables camera shake when taking damage", 0);
    }

    /**
     * Static convenience check for use inside EntityRenderer hook.
     * Usage:  if (NoHurtCam.isActive()) return;
     */
    public static boolean isActive() {
        net.minecraft.client.myclient.ModuleManager mgr =
            null; // static access — use the pattern below in your hook

        // In your hook, use:
        // ModuleManager.get(NoHurtCam.class) != null &&
        // ModuleManager.get(NoHurtCam.class).isEnabled()
        return false; // placeholder — see hook instructions above
    }
}
