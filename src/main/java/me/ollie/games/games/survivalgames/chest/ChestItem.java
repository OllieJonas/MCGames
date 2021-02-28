package me.ollie.games.games.survivalgames.chest;

import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ChestItem {

    private final ItemStack item;

    private static final Random RANDOM = new Random();

    private final boolean shouldGenerateRandomAmount;


    public ChestItem(ItemStack item) {
        this(item, false);
    }

    public ChestItem(ItemStack item, boolean shouldGenerateRandomAmount) {
        this.item = makeUnbreakable(item);
        this.shouldGenerateRandomAmount = shouldGenerateRandomAmount;
    }

    public ItemStack getItem() {
        return shouldGenerateRandomAmount ? randomNumberOfItem(5) : item;
    }

    private ItemStack makeUnbreakable(ItemStack item) {
        item.getItemMeta().setUnbreakable(true);
        return item;
    }

    @SuppressWarnings("SameParameterValue")
    private ItemStack randomNumberOfItem(int bound) {
        int randInt = RANDOM.nextInt(bound);
        item.setAmount(randInt);
        return item;
    }
}
