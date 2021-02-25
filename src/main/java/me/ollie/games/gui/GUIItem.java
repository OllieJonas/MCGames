package me.ollie.games.gui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@Getter
@AllArgsConstructor
@Builder
public class GUIItem {

    private final ItemStack item;

    private final Consumer<Player> action;

    private final boolean itemClosesMenu;
}
