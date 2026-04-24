package net.minecraft.client.myclient.modules.combat;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.myclient.modules.Module;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
public class HitColor extends Module {
    private int flashTicks = 0;
    private int lastHurtTime = 0;
    public HitColor() { super("HitColor", "Combat", "Flashes screen on damage", 0); }
    @Override public void onUpdate() {
        if (mc.thePlayer == null) return;
        int hurt = mc.thePlayer.hurtTime;
        if (hurt > lastHurtTime) flashTicks = 8;
        lastHurtTime = hurt;
        if (flashTicks > 0) flashTicks--;
    }
    @Override public void onRender(float partialTicks) {
        if (flashTicks <= 0) return;
        ScaledResolution sr = new ScaledResolution(mc);
        float alpha = (flashTicks / 8.0f) * 0.35f;
        int color = (int)(alpha * 255) << 24 | 0xFF0000;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        Gui.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), color);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
