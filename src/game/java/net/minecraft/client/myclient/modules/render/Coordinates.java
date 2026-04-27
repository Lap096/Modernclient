package net.minecraft.client.myclient.modules.render;
import net.minecraft.client.myclient.modules.Module;
public class Coordinates extends Module {
    public Coordinates() { super("Coordinates", "Shows XYZ", 0, Category.RENDER); this.x = 2; this.y = 30; }
    @Override public void onRender(float pt) {
        String s = String.format("XYZ: %.0f %.0f %.0f", mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        this.width = mc.fontRendererObj.getStringWidth(s);
        mc.fontRendererObj.drawStringWithShadow(s, (int)this.x, (int)this.y, 0xFFFFFFFF);
    }
}
