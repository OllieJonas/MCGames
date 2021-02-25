package me.ollie.games.gui;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class GUI {

    private final String name;

    private final int size;

    private final Inventory inventory;

    private final boolean hasBorder;

    private final Map<Integer, GUIItem> items;

    public GUI(String name, int size) {
        this(name, size, true);
    }

    public GUI(String name, int size, boolean hasBorder) {
        this.name = name;
        this.size = size;
        this.hasBorder = hasBorder;
        this.inventory = Bukkit.createInventory(null, size, name);
        this.items = new HashMap<>();

        addItems();
        init();
    }

    public abstract void addItems();

    public void init() {
        items.entrySet().forEach((e -> inventory.setItem(e.getKey(), e.getValue().getItem())));
    }

    public void add(int index, GUIItem item) {
        items.put(index, item);
    }

    public void open(Player player) {
        player.closeInventory();
        player.openInventory(inventory);
    }

    public void close(Player player) {
        player.closeInventory();
    }

    public GUIItem getItem(int slot) {
        return items.get(slot);
    }
}
