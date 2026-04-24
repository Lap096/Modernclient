package net.minecraft.client.myclient.modules.movement;
import net.minecraft.client.myclient.modules.Module;
public class Sneak extends Module {
    public Sneak() { super("Sneak", "Movement", "Auto sneak toggle", 0); }
    @Override public void onUpdate() {
        if (mc.thePlayer == null) return;
        mc.thePlayer.setSneaking(true);
    }
    @Override public void onDisable() {
        if (mc.thePlayer != null) mc.thePlayer.setSneaking(false);
    }
}
