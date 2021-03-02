package me.ollie.games.games.survivalgames.kit;

import com.google.common.collect.Lists;
import lombok.Getter;
import me.ollie.games.gui.GUI;
import me.ollie.games.gui.GUIItem;
import me.ollie.games.util.EnchantmentToName;
import me.ollie.games.util.ItemStackBuilder;
import me.ollie.games.util.RomanNumberUtil;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KitGUI {

    private final KitManager manager;

    @Getter
    private GUI gui;

    // so close to getting this working :(
//    private final Function<Kit, List<String>> kitToLoreStream = kit -> Stream.concat(
//            Stream.concat(
//                    Stream.of(Collections.singletonList(ChatColor.GRAY + "Click to select this kit!")),
//                    Stream.of(Collections.singletonList(" "))),
//                    kit.getItems().stream()
//                            .map(item -> Stream.concat(
//                                    Stream.of(Collections.singletonList(ChatColor.GRAY + " - " + item.getItemMeta().getDisplayName())),
//                                    kit.getItems().stream().map(k -> k.getEnchantments().entrySet().stream()
//                                            .map(e -> ChatColor.DARK_GRAY + "  - " + EnchantmentToName.convert(e.getKey()) + " " + RomanNumberUtil.toRoman(e.getValue()))
//                                            .collect(Collectors.toList()))
//                                            .collect(Collectors.toList())
//                                            .stream()))).collect(Collectors.toList()).stream().flatMap(Collection::stream).collect(Collectors.toList());

    private final Function<Kit, List<String>> kitToLoreFunction = kit -> {
        List<String> list = new ArrayList<>();
        list.add(ChatColor.GRAY + "Click to select this kit!");
        list.add(" ");

        for (ItemStack i : kit.getItems()) {
            list.add(ChatColor.GRAY + " - " + i.getItemMeta().getDisplayName() + " x" + i.getAmount());

            for (Map.Entry<Enchantment, Integer> entry : i.getEnchantments().entrySet()) {
                list.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "        " + EnchantmentToName.convert(entry.getKey()) + " " + RomanNumberUtil.toRoman(entry.getValue()));
            }

        }
        return list;
    };

    private final Function<Kit, ItemStack> kitToItemStackFunction = (kit) -> new ItemStackBuilder(
            kit.getIcon())
            .withName(ChatColor.AQUA + kit.getName())
            .withLore(kitToLoreFunction.apply(kit))
            .build();


    public KitGUI(KitManager manager) {
        this.manager = manager;
        initGui();
    }

    public void initGui() {
        this.gui = new GUI("Kit Selector - Press ESC to Exit!", 54) {
            @Override
            public void addItems() {
                AtomicInteger counter = new AtomicInteger(0);
                KitRegistry.getKits().stream().map(kit -> new GUIItem(kitToItemStackFunction.apply(kit), (p, i) -> manager.addKit(p, KitRegistry.getKitFromIcon(i.getType())), true)).forEach(i -> add(counter.getAndIncrement(), i));
            }
        };
    }
}
