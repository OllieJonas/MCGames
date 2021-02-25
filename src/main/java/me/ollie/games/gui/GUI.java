package me.ollie.games.gui;

import lombok.Getter;
import me.ollie.games.util.ChestGUIUtils;
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

        if (hasBorder)
            ChestGUIUtils.calculateFiller(size).forEach(i -> inventory.setItem(i, ChestGUIUtils.BORDER_ITEM));
    }

    public void add(int index, GUIItem item) {
        index = hasBorder ? ChestGUIUtils.shiftPosition(index) : index;
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
