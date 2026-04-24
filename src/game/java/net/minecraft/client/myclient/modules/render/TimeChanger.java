package net.minecraft.client.myclient.modules.render;
import net.minecraft.client.myclient.modules.Module;
public class TimeChanger extends Module {
    public TimeChanger() { super("TimeChanger", "Render", "Locks sky to daytime", 0); }
    @Override public void onUpdate() {
        if (mc.theWorld == null) return;
        mc.theWorld.setWorldTime(6000L);
    }
}
