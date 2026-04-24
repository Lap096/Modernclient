package net.minecraft.client.myclient.items;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * ItemMace — custom display item modeled after the modern Mace.
 *
 * This is a CLIENT-SIDE cosmetic item. The "smash" mechanic is
 * purely visual (particles + sound). Actual damage is always
 * server-authoritative.
 *
 * Model: uses netherite_axe texture as base (swap with
 *        assets/minecraft/textures/items/mace.png once you have it).
 *
 * Register in ItemNetherite.register() or separately.
 */
public class ItemMace extends Item {

    public static final int ID = 5020;
    public static Item mace;

    public ItemMace() {
        super();
        setUnlocalizedName("mace");
        setTextureName("minecraft:mace");      // → textures/items/mace.png
        setCreativeTab(CreativeTabs.tabCombat);
        setMaxDamage(500);
        setMaxStackSize(1);
    }

    /**
     * Visual smash effect — called client-side when right-clicking
     * while falling. Spawns crit particles. No server packets sent.
     */
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (world.isRemote) { // client side only
            double fallDist = player.fallDistance;
            if (fallDist > 1.5) {
                // Spawn visual crit particles proportional to fall distance
                int particleCount = (int) Math.min(fallDist * 3, 30);
                for (int i = 0; i < particleCount; i++) {
                    world.spawnParticle(
                        "crit",
                        player.posX + (Math.random() - 0.5) * 0.5,
                        player.posY,
                        player.posZ + (Math.random() - 0.5) * 0.5,
                        (Math.random() - 0.5) * 0.3,
                        Math.random() * 0.1,
                        (Math.random() - 0.5) * 0.3
                    );
                }

                // Play woosh sound
                world.playSoundAtEntity(player, "random.break", 0.5f, 1.2f);
            }
        }
        return stack;
    }

    /** Register this item — call from your init() */
    public static void register() {
        mace = new ItemMace();
        mace.setRegistryName("mace");
        // Item.itemRegistry.register(ID, "mace", mace);
        // ^ Uncomment and adapt to EaglercraftX registry API
        System.out.println("[MyClient] Registered Mace item.");
    }
}
