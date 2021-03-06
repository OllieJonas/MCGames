package me.ollie.games.util.potion;

import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class PotionEffectTypeToName {

    public static String convert(PotionEffectType type) {
        switch (type.getName()) {
            case "ABSORPTION":
                return "Absorption";
            case "BLINDNESS":
                return "Blindness";
            case "CONFUSION":
                return "Nausea";
            case "DAMAGE_RESISTANCE":
                return "Resistance";
            case "FAST_DIGGING":
                return "Haste";
            case "GLOWING":
                return "Glowing";
            case "FIRE_RESISTANCE":
                return "Fire Resistance";
            case "HARM":
                return "Damage";
            case "HEAL":
                return "Healing";
            case "INVISIBILITY":
                return "Invisibility";
            case "JUMP":
                return "Jump";
            case "LUCK":
                return "Luck";
            case "POISON":
                return "Poison";
            case "WITHER":
                return "Wither";
            case "SLOW_FALLING":
                return "Feather Falling";
            case "SLOWNESS":
                return "Slowness";
            case "SPEED":
                return "Speed";
            case "STRENGTH":
                return "Strength";
            case "WEAKNESS":
                return "Weakness";
            default:
                return "Something idk what it is lmao";
        }
    }
}
