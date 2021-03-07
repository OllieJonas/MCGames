package me.ollie.games.games.survivalgames.chest;

import me.ollie.games.util.WeightedRandomSet;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class SGChest {

    private static final Random RANDOM = new Random();

    public static Map<Integer, ItemStack> buildItems() {
        return buildItemSet().stream().collect(Collectors.toMap(
                item -> RANDOM.nextInt(27),
                item -> item,
                (i1, i2) -> i1 // ignore duplicate keys
        ));
    }

    public static Set<ItemStack> buildItemSet() {
        Set<ItemStack> items = new HashSet<>();

        WeightedRandomSet<ChestItem> possibleItems = ChestItemRepository.getItems();

        int size = RANDOM.nextInt(2     ) + 2;
        for (int i = 0; i < size; i++) {
            items.add(possibleItems.getRandom().getItem());
        }

        return items;
    }
}
