package me.ollie.games.lobby;

import lombok.Getter;
import me.ollie.games.gui.GUI;
import me.ollie.games.gui.GUIItem;
import me.ollie.games.util.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class LobbyGUI {

    @Getter
    private GUI gui;

    private final Function<Lobby, ItemStack> lobbyItemStackFunction = l -> new ItemStackBuilder(Material.EMERALD_BLOCK)
            .withName(l.getGame().getName())
            .withLore(" ", ChatColor.GRAY + "Player Count: " + ChatColor.AQUA + l.getPlayers().size() + ChatColor.GRAY + " / " + ChatColor.AQUA + l.getMaxPlayers())
            .build();

    public LobbyGUI() {
        initGui();
    }

    public void initGui() {
        this.gui = new GUI("Games", 54, true) {
            @Override
            public void addItems() {
                AtomicInteger counter = new AtomicInteger(0);
                LobbyManager.getInstance()
                        .getLobbies()
                        .values()
                        .stream()
                        .map(l -> new GUIItem(lobbyItemStackFunction.apply(l), p -> p.sendMessage("hihihi"), true))
                        .forEach(i -> add(counter.getAndIncrement(), i));
            }
        };
    }

    public void open(Player player) {
        gui.open(player);
    }
    public void update(Collection<Player> observers) {
        observers.forEach(HumanEntity::closeInventory);
        observers.forEach(p -> gui.open(p));
    }
}
