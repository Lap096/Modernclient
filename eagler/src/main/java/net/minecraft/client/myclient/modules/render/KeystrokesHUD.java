package net.minecraft.client.myclient.modules.render;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.myclient.modules.Module;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

/**
 * KeystrokesHUD — renders WASD + LMB/RMB keys in the bottom-left.
 *
 * Each key lights up white when pressed, dark when released.
 * Uses Minecraft's built-in Gui drawing (no external dependencies).
 *
 * Hook: call onRender(partialTicks) from your overlay renderer.
 */
public class KeystrokesHUD extends Module {

    // Key box dimensions
    private static final int KEY_W  = 22;
    private static final int KEY_H  = 22;
    private static final int GAP    = 3;

    // Colors (ARGB)
    private static final int COLOR_ACTIVE   = 0xCCFFFFFF; // white, semi-transparent
    private static final int COLOR_INACTIVE = 0x88333333; // dark grey
    private static final int COLOR_TEXT_ON  = 0xFF000000; // black text when lit
    private static final int COLOR_TEXT_OFF = 0xFFAAAAAA; // grey text when dark

    public KeystrokesHUD() {
        super("KeystrokesHUD", "Render",
              "Shows WASD + mouse click indicators", 0);
    }

    @Override
    public void onRender(float partialTicks) {
        if (mc.thePlayer == null || mc.currentScreen != null) return;

        ScaledResolution sr = new ScaledResolution(mc);
        int sw = sr.getScaledWidth();
        int sh = sr.getScaledHeight();

        // Bottom-left anchor
        int baseX = 6;
        int baseY = sh - (KEY_H * 3) - (GAP * 2) - 6;

        boolean wDown = mc.gameSettings.keyBindForward.isKeyDown();
        boolean aDown = mc.gameSettings.keyBindLeft.isKeyDown();
        boolean sDown = mc.gameSettings.keyBindBack.isKeyDown();
        boolean dDown = mc.gameSettings.keyBindRight.isKeyDown();
        boolean lmb   = Mouse.isButtonDown(0);
        boolean rmb   = Mouse.isButtonDown(1);

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // Row 1: W (centered above A S D)
        int wX = baseX + KEY_W + GAP;
        drawKey(wX, baseY, "W", wDown);

        // Row 2: A S D
        int row2Y = baseY + KEY_H + GAP;
        drawKey(baseX,               row2Y, "A", aDown);
        drawKey(baseX + KEY_W + GAP, row2Y, "S", sDown);
        drawKey(baseX + (KEY_W + GAP) * 2, row2Y, "D", dDown);

        // Row 3: LMB  RMB (wider keys)
        int row3Y = row2Y + KEY_H + GAP;
        int mouseKeyW = (KEY_W * 3 + GAP * 2 - GAP) / 2;
        drawKeyWide(baseX, row3Y, mouseKeyW, "LMB", lmb);
        drawKeyWide(baseX + mouseKeyW + GAP, row3Y, mouseKeyW, "RMB", rmb);

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    // Draw a standard square key
    private void drawKey(int x, int y, String label, boolean pressed) {
        int bg   = pressed ? COLOR_ACTIVE   : COLOR_INACTIVE;
        int text = pressed ? COLOR_TEXT_ON  : COLOR_TEXT_OFF;

        // Background box
        Gui.drawRect(x, y, x + KEY_W, y + KEY_H, bg);

        // Thin border
        Gui.drawRect(x,          y,          x + KEY_W,     y + 1,       0x44FFFFFF);
        Gui.drawRect(x,          y,          x + 1,         y + KEY_H,   0x44FFFFFF);
        Gui.drawRect(x,          y + KEY_H - 1, x + KEY_W, y + KEY_H,   0x22000000);
        Gui.drawRect(x + KEY_W - 1, y,       x + KEY_W,    y + KEY_H,   0x22000000);

        // Label — centered
        int tw = mc.fontRendererObj.getStringWidth(label);
        mc.fontRendererObj.drawString(
            label,
            x + (KEY_W - tw) / 2,
            y + (KEY_H - 8) / 2,
            text,
            false
        );
    }

    // Draw a wider key (for LMB/RMB)
    private void drawKeyWide(int x, int y, int w, String label, boolean pressed) {
        int bg   = pressed ? COLOR_ACTIVE   : COLOR_INACTIVE;
        int text = pressed ? COLOR_TEXT_ON  : COLOR_TEXT_OFF;

        Gui.drawRect(x, y, x + w, y + KEY_H, bg);
        Gui.drawRect(x,     y,     x + w, y + 1,      0x44FFFFFF);
        Gui.drawRect(x,     y,     x + 1, y + KEY_H,  0x44FFFFFF);

        int tw = mc.fontRendererObj.getStringWidth(label);
        mc.fontRendererObj.drawString(
            label,
            x + (w  - tw) / 2,
            y + (KEY_H - 8) / 2,
            text,
            false
        );
    }
}
