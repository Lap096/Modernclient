package net.minecraft.client.myclient.modules.render;

import net.minecraft.client.myclient.modules.Module;

/**
 * Nametags — placeholder, always-on nametag rendering
 * is handled via Fullbright + vanilla settings in this version.
 */
public class Nametags extends Module {
    public Nametags() {
        super("Nametags", "Render", "Always shows nametags", 0);
    }

    @Override
    public void onEnable() {
        mc.gameSettings.showDebugInfo = false;
    }
}
