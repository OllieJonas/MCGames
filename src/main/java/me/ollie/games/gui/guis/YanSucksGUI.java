package me.ollie.games.gui.guis;

import me.ollie.games.gui.GUI;
import me.ollie.games.gui.GUIItem;
import me.ollie.games.util.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class YanSucksGUI extends GUI {

    public YanSucksGUI() {
        super("yan sucks", 27);
    }

    @Override
    public void addItems() {
        add(0, GUIItem.builder()
                .item(new ItemStackBuilder(Material.GRASS_BLOCK)
                        .withName(ChatColor.RED + "yan sucks")
                        .withLore(ChatColor.GRAY + "yan indeed does suck")
                        .build())
                .action(player -> player.sendMessage("yan sucks"))
                .itemClosesMenu(true)
                .build());
    }
}
