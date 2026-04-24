/*
 * ============================================================
 *  HOOK GUIDE — How to wire MyClient into EaglercraftX 1.8
 * ============================================================
 *
 * You need to make SMALL edits to 3 existing Minecraft source files.
 * Search for the comments marked with [HOOK] to find the right spots.
 *
 * ============================================================
 * FILE 1:  src/main/java/net/minecraft/client/Minecraft.java
 * ============================================================
 *
 * ADD IMPORT at top:
 *   import net.minecraft.client.myclient.MyClient;
 *   import net.minecraft.client.myclient.gui.ClickGUI;
 *
 *
 * FIND:  startGame()  method, near the end where mods/resources load.
 * ADD (after the existing init code):
 *
 *   // [HOOK] MyClient init
 *   MyClient.init();
 *
 *
 * FIND:  runTick()  or  runGameLoop()  method.
 * ADD inside the per-tick block:
 *
 *   // [HOOK] MyClient tick
 *   MyClient.onTick();
 *
 *
 * FIND:  dispatchKeypresses()  or wherever KEY events are processed.
 * ADD inside the key-down check:
 *
 *   // [HOOK] ClickGUI toggle
 *   if (Keyboard.getEventKey() == org.lwjgl.input.Keyboard.KEY_RSHIFT
 *    || Keyboard.getEventKey() == org.lwjgl.input.Keyboard.KEY_INSERT) {
 *       if (currentScreen == null) {
 *           displayGuiScreen(new ClickGUI());
 *       }
 *   }
 *
 *   // [HOOK] Module keybinds
 *   MyClient.onKey(Keyboard.getEventKey());
 *
 *
 * ============================================================
 * FILE 2:  src/main/java/net/minecraft/client/renderer/EntityRenderer.java
 * ============================================================
 *
 * ADD IMPORT:
 *   import net.minecraft.client.myclient.MyClient;
 *   import net.minecraft.client.myclient.ModuleManager;
 *   import net.minecraft.client.myclient.modules.combat.NoHurtCam;
 *
 *
 * FIND:  renderWorld(float partialTicks, ...)  or  updateCameraAndRender()
 * ADD at the end of the 2D overlay section (after chat, crosshair etc.):
 *
 *   // [HOOK] MyClient render (HUDs, ArrayList, Keystrokes, etc.)
 *   MyClient.onRender(partialTicks);
 *
 *
 * FIND:  hurtCameraEffect()  (the method that tilts screen on damage).
 * ADD at the very TOP of that method:
 *
 *   // [HOOK] NoHurtCam
 *   if (ModuleManager.get(NoHurtCam.class) != null
 *    && ModuleManager.get(NoHurtCam.class).isEnabled()) return;
 *
 *
 * ============================================================
 * FILE 3:  src/main/java/net/minecraft/item/Item.java
 * ============================================================
 *
 * ADD IMPORT:
 *   import net.minecraft.client.myclient.items.ItemNetherite;
 *   import net.minecraft.client.myclient.items.ItemMace;
 *
 *
 * FIND:  registerItems()  static block at the bottom of Item.java.
 * ADD after all vanilla items:
 *
 *   // [HOOK] Custom items
 *   ItemNetherite.register();
 *   ItemMace.register();
 *
 *
 * ============================================================
 * TEXTURE FILES NEEDED
 * ============================================================
 *
 * Place these PNG files (16x16 or 32x32) in:
 *   src/main/resources/assets/minecraft/textures/items/
 *
 *   netherite_ingot.png
 *   netherite_sword.png
 *   netherite_pickaxe.png
 *   netherite_axe.png
 *   netherite_shovel.png
 *   netherite_helmet.png
 *   netherite_chestplate.png
 *   netherite_leggings.png
 *   netherite_boots.png
 *   mace.png
 *
 * Get the real textures from:
 *   https://github.com/InventivetalentDev/minecraft-assets/tree/1.21/assets/minecraft/textures/item
 * (Download and rename — e.g. netherite_ingot.png already exists there)
 *
 *
 * ============================================================
 * BUILD
 * ============================================================
 *
 * In your GitHub Codespace terminal:
 *
 *   sdk install java 17.0.9-tem   # first time only
 *   ./gradlew buildTeaVM
 *
 * Output: javascript/index.html  — open this in any browser.
 *
 * ============================================================
 */
