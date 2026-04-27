package net.minecraft.client.myclient.modules.render;
import net.minecraft.client.myclient.modules.Module;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
public class ArmorHUD extends Module {
    public ArmorHUD() { super("ArmorHUD", "Armor durability", 0, Category.RENDER); this.x = 200; this.y = 10; this.width = 80; this.height = 16; }
    @Override public void onRender(float pt) {
        for (int i = 0; i <= 3; i++) {
            ItemStack s = mc.thePlayer.inventory.armorInventory[i];
            if (s != null) {
                int px = (int)this.x + (3 - i) * 20;
                RenderHelper.enableGUIStandardItemLighting();
                mc.getRenderItem().renderItemAndEffectIntoGUI(s, px, (int)this.y);
                mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, s, px, (int)this.y);
                RenderHelper.disableStandardItemLighting();
            }
        }
    }
}
