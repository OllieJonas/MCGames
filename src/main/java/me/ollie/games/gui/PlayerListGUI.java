package me.ollie.games.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class PlayerListGUI {

    private Function<Player, ItemStack> playerItemStackFunction;

    private BiConsumer<Player, Player> action;
}
