package net.minecraft.client.myclient.modules;

import net.minecraft.client.Minecraft;

public class Module {
    protected final Minecraft mc = Minecraft.getMinecraft();
    private String name, description;
    private int keybind;
    private boolean enabled;
    private Category category;

    public float x = 10, y = 10;
    public float width = 60, height = 15;

    // This constructor matches your existing modules: (Name, CategoryString, Description, Keybind)
    public Module(String name, String catName, String description, int keybind) {
        this.name = name;
        this.description = description;
        this.keybind = keybind;
        try {
            this.category = Category.valueOf(catName.toUpperCase());
        } catch (Exception e) {
            this.category = Category.RENDER; 
        }
    }

    // This constructor matches my new modules: (Name, Description, Keybind, CategoryEnum)
    public Module(String name, String description, int keybind, Category category) {
        this.name = name;
        this.description = description;
        this.keybind = keybind;
        this.category = category;
    }

    public void onUpdate() {}
    public void onRender(float partialTicks) {}
    public void onKeyPress(int keyCode) {}
    public void onEnable() {}
    public void onDisable() {}

    public void toggle() { setEnabled(!enabled); }
    public void setEnabled(boolean enabled) { 
        this.enabled = enabled; 
        if(enabled) onEnable(); else onDisable();
    }
    
    public boolean isEnabled() { return enabled; }
    public String getName() { return name; } // Added for ClickGUI
    public String getDisplayName() { return name; }
    public int getKeybind() { return keybind; }
    public Category getCategory() { return category; } // Added for ClickGUI

    public enum Category { COMBAT, MOVEMENT, RENDER, PLAYER }
}
