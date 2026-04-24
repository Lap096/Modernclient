package net.minecraft.client.myclient.modules.movement;

import net.minecraft.client.myclient.modules.Module;
import org.lwjgl.input.Keyboard;

/**
 * ToggleSprint — automatically sprints whenever the player
 * is moving forward, without holding the sprint key.
 *
 * Keybind: none by default (toggle via ClickGUI).
 * Safe: this is purely client-side key emulation, widely used.
 */
public class ToggleSprint extends Module {

    public ToggleSprint() {
        super("ToggleSprint", "Movement",
              "Auto-sprint when moving forward", 0);
    }

    @Override
    public void onUpdate() {
        if (mc.thePlayer == null) return;

        // Sprint if moving forward and not in a blocking/eating state
        boolean movingForward  = mc.gameSettings.keyBindForward.isKeyDown();
        boolean isSneaking     = mc.thePlayer.isSneaking();
        boolean usingItem      = mc.thePlayer.isUsingItem();
        boolean canSprint      = mc.thePlayer.getFoodStats().getFoodLevel() > 6
                                 || mc.thePlayer.capabilities.allowFlying;

        if (movingForward && !isSneaking && !usingItem && canSprint) {
            mc.thePlayer.setSprinting(true);
        }
    }

    @Override
    public void onDisable() {
        // Stop sprinting when toggled off
        if (mc.thePlayer != null) {
            mc.thePlayer.setSprinting(false);
        }
    }
}
