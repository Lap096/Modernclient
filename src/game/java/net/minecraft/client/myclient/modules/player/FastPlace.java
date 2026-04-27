package net.minecraft.client.myclient.modules.player;
import net.minecraft.client.myclient.modules.Module;
import net.minecraft.util.Timer;
import java.lang.reflect.Field;

public class FastPlace extends Module {
    public FastPlace() { super("FastPlace", "No place delay", 0, Category.PLAYER); }
    
    @Override 
    public void onUpdate() { 
        // We use the 'rightClickDelayTimer' via the Minecraft instance directly
        // Note: In some Eaglercraft versions, this is simply 'mc.rightClickDelayTimer'
        // If it's private, we have to use the field name used in your specific build
        try {
            Field f = mc.getClass().getDeclaredField("rightClickDelayTimer");
            f.setAccessible(true);
            f.set(mc, 0);
        } catch (Exception e) {
            // Fallback for different obfuscation
        }
    }
}
