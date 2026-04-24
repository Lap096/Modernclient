package net.minecraft.client.myclient.modules.render;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.myclient.ModuleManager;
import net.minecraft.client.myclient.modules.Module;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.Mouse;


import java.util.ArrayList;
import java.util.List;

/**
 * ModuleArrayList — right-side list of all active modules.
 *
 * Features:
 *  - Smooth slide-in animation when a module is enabled
 *  - Color gradient accent bar on the right edge
 *  - Sorted longest-name first for clean alignment
 *  - Semi-transparent dark background per entry
 */
public class ModuleArrayList extends Module {

    // Per-entry animation state (slide X offset, 0 = fully visible)
    private final java.util.HashMap<String, Float> animX =
        new java.util.HashMap<>();

    private static final float ANIM_SPEED  = 0.18f; // lerp factor
    private static final int   ENTRY_H     = 13;    // height per row (px)
    private static final int   PADDING_X   = 5;
    private static final int   BAR_W       = 3;     // right accent bar width

    // Gradient colors for accent bar (HSB cycle based on Y position)
    private int accentColor(int index, int total) {
        // Simple rainbow: hue shifts from cyan (180°) to violet (280°)
        float hue = 180f + (total > 1 ? (100f * index / (total - 1)) : 0);
        return hsvToRgb(hue, 0.7f, 1.0f) | 0xFF000000;
    }

    public ModuleArrayList() {
        super("ArrayList", "Render",
              "Shows enabled modules on the right side", 0);
    }

    @Override
    public void onRender(float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        int sw = sr.getScaledWidth();

        List<Module> active = ModuleManager.getEnabledSorted();
        // Don't show "ArrayList" itself in the list
        active.removeIf(m -> m instanceof ModuleArrayList);

        int total = active.size();

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager.blendFunc(net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.GL_SRC_ALPHA, net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.GL_ONE_MINUS_SRC_ALPHA);

        for (int i = 0; i < total; i++) {
            Module m  = active.get(i);
            String name = m.getDisplayName();
            int textW   = mc.fontRendererObj.getStringWidth(name);
            int entryW  = textW + PADDING_X * 2 + BAR_W;

            // Animate: slide from right edge toward final position
            float targetX = 0f;
            float curX = animX.getOrDefault(name, (float) entryW);
            curX += (targetX - curX) * ANIM_SPEED;
            animX.put(name, curX);

            int y  = i * (ENTRY_H + 1) + 2;
            int x  = sw - entryW + (int) curX;

            // Dark background
            Gui.drawRect(x, y, sw, y + ENTRY_H, 0x99111111);

            // Accent bar (right edge)
            int accent = accentColor(i, total);
            Gui.drawRect(sw - BAR_W, y, sw, y + ENTRY_H, accent);

            // Module name text
            mc.fontRendererObj.drawStringWithShadow(
                name,
                x + PADDING_X,
                y + (ENTRY_H - 8) / 2,
                0xFFFFFF
            );
        }

        // Clean up animation state for modules that are no longer active
        List<String> activeNames = new ArrayList<>();
        for (Module m : active) activeNames.add(m.getDisplayName());
        animX.keySet().retainAll(activeNames);

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    // --- Helpers ---
    private static int hsvToRgb(float h, float s, float v) {
        h = h % 360f;
        float c  = v * s;
        float x  = c * (1f - Math.abs((h / 60f) % 2f - 1f));
        float m  = v - c;
        float r, g, b;
        if      (h < 60)  { r = c; g = x; b = 0; }
        else if (h < 120) { r = x; g = c; b = 0; }
        else if (h < 180) { r = 0; g = c; b = x; }
        else if (h < 240) { r = 0; g = x; b = c; }
        else if (h < 300) { r = x; g = 0; b = c; }
        else              { r = c; g = 0; b = x; }
        return (((int)((r + m) * 255)) << 16)
             | (((int)((g + m) * 255)) << 8)
             |  ((int)((b + m) * 255));
    }
}
