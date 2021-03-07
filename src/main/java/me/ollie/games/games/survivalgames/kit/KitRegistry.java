package me.ollie.games.games.survivalgames.kit;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.ollie.games.util.ItemStackBuilder;
import me.ollie.games.util.potion.Potion;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.List;

@UtilityClass
public class KitRegistry {

    @Getter
    private final List<Kit> kits;

    private final Map<Material, Kit> iconToKitMap;

    static {
        kits = new ArrayList<>();
        iconToKitMap = new HashMap<>();
        add(new Kit("Swordsman", Material.STONE_SWORD, Lists.newArrayList(
                new ItemStackBuilder(Material.STONE_SWORD).withName("Stone Sword").withEnchantment(Enchantment.DAMAGE_ALL, 1).build(),
                new ItemStackBuilder(Material.GOLDEN_CHESTPLATE).withName("Golden Chestplate").withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build()
        )));
        add(new Kit("Archer", Material.BOW, Lists.newArrayList(
                new ItemStackBuilder(Material.BOW).withName("Bow").withEnchantment(Enchantment.DAMAGE_ALL, 1).withEnchantment(Enchantment.DURABILITY, 1).build(),
                new ItemStackBuilder(Material.ARROW).withName("Arrow").withAmount(12).build(),
                new ItemStackBuilder(Material.DIAMOND_HELMET).withName("Diamond Helmet").withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build()
        )));

        add(new Kit("Armourer", Material.CHAINMAIL_CHESTPLATE, Lists.newArrayList(
                new ItemStackBuilder(Material.CHAINMAIL_CHESTPLATE).withName("Chainmail Chestplate").withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
                new ItemStackBuilder(Material.CHAINMAIL_LEGGINGS).withName("Chainmail Leggings").withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build()
        )));

        add(new Kit("Frog", Material.LILY_PAD, Lists.newArrayList(
                Potion.builder().amount(3).isSplash(true).effect(PotionEffectType.JUMP).amplitude(2).duration(15).build().toItemStack(),
                new ItemStackBuilder(Material.LEATHER_BOOTS).withName("Frog Boots").withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()
        )));

        add(new Kit("Cocaine Dealer", Material.SUGAR, Lists.newArrayList(
            Potion.builder().amount(2).isSplash(true).effect(PotionEffectType.SPEED).amplitude(2).duration(10).build().toItemStack(),
            Potion.builder().amount(1).effect(PotionEffectType.SPEED).amplitude(2).duration(20).build().toItemStack()
        )));

        add(new Kit("d r o n k", Material.WHEAT, Lists.newArrayList(
            Potion.builder().amount(2).isSplash(true).effect(PotionEffectType.CONFUSION).amplitude(1).duration(20).build().toItemStack(),
                Potion.builder().name(ChatColor.AQUA + "Whiskey").color(Color.BLACK).amount(2).isSplash(false).effect(PotionEffectType.CONFUSION).duration(30).amplitude(3).build().toItemStack()
        )));

        add(new Kit("Wolftamer", Material.BONE, Lists.newArrayList(
            new ItemStackBuilder(Material.WOLF_SPAWN_EGG).withAmount(3).withName("Wolf").build(),
                new ItemStackBuilder(Material.BONE).withAmount(16).withName("Bones").build(),
                new ItemStackBuilder(Material.IRON_HELMET).withName("Iron Helmet").build()
        )));

        add(new Kit("Fisherman", Material.FISHING_ROD, Lists.newArrayList(
                new ItemStackBuilder(Material.FISHING_ROD).withName("Fishing Rod").withEnchantment(Enchantment.DAMAGE_ALL, 3).build(),
                new ItemStackBuilder(Material.CHAINMAIL_HELMET).withName("Chainmail Helmet").build(),
                new ItemStackBuilder(Material.LEATHER_LEGGINGS).withName("Leather Leggings").build()
        )));

        add(new Kit("Hunter", Material.SPECTRAL_ARROW, Lists.newArrayList(
                new ItemStackBuilder(Material.BOW).withName("Bow").build(),
                new ItemStackBuilder(Material.SPECTRAL_ARROW).withName("Spectral Arrow").withAmount(4).build(),
                Potion.builder().amount(1).effect(PotionEffectType.GLOWING).isSplash(true).amplitude(1).duration(45).build().toItemStack()
        )));

        add(new Kit("Rogue", Material.WOODEN_SWORD, Lists.newArrayList(
                new ItemStackBuilder(Material.WOODEN_SWORD).withName("Rogue's Sword (Durability 5)").withData(59 - 5).withEnchantment(Enchantment.DAMAGE_ALL, 5).build(),
                Potion.builder().amount(1).effect(PotionEffectType.INVISIBILITY).isSplash(false).duration(15).amplitude(1).build().toItemStack()
        )));

        add(new Kit("Horsetamer", Material.HORSE_SPAWN_EGG, Lists.newArrayList(
                new ItemStackBuilder(Material.HORSE_SPAWN_EGG).withName("Horse").build(),
                new ItemStackBuilder(Material.WHEAT).withName("Wheat").withAmount(12).build(),
                new ItemStackBuilder(Material.SADDLE).withName("Saddle").withAmount(1).build(),
                new ItemStackBuilder(Material.IRON_HORSE_ARMOR).withName("Iron Horse Armour").withAmount(1).build()
        )));

        add(new Kit("Toxicologist", Material.SPIDER_EYE, Lists.newArrayList(
                Potion.builder().amount(3).effect(PotionEffectType.HARM).isSplash(true).amplitude(2).duration(10).build().toItemStack(),
                Potion.builder().amount(2).effect(PotionEffectType.POISON).isSplash(true).amplitude(3).duration(10).build().toItemStack()
        )));

        add(new Kit("Enchanter", Material.ENCHANTING_TABLE, Lists.newArrayList(
                new ItemStackBuilder(Material.LAPIS_LAZULI).withName("Lapis Lazuli").withAmount(12).build(),
                new ItemStackBuilder(Material.EXPERIENCE_BOTTLE).withName("Bottles of XP").withAmount(20).build(),
                new ItemStackBuilder(Material.WOODEN_SWORD).withName("Wooden Sword").withAmount(1).build()
        )));

        add(new Kit("fasty slowy boy", Material.ANVIL, Lists.newArrayList(
                Potion.builder().effect(PotionEffectType.SLOW).isSplash(true).amplitude(2).amount(3).duration(20).build().toItemStack(),
                Potion.builder().effect(PotionEffectType.SPEED).isSplash(true).amount(2).amplitude(2).duration(20).build().toItemStack()
        )));

        add(new Kit("Bat", Material.BAT_SPAWN_EGG, Lists.newArrayList(
                Potion.builder().effect(PotionEffectType.BLINDNESS).isSplash(true).amplitude(1).amount(4).duration(20).build().toItemStack(),
                new ItemStackBuilder(Material.WOODEN_SWORD).withName("Wooden Sword").withAmount(1).withEnchantment(Enchantment.DAMAGE_ALL, 2).build()
        )));

        add(new Kit("Wingman", Material.ELYTRA, Lists.newArrayList(
                new ItemStackBuilder(Material.ELYTRA).withName("Elytra").withAmount(1).build(),
                new ItemStackBuilder(Material.FIREWORK_ROCKET).withName("Fireworks").withAmount(15).build()
        )));
    }

    private void add(Kit kit) {
        kits.add(kit);
        iconToKitMap.put(kit.getIcon(), kit);
    }

    public Kit getKitFromIcon(Material material) {
        return iconToKitMap.get(material);
    }

    public Kit randomKit() {
        return kits.get(new Random().nextInt(kits.size()) % kits.size());
    }
}
