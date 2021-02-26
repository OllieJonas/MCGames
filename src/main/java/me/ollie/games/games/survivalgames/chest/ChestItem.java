package me.ollie.games.games.survivalgames.chest;

import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ChestItem {

    private final ItemStack item;

    private final float dropChance;

    private final boolean shouldGenerateRandomAmount;


    public ChestItem(ItemStack item, float dropChance) {
        this(item, dropChance, false);
    }
    public ChestItem(ItemStack item, float dropChance, boolean shouldGenerateRandomAmount) {
        this.item = makeUnbreakable(item);
        this.dropChance = dropChance;
        this.shouldGenerateRandomAmount = shouldGenerateRandomAmount;
    }

    public ItemStack getItem() {
        return shouldGenerateRandomAmount ? randomNumberOfItem(5) : item;
    }

    public float getDropChance() {
        return dropChance;
    }

    private ItemStack makeUnbreakable(ItemStack item) {
        item.getItemMeta().setUnbreakable(true);
        return item;
    }



    private ItemStack randomNumberOfItem(int bound) {
        Random random = new Random();
        int randInt = random.nextInt(bound);
        item.setAmount(randInt);
        return item;
    }
}
