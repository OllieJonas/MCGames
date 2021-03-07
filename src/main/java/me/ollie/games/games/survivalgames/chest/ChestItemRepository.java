package me.ollie.games.games.survivalgames.chest;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.ollie.games.util.ItemStackBuilder;
import me.ollie.games.util.WeightedRandomSet;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class ChestItemRepository {

    @Getter
    private final WeightedRandomSet<ChestItem> items = new WeightedRandomSet<>();

    static {
        // Weapons

        items.add(new ChestItem(new ItemStackBuilder(Material.COMPASS).withName(ChatColor.AQUA + "Player Tracker").build()), 0.3F);
        items.add(new ChestItem(new ItemStack(Material.WOODEN_SWORD)), 0.3F);
        items.add(new ChestItem(new ItemStack(Material.STONE_SWORD)), 0.15F);
        items.add(new ChestItem(new ItemStack(Material.WOODEN_AXE)), 0.35F);
        items.add(new ChestItem(new ItemStack(Material.STONE_AXE)), 0.2F);
        items.add(new ChestItem(new ItemStack(Material.IRON_SWORD)), 0.05F);

        // Armour
        items.add(new ChestItem(new ItemStack(Material.LEATHER_HELMET)), 0.4F);
        items.add(new ChestItem(new ItemStack(Material.LEATHER_CHESTPLATE)), 0.4F);
        items.add(new ChestItem(new ItemStack(Material.LEATHER_LEGGINGS)), 0.4F);
        items.add(new ChestItem(new ItemStack(Material.LEATHER_BOOTS)), 0.4F);
        items.add(new ChestItem(new ItemStackBuilder(Material.LEATHER_BOOTS).withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build()), 0.3F);

        items.add(new ChestItem(new ItemStack(Material.CHAINMAIL_HELMET)), 0.1F);
        items.add(new ChestItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE)), 0.1F);
        items.add(new ChestItem(new ItemStack(Material.CHAINMAIL_LEGGINGS)), 0.1F);
        items.add(new ChestItem(new ItemStack(Material.CHAINMAIL_BOOTS)), 0.1F);

        items.add(new ChestItem(new ItemStack(Material.GOLDEN_HELMET)), 0.1F);
        items.add(new ChestItem(new ItemStack(Material.GOLDEN_CHESTPLATE)), 0.1F);
        items.add(new ChestItem(new ItemStack(Material.GOLDEN_LEGGINGS)), 0.1F);
        items.add(new ChestItem(new ItemStack(Material.GOLDEN_BOOTS)), 0.1F);

        items.add(new ChestItem(new ItemStack(Material.IRON_HELMET)), 0.05F);
        items.add(new ChestItem(new ItemStack(Material.IRON_CHESTPLATE)), 0.05F);
        items.add(new ChestItem(new ItemStack(Material.IRON_LEGGINGS)), 0.05F);
        items.add(new ChestItem(new ItemStack(Material.IRON_BOOTS)), 0.05F);

        // Materials
        items.add(new ChestItem(new ItemStack(Material.STICK)), 0.3F);
        items.add(new ChestItem(new ItemStack(Material.ARROW), true), 0.3F);
        items.add(new ChestItem(new ItemStack(Material.DIAMOND)), 0.05F);

        items.add(new ChestItem(new ItemStack(Material.EXPERIENCE_BOTTLE), true), 0.2F);

        // Random stuff that's useful in battle
        items.add(new ChestItem(new ItemStack(Material.SNOWBALL), true), 0.1F);
        items.add(new ChestItem(new ItemStack(Material.FISHING_ROD)), 0.1F);
        items.add(new ChestItem(new ItemStack(Material.SHIELD)), 0.1F);
        items.add(new ChestItem(new ItemStack(Material.BOW)), 0.1F);
        items.add(new ChestItem(new ItemStack(Material.LAPIS_LAZULI), true), 0.3F);
        items.add(new ChestItem(new ItemStack(Material.ENDER_PEARL), true), 0.1F);

        items.add(new ChestItem(new ItemStackBuilder(Material.STONE_AXE).withEnchantment(Enchantment.DAMAGE_ALL, 1).withName("&bThor's Hammer").build()), 0.01F);
        items.add(new ChestItem(new ItemStackBuilder(Material.STICK).withEnchantment(Enchantment.KNOCKBACK, 5).withName(ChatColor.ITALIC + "haha knockback go brrr").build()), 0.01F);
    }
}
