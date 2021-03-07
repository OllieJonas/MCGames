package me.ollie.games.util;

import lombok.experimental.UtilityClass;
import org.bukkit.enchantments.Enchantment;

@UtilityClass
public class EnchantmentToName {

    public String convert(Enchantment enchantment) {
        switch (enchantment.getName()) {
            case "DAMAGE_ALL":
                return "Sharpness";
            case "DURABILITY":
                return "Unbreaking";
            case "PROTECTION_ENVIRONMENTAL":
                return "Protection";
            default:
                return "Unknown";
        }
    }
}
