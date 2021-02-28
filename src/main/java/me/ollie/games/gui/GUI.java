package me.ollie.games.gui;

import lombok.Getter;
import me.ollie.games.util.ChestGUIUtils;
import me.ollie.games.util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class GUI {

    public final ItemStack BORDER_ITEM = new ItemStackBuilder(Material.BLACK_STAINED_GLASS_PANE)
            .withName(ChatColor.BLACK + "|")
            .build();

    private final String name;

    private final Player player;

    private final int size;

    private final Inventory inventory;

    private final boolean hasBorder;

    private Map<Integer, GUIItem> items;

    public GUI(String name, int size) {
        this(null, name, size, true);
    }

    public GUI(Player player, String name, int size) {
        this(player, name, size, true);
    }

    public GUI(Player player, String name, int size, boolean hasBorder) {
        this.player = player;
        this.name = name;
        this.size = size;
        this.hasBorder = hasBorder;
        this.inventory = Bukkit.createInventory(player, size, name);
        this.items = new HashMap<>();

        addItems();
        init();
    }

    public abstract void addItems();

    public void init() {
        items.entrySet().forEach((e -> inventory.setItem(e.getKey(), e.getValue().getItem())));

        if (hasBorder)
            ChestGUIUtils.calculateFiller(size).forEach(i -> inventory.setItem(i, BORDER_ITEM));
    }

    public void add(int index, GUIItem item) {
        index = hasBorder ? ChestGUIUtils.shiftToBorderPosition(index) : index;
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

    public void redraw() {
        clear();
        addItems();
        init();
    }

    public void clear() {
        this.items = new HashMap<>();
    }
}
