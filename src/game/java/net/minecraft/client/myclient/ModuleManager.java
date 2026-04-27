package net.minecraft.client.myclient;
import net.minecraft.client.myclient.modules.Module;
import net.minecraft.client.myclient.modules.combat.*;
import net.minecraft.client.myclient.modules.movement.*;
import net.minecraft.client.myclient.modules.render.*;
import net.minecraft.client.myclient.modules.player.*;
import java.util.*;

public class ModuleManager {
    private static final List<Module> modules = new ArrayList<>();
    public static void init() {
        register(new AntiBlind()); register(new HitColor()); register(new Sneak());
        register(new TimeChanger()); register(new Nametags()); register(new ToggleSprint());
        register(new Fullbright()); register(new Zoom()); register(new NoHurtCam());
        register(new KeystrokesHUD()); register(new ModuleArrayList()); register(new CPSCounter());
        register(new FastPlace()); register(new ClearChat()); register(new Coordinates()); register(new ArmorHUD());
    }
    private static void register(Module m) { modules.add(m); }
    public static void tick() { for (Module m : modules) { if (m.isEnabled()) m.onUpdate(); } }
    public static void renderTick(float pt) { for (Module m : modules) { if (m.isEnabled()) m.onRender(pt); } }
    public static void onKey(int k) {
        for (Module m : modules) {
            if (m.getKeybind() != 0 && m.getKeybind() == k) m.toggle();
            m.onKeyPress(k);
        }
    }
    public static List<Module> getModules() { return modules; }
    public static List<Module> getEnabledSorted() {
        List<Module> active = new ArrayList<>();
        for (Module m : modules) { if (m.isEnabled()) active.add(m); }
        active.sort(Comparator.comparingInt(m -> -m.getDisplayName().length()));
        return active;
    }
}
