package net.minecraft.client.myclient.gui;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.myclient.MyClient;
public class HelixMainMenu extends GuiScreen {
    private float time = 0f;
    @Override
    public void initGui() {
        MyClient.init();
        int cx = width / 2;
        int cy = height / 2;
        buttonList.clear();
        buttonList.add(new GuiButton(1, cx - 100, cy - 10, 200, 24, "\u00bb Connect to ballcraft.cc"));
        buttonList.add(new GuiButton(2, cx - 100, cy + 20, 200, 24, "Singleplayer"));
        buttonList.add(new GuiButton(3, cx - 100, cy + 50, 96, 24, "Options..."));
        buttonList.add(new GuiButton(4, cx + 4,   cy + 50, 96, 24, "Quit"));
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        time += 0.01f;
        drawRect(0, 0, width, height, 0xFF0a0a14);
        for (int i = 0; i < 40; i++) {
            int px = (int)((Math.sin(i * 2.3 + time) * 0.5 + 0.5) * width);
            int py = (int)((Math.cos(i * 1.7 + time * 0.7) * 0.5 + 0.5) * height);
            drawRect(px, py, px + 2, py + 2, 0x554c9eff);
        }
        int tx = width / 2;
        drawRect(0, height / 2 - 60, width, height / 2 - 59, 0x334c9eff);
        drawCenteredString(fontRendererObj, "\u00a7b\u00a7lHELIX \u00a7f\u00a7lCLIENT", tx, height / 2 - 95, 0xFFFFFFFF);
        drawCenteredString(fontRendererObj, "\u00a77Built by \u00a7fBrad \u00a78| \u00a77v1.0.0", tx, height / 2 - 78, 0xFFFFFFFF);
        drawRect(tx - 80, height / 2 - 68, tx + 80, height / 2 - 67, 0x554c9eff);
        drawCenteredString(fontRendererObj, "\u00a78ballcraft.cc", tx, height / 2 - 58, 0xFFFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawString(fontRendererObj, "\u00a78Helix Client v1.1 | EaglercraftX 12.0.0", 4, height - 10, 0xFFFFFFFF);
    }
    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 1:
                mc.displayGuiScreen(new GuiConnecting(this, mc, "wss://ballcraft.cc", 25565));
                break;
            case 2:
                mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 3:
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                break;
            case 4:
                mc.shutdown();
                break;
        }
    }
    @Override
    public boolean doesGuiPauseGame() { return false; }
}
