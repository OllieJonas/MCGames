package me.ollie.games.util.potion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.ollie.games.util.RomanNumberUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Potion {

    private boolean isSplash;
    private int amount;
    private PotionEffectType type;
    private int amplitude;
    private int duration;

    public ItemStack toItemStack() {
        Material material = isSplash ? Material.SPLASH_POTION : Material.POTION;
        ItemStack item = new ItemStack(material);
        item.setAmount(amount);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(buildName());
        meta.setLore(buildLore());
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        item.setItemMeta(meta);
        return item;
    }

    private String buildName() {
        StringBuilder builder = new StringBuilder();
        builder.append(ChatColor.AQUA);
        if (isSplash)
            builder.append("Splash ");
        builder.append("Potion of ");
        builder.append(PotionEffectTypeToName.convert(type));
        builder.append(" ").append(RomanNumberUtil.toRoman(amplitude));
        builder.append("(").append(duration).append(" seconds").append(")");
        return builder.toString();
    }

    private List<String> buildLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Potion Effect: " + ChatColor.AQUA + type.getName());
        lore.add(ChatColor.GRAY + "Duration: " + ChatColor.AQUA + duration + ChatColor.GRAY + " seconds");
        lore.add(ChatColor.GRAY + "Strength: " + ChatColor.AQUA + RomanNumberUtil.toRoman(amplitude));
        return lore;
    }
}

