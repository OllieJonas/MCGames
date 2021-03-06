package me.ollie.games.games.survivalgames.kit;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.ollie.games.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.*;

@UtilityClass
public class KitRegistry {

    @Getter
    private final List<Kit> kits;

    private final Map<Material, Kit> iconToKitMap;

    static {
        kits = new ArrayList<>();
        iconToKitMap = new HashMap<>();
        add(new Kit("Knight", Material.STONE_SWORD, Lists.newArrayList(
                new ItemStackBuilder(Material.STONE_SWORD).withName("Stone Sword").withEnchantment(Enchantment.DAMAGE_ALL, 1).build(),
                new ItemStackBuilder(Material.GOLDEN_CHESTPLATE).withName("Golden Chestplate").withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build()
        )));
        add(new Kit("Archer", Material.BOW, Lists.newArrayList(
                new ItemStackBuilder(Material.BOW).withName("Bow").withEnchantment(Enchantment.DURABILITY, 1).build(),
                new ItemStackBuilder(Material.ARROW).withAmount(12).build(),
                new ItemStackBuilder(Material.DIAMOND_HELMET).withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build()
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
