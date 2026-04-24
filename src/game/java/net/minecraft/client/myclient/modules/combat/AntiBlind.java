package net.minecraft.client.myclient.modules.combat;
import net.minecraft.client.myclient.modules.Module;
import net.minecraft.potion.Potion;
public class AntiBlind extends Module {
    public AntiBlind() { super("AntiBlind", "Combat", "Removes blindness effect", 0); }
    @Override public void onUpdate() {
        if (mc.thePlayer == null) return;
        if (mc.thePlayer.isPotionActive(Potion.blindness))
            mc.thePlayer.removePotionEffect(Potion.blindness.getId());
    }
}
