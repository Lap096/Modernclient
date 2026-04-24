    package net.minecraft.client.myclient.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.myclient.ModuleManager;
import net.minecraft.client.myclient.modules.Module;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.Mouse;



import java.io.IOException;
import java.util.*;

/**
 * ClickGUI — Press RIGHT SHIFT (or INSERT) to open.
 * Dark, modern panel layout grouped by category.
 *
 * HOW TO HOOK:
 *   In Minecraft's key handler (dispatchKeypresses or similar):
 *
 *     if (keyCode == net.lax1dude.eaglercraft.v1_8.internal.KeyboardConstants.KEY_RSHIFT || keyCode == Keyboard.KEY_INSERT) {
 *         mc.displayGuiScreen(new ClickGUI());
 *     }
 *
 * Controls:
 *   Left-click a module  → toggle it on/off
 *   Right-click a module → (future: open settings)
 *   Escape / Right Shift → close
 */
public class ClickGUI extends GuiScreen {

    // Panel layout
    private static final int PANEL_W      = 120;
    private static final int HEADER_H     = 18;
    private static final int MODULE_H     = 16;
    private static final int PANEL_GAP    = 8;
    private static final int PANEL_TOP    = 30;

    // Colors
    private static final int COL_BG        = 0xE8141414; // near-black
    private static final int COL_HEADER    = 0xFF1E1E2E; // slightly lighter
    private static final int COL_ENABLED   = 0xFF4C9EFF; // blue accent
    private static final int COL_DISABLED  = 0xFF2A2A3A;
    private static final int COL_HOVER     = 0xFF252535;
    private static final int COL_TEXT      = 0xFFEEEEEE;
    private static final int COL_SUBTEXT   = 0xFF888899;

    // Ordered category list
    private static final String[] CATEGORIES = {"Movement", "Render", "Combat"};

    // Track drag state per panel
    private final Map<String, int[]> panelPositions = new LinkedHashMap<>();
    private String dragging     = null;
    private int    dragOffX     = 0;
    private int    dragOffY     = 0;

    // Hover tracking
    private Module hoveredModule = null;

    @Override
    public void initGui() {
        // Spread panels horizontally on first open
        for (int i = 0; i < CATEGORIES.length; i++) {
            String cat = CATEGORIES[i];
            if (!panelPositions.containsKey(cat)) {
                panelPositions.put(cat,
                    new int[]{ PANEL_GAP + i * (PANEL_W + PANEL_GAP), PANEL_TOP });
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Blurred/dimmed background
        drawRect(0, 0, width, height, 0x88000000);

        // Title bar
        drawCenteredString(fontRendererObj, "§b§lMyClient §7— §fModules",
                           width / 2, 10, 0xFFFFFF);

        hoveredModule = null;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager.blendFunc(net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.GL_SRC_ALPHA, net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.GL_ONE_MINUS_SRC_ALPHA);

        for (String cat : CATEGORIES) {
            List<Module> mods = getModulesInCategory(cat);
            int[] pos = panelPositions.get(cat);
            int px = pos[0], py = pos[1];
            int panelH = HEADER_H + mods.size() * MODULE_H;

            // Panel background
            drawRect(px, py, px + PANEL_W, py + panelH, COL_BG);

            // Category header
            drawRect(px, py, px + PANEL_W, py + HEADER_H, COL_HEADER);
            // Accent line under header
            drawRect(px, py + HEADER_H - 2, px + PANEL_W, py + HEADER_H,
                     COL_ENABLED);
            fontRendererObj.drawString("§f§l" + cat,
                px + 6, py + (HEADER_H - 8) / 2, COL_TEXT);

            // Module rows
            for (int i = 0; i < mods.size(); i++) {
                Module m  = mods.get(i);
                int ry    = py + HEADER_H + i * MODULE_H;
                boolean hovered = mouseX >= px && mouseX <= px + PANEL_W
                               && mouseY >= ry && mouseY <= ry + MODULE_H;
                if (hovered) hoveredModule = m;

                int rowBg = m.isEnabled()
                    ? (hovered ? 0xFF3A6ECC : COL_ENABLED)
                    : (hovered ? COL_HOVER  : COL_DISABLED);
                drawRect(px, ry, px + PANEL_W, ry + MODULE_H, rowBg);

                // Left enabled indicator
                if (m.isEnabled()) {
                    drawRect(px, ry, px + 2, ry + MODULE_H, 0xFF80C8FF);
                }

                fontRendererObj.drawString(m.getName(),
                    px + 8, ry + (MODULE_H - 8) / 2, COL_TEXT);
            }

            // Thin border around panel
            drawHollowRect(px, py, px + PANEL_W, py + panelH, 0x44FFFFFF);
        }

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY,
                                int mouseButton) {
        for (String cat : CATEGORIES) {
            List<Module> mods = getModulesInCategory(cat);
            int[] pos = panelPositions.get(cat);
            int px = pos[0], py = pos[1];

            // Click on header → start drag
            if (mouseX >= px && mouseX <= px + PANEL_W
             && mouseY >= py && mouseY <= py + HEADER_H) {
                dragging = cat;
                dragOffX = mouseX - px;
                dragOffY = mouseY - py;
                return;
            }

            // Click on module row
            for (int i = 0; i < mods.size(); i++) {
                int ry = py + HEADER_H + i * MODULE_H;
                if (mouseX >= px && mouseX <= px + PANEL_W
                 && mouseY >= ry && mouseY <= ry + MODULE_H) {
                    if (mouseButton == 0) {
                        mods.get(i).toggle(); // left click = toggle
                    }
                    return;
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = null;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY,
                               int clickedMouseButton, long timeSinceLastClick) {
        if (dragging != null) {
            int[] pos = panelPositions.get(dragging);
            pos[0] = mouseX - dragOffX;
            pos[1] = mouseY - dragOffY;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == net.lax1dude.eaglercraft.v1_8.internal.KeyboardConstants.KEY_ESCAPE
         || keyCode == net.lax1dude.eaglercraft.v1_8.internal.KeyboardConstants.KEY_RSHIFT) {
            mc.displayGuiScreen(null);
        }
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }

    // --- Helpers ---
    private List<Module> getModulesInCategory(String cat) {
        List<Module> result = new ArrayList<>();
        for (Module m : ModuleManager.getModules()) {
            if (m.getCategory().equals(cat)) result.add(m);
        }
        return result;
    }

    private void drawHollowRect(int x1, int y1, int x2, int y2, int color) {
        drawRect(x1,     y1,     x2, y1 + 1, color);
        drawRect(x1,     y2 - 1, x2, y2,     color);
        drawRect(x1,     y1,     x1 + 1, y2, color);
        drawRect(x2 - 1, y1,     x2, y2,     color);
    }

    private void myDrawRect(int x1, int y1, int x2, int y2, int color) {
        Gui.drawRect(x1, y1, x2, y2, color);
    }
}
