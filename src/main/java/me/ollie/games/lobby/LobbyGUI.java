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
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LobbyGUI {

    @Getter
    private GUI gui;

    private final BiFunction<Lobby, Integer, ItemStack> lobbyItemStackFunction = (l, i) -> new ItemStackBuilder(l.getState().getMaterial())
            .withName(ChatColor.YELLOW + "" + ChatColor.BOLD + l.getGame().getName())
            .withLore(
                    " ",
                    ChatColor.GRAY + "Player Count: " + ChatColor.AQUA + l.getPlayers().size() + ChatColor.GRAY + " / " + ChatColor.AQUA + l.getMaxPlayers(),
                    " ",
                    ChatColor.GRAY + "Lobby: " + ChatColor.AQUA + i
            )
            .build();

    public LobbyGUI() {
        initGui();
    }

    public void initGui() {
        this.gui = new GUI("Games", 54) {
            @Override
            public void addItems() {
                AtomicInteger counter = new AtomicInteger(0);

                LobbyManager.getInstance()
                        .getLobbies()
                        .values()
                        .stream()
                        .map(l -> new GUIItem(lobbyItemStackFunction.apply(l, counter.get()), (player, item) -> {
                            // jesus christ this is hot garbage - this needs to be fixed later
                            String lobbyStr = ChatColor.stripColor(Objects.requireNonNull(item.getLore()).get(3));
                            int lobbyNo = Integer.parseInt(lobbyStr.split(" ")[1]);
                            l.getState().actionOnClickFactory().accept(player, lobbyNo);
                        }, true))
                        .forEach(i -> add(counter.getAndIncrement(), i));

            }
        };
    }
}
