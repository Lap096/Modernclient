package net.minecraft.client.myclient.modules;

import net.minecraft.client.Minecraft;


/**
 * Base class for all client modules.
 * Every feature (ToggleSprint, Fullbright, etc.) extends this.
 */
public abstract class Module {

    protected Minecraft mc = Minecraft.getMinecraft();

    private String name;        // Display name in ArrayList / ClickGUI
    private String category;    // "Movement", "Render", "Combat"
    private String description; // Tooltip text
    private boolean enabled;    // Is this module active?
    private int keybind;        // Keyboard.KEY_* toggle key (0 = none)

    public Module(String name, String category, String description, int keybind) {
        this.name       = name;
        this.category   = category;
        this.description = description;
        this.keybind    = keybind;
        this.enabled    = false;
    }

    // Called every game tick while enabled
    public void onUpdate() {}

    // Called every render frame while enabled
    public void onRender(float partialTicks) {}

    // Called when module is toggled ON
    public void onEnable() {}

    // Called when module is toggled OFF
    public void onDisable() {}

    // Called on every keyboard event (check your own keybind here if needed)
    public void onKeyPress(int keyCode) {}

    // --- Toggle logic ---
    public void toggle() {
        enabled = !enabled;
        if (enabled) onEnable();
        else         onDisable();
    }

    public void setEnabled(boolean state) {
        if (state != enabled) toggle();
    }

    // --- Getters ---
    public String  getName()        { return name; }
    public String  getCategory()    { return category; }
    public String  getDescription() { return description; }
    public boolean isEnabled()      { return enabled; }
    public int     getKeybind()     { return keybind; }
    public void    setKeybind(int k){ keybind = k; }

    // Pretty name for ArrayList (subclasses can override for suffix info)
    public String getDisplayName()  { return name; }
}
