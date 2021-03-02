package me.ollie.games.util;

import com.google.common.collect.Lists;
import me.ollie.games.games.survivalgames.kit.Kit;
import me.ollie.games.games.survivalgames.kit.KitRegistry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

    static Kit kit = new Kit("Swordsman", Material.STONE_SWORD, Lists.newArrayList(new ItemStackBuilder(Material.STONE_SWORD).withEnchantment(Enchantment.DAMAGE_ALL, 1).build()));

    private static final Function<Kit, List<String>> kitToLoreFunction = kit -> Stream.concat(
            Stream.of(Lists.newArrayList(ChatColor.GRAY + "penis Click to select this kit!", " ")),
            kit.getItems().stream()
                    .map(item -> Stream.concat(Stream.of(Lists.asList("  - " + ChatColor.GRAY + item.getItemMeta().getDisplayName(), new String[0])), item.getEnchantments()
                                    .entrySet()
                                    .stream()
                                    .map(e -> ChatColor.DARK_GRAY + "  - " + e.getKey().getName() +
                                            " " + RomanNumberUtil.toRoman(e.getValue()))).collect(Collectors.toList())))
            .flatMap(Stream::of)
            .map(String::valueOf)
            .collect(Collectors.toList());

    public static void main(String[] args) {
        System.out.println(String.join("\n", kitToLoreFunction.apply(kit)));
    }
}
