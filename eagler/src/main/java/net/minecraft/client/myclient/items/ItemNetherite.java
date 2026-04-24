package net.minecraft.client.myclient.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;

/**
 * ItemNetherite — registers Netherite variants as custom items.
 *
 * Since 1.8 has no Netherite natively, we register custom Item IDs
 * in the 5000+ range (safe range, not used by vanilla).
 *
 * HOW TO REGISTER:
 *   In Item.java's registerItems() (or equivalent bootstrap), call:
 *       ItemNetherite.register();
 *
 *   Then add model JSONs + textures (see companion files).
 *
 * These items are client-only display items — they won't exist
 * on the server, but you can map them via resource pack tricks
 * or simply display them in your custom GUI/HUD.
 */
public class ItemNetherite {

    // Custom item IDs (pick ranges not used by 1.8 vanilla: safe above 4096)
    public static final int ID_NETHERITE_INGOT    = 5001;
    public static final int ID_NETHERITE_SWORD    = 5002;
    public static final int ID_NETHERITE_PICKAXE  = 5003;
    public static final int ID_NETHERITE_AXE      = 5004;
    public static final int ID_NETHERITE_SHOVEL   = 5005;
    public static final int ID_NETHERITE_HOE      = 5006;
    public static final int ID_NETHERITE_HELMET   = 5007;
    public static final int ID_NETHERITE_CHESTPLATE = 5008;
    public static final int ID_NETHERITE_LEGGINGS = 5009;
    public static final int ID_NETHERITE_BOOTS    = 5010;

    // Static references
    public static Item netheriteIngot;
    public static Item netheriteSword;
    public static Item netheritePickaxe;
    public static Item netheriteAxe;
    public static Item netheriteShovel;
    public static Item netheriteHoe;
    public static Item netheriteHelmet;
    public static Item netheriteChestplate;
    public static Item netheriteLeggings;
    public static Item netheriteBoots;

    public static void register() {
        // Each item is a basic Item with a custom texture name.
        // Texture must exist at:
        //   assets/minecraft/textures/items/netherite_ingot.png  etc.

        netheriteIngot = new Item()
            .setUnlocalizedName("netherite_ingot")
            .setTextureName("minecraft:netherite_ingot")
            .setCreativeTab(CreativeTabs.tabMaterials)
            .setMaxStackSize(64);
        netheriteIngot.setRegistryName("netherite_ingot");
        // Item.itemRegistry.register(ID_NETHERITE_INGOT, "netherite_ingot", netheriteIngot);
        // ^ Uncomment and adapt to match EaglercraftX's Item registry API.

        netheriteSword = new Item()
            .setUnlocalizedName("netherite_sword")
            .setTextureName("minecraft:netherite_sword")
            .setCreativeTab(CreativeTabs.tabCombat)
            .setMaxDamage(2031); // same as modern Netherite
        netheriteSword.setRegistryName("netherite_sword");

        netheritePickaxe = new Item()
            .setUnlocalizedName("netherite_pickaxe")
            .setTextureName("minecraft:netherite_pickaxe")
            .setCreativeTab(CreativeTabs.tabTools)
            .setMaxDamage(2031);
        netheritePickaxe.setRegistryName("netherite_pickaxe");

        netheriteAxe = new Item()
            .setUnlocalizedName("netherite_axe")
            .setTextureName("minecraft:netherite_axe")
            .setCreativeTab(CreativeTabs.tabTools)
            .setMaxDamage(2031);
        netheriteAxe.setRegistryName("netherite_axe");

        netheriteShovel = new Item()
            .setUnlocalizedName("netherite_shovel")
            .setTextureName("minecraft:netherite_shovel")
            .setCreativeTab(CreativeTabs.tabTools)
            .setMaxDamage(2031);
        netheriteShovel.setRegistryName("netherite_shovel");

        // Armor items (basic Item, no ArmorMaterial since this is display-only)
        netheriteHelmet = new Item()
            .setUnlocalizedName("netherite_helmet")
            .setTextureName("minecraft:netherite_helmet")
            .setCreativeTab(CreativeTabs.tabCombat)
            .setMaxDamage(407);
        netheriteHelmet.setRegistryName("netherite_helmet");

        netheriteChestplate = new Item()
            .setUnlocalizedName("netherite_chestplate")
            .setTextureName("minecraft:netherite_chestplate")
            .setCreativeTab(CreativeTabs.tabCombat)
            .setMaxDamage(592);
        netheriteChestplate.setRegistryName("netherite_chestplate");

        netheriteLeggings = new Item()
            .setUnlocalizedName("netherite_leggings")
            .setTextureName("minecraft:netherite_leggings")
            .setCreativeTab(CreativeTabs.tabCombat)
            .setMaxDamage(555);
        netheriteLeggings.setRegistryName("netherite_leggings");

        netheriteBoots = new Item()
            .setUnlocalizedName("netherite_boots")
            .setTextureName("minecraft:netherite_boots")
            .setCreativeTab(CreativeTabs.tabCombat)
            .setMaxDamage(481);
        netheriteBoots.setRegistryName("netherite_boots");

        System.out.println("[MyClient] Registered Netherite items.");
    }
}
