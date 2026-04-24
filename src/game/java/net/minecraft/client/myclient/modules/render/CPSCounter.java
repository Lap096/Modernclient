package net.minecraft.client.myclient.modules.render;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.myclient.modules.Module;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.Mouse;


import java.util.LinkedList;
import java.util.Queue;

/**
 * CPSCounter — tracks left/right click CPS and renders alongside FPS.
 *
 * Displays top-left (below any debug info):
 *   FPS: 120   CPS: 8 / 3
 *          (left / right clicks per second)
 *
 * Hook: call Mouse.isButtonDown events from your tick to feed clicks.
 * The onRender() handles display.
 */
public class CPSCounter extends Module {

    // Track click timestamps for the last 1000ms
    private final Queue<Long> leftClicks  = new LinkedList<>();
    private final Queue<Long> rightClicks = new LinkedList<>();

    // Previous button states to detect new presses
    private boolean prevLeft  = false;
    private boolean prevRight = false;

    public CPSCounter() {
        super("CPSCounter", "Render",
              "Shows FPS and clicks per second", 0);
    }

    @Override
    public void onUpdate() {
        long now = System.currentTimeMillis();

        // Detect new left click
        boolean curLeft = Mouse.isButtonDown(0);
        if (curLeft && !prevLeft) leftClicks.add(now);
        prevLeft = curLeft;

        // Detect new right click
        boolean curRight = Mouse.isButtonDown(1);
        if (curRight && !prevRight) rightClicks.add(now);
        prevRight = curRight;

        // Purge clicks older than 1 second
        purge(leftClicks,  now);
        purge(rightClicks, now);
    }

    private void purge(Queue<Long> q, long now) {
        while (!q.isEmpty() && now - q.peek() > 1000L) {
            q.poll();
        }
    }

    @Override
    public void onRender(float partialTicks) {
        if (mc.thePlayer == null) return;

        ScaledResolution sr = new ScaledResolution(mc);

        int fps  = mc.getDebugFPS();
        int lcps = leftClicks.size();
        int rcps = rightClicks.size();

        String fpsStr = "§7FPS: §f" + fps;
        String cpsStr = "§7CPS: §f" + lcps + " §7/ §f" + rcps;

        int x = 6;
        int y = 6;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();

        // Semi-transparent background pill
        int w = Math.max(
            mc.fontRendererObj.getStringWidth(fpsStr),
            mc.fontRendererObj.getStringWidth(cpsStr)
        ) + 8;
        drawRoundedRect(x - 4, y - 2, x + w, y + 22, 0x88000000);

        mc.fontRendererObj.drawStringWithShadow(fpsStr, x, y,      0xFFFFFF);
        mc.fontRendererObj.drawStringWithShadow(cpsStr, x, y + 11, 0xFFFFFF);

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    // Simple filled rect with slight rounded-look using 1px corner trim
    private void drawRoundedRect(int x1, int y1, int x2, int y2, int color) {
        net.minecraft.client.gui.Gui.drawRect(x1 + 1, y1,     x2 - 1, y2,     color);
        net.minecraft.client.gui.Gui.drawRect(x1,     y1 + 1, x2,     y2 - 1, color);
    }
}
