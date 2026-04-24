package net.minecraft.client.myclient;

import net.minecraft.client.myclient.modules.Module;
import net.minecraft.client.myclient.modules.combat.NoHurtCam;
import net.minecraft.client.myclient.modules.movement.ToggleSprint;
import net.minecraft.client.myclient.modules.render.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Central registry for all modules.
 * Call ModuleManager.init() once at client startup.
 * Call tick() every game tick and renderTick() every frame.
 */
public class ModuleManager {

    private static final List<Module> modules = new ArrayList<>();

    // ---------------------------------------------------------------
    // Registration — add every module here
    // ---------------------------------------------------------------
    public static void init() {
        register(new ToggleSprint());
        register(new Fullbright());
        register(new Zoom());
        register(new NoHurtCam());
        register(new KeystrokesHUD());
        register(new ModuleArrayList());
        register(new CPSCounter());
    }

    private static void register(Module m) {
        modules.add(m);
    }

    // ---------------------------------------------------------------
    // Tick hooks — called from Minecraft event hooks
    // ---------------------------------------------------------------
    public static void tick() {
        for (Module m : modules) {
            if (m.isEnabled()) m.onUpdate();
        }
    }

    public static void renderTick(float partialTicks) {
        for (Module m : modules) {
            if (m.isEnabled()) m.onRender(partialTicks);
        }
    }

    public static void onKey(int keyCode) {
        // Toggle module if its keybind was pressed
        for (Module m : modules) {
            if (m.getKeybind() != 0 && m.getKeybind() == keyCode) {
                m.toggle();
            }
            m.onKeyPress(keyCode);
        }
    }

    // ---------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------
    public static List<Module> getModules() { return modules; }

    /** Returns enabled modules sorted by display-name length (longest first)
     *  — used by ArrayList renderer for clean right-side alignment. */
    public static List<Module> getEnabledSorted() {
        List<Module> active = new ArrayList<>();
        for (Module m : modules) {
            if (m.isEnabled()) active.add(m);
        }
        active.sort(Comparator.comparingInt(
            m -> -m.getDisplayName().length()
        ));
        return active;
    }

    /** Get a module by class type */
    @SuppressWarnings("unchecked")
    public static <T extends Module> T get(Class<T> clazz) {
        for (Module m : modules) {
            if (m.getClass() == clazz) return (T) m;
        }
        return null;
    }
}
