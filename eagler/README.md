# MyClient — EaglercraftX 1.8.8 Custom Client

## File Structure (drop into workspace)

```
EaglercraftX-1.8-workspace/
  sources/main/java/net/minecraft/client/myclient/
    MyClient.java                  ← Entry point
    ModuleManager.java             ← Module registry
    gui/
      ClickGUI.java                ← RShift menu
    modules/
      Module.java                  ← Base class
      movement/
        ToggleSprint.java
      render/
        Fullbright.java
        Zoom.java
        KeystrokesHUD.java
        ModuleArrayList.java
        CPSCounter.java
      combat/
        NoHurtCam.java
    items/
      ItemNetherite.java
      ItemMace.java

  sources/main/resources/assets/minecraft/
    models/item/
      netherite_ingot.json
      mace.json
    textures/items/
      ← Download PNGs here (see below)

  launcher.html                    ← Your custom launcher page
  HOOK_GUIDE.java                  ← Exact edits needed in MC source
```

---

## Step 1 — Set up Codespaces

1. Fork https://github.com/Eaglercraft-Archive/EaglercraftX-1.8-workspace
2. Open in GitHub Codespaces (Code → Codespaces → New)
3. In the terminal:
   ```bash
   sdk install java 17.0.9-tem
   ./gradlew buildTeaVM   # verify base builds
   ```

---

## Step 2 — Drop in these files

Copy everything from this zip into the workspace, matching the paths above.

---

## Step 3 — Hook into Minecraft source

Open `HOOK_GUIDE.java` — it shows the exact lines to add in:
- `Minecraft.java`       (init, tick, key handler)
- `EntityRenderer.java`  (render hook, NoHurtCam)
- `Item.java`            (item registration)

---

## Step 4 — Download textures

Get Netherite + Mace textures from:
https://github.com/InventivetalentDev/minecraft-assets/tree/1.21/assets/minecraft/textures/item

Files needed (copy to `textures/items/`):
- netherite_ingot.png
- netherite_sword.png
- netherite_pickaxe.png
- netherite_axe.png
- netherite_shovel.png
- netherite_helmet.png
- netherite_chestplate.png
- netherite_leggings.png
- netherite_boots.png
- mace.png

---

## Step 5 — Build

```bash
./gradlew buildTeaVM
```

Output: `javascript/index.html`

Open in browser → replace the default launcher with your `launcher.html`.

---

## Keybinds (in-game)

| Key          | Action                      |
|--------------|-----------------------------|
| Right Shift  | Open/close ClickGUI         |
| C (hold)     | Zoom                        |
| ClickGUI     | Toggle all other modules    |

---

## Module List

| Module        | Category | Default |
|---------------|----------|---------|
| ToggleSprint  | Movement | Off     |
| Fullbright    | Render   | Off     |
| Zoom          | Render   | On*     |
| NoHurtCam     | Combat   | Off     |
| KeystrokesHUD | Render   | Off     |
| ArrayList     | Render   | Off     |
| CPSCounter    | Render   | Off     |

*Zoom is always "enabled" but only activates while holding C.
