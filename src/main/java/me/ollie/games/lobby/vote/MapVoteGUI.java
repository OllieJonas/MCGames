package me.ollie.games.lobby.vote;

import lombok.Getter;
import me.ollie.games.core.AbstractGameMap;
import me.ollie.games.games.AbstractGame;
import me.ollie.games.gui.GUI;
import me.ollie.games.gui.GUIItem;
import me.ollie.games.util.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MapVoteGUI {

    @Getter
    private GUI gui;

    private static final BiFunction<AbstractGameMap, Integer, ItemStack> mapToItemFunction = (map, votes) -> new ItemStackBuilder(Material.OAK_SIGN)
            .withName(ChatColor.DARK_AQUA + map.getName())
            .withLore(ChatColor.GRAY + "Click to vote!", " ", ChatColor.GRAY + "Votes: " + ChatColor.AQUA + votes)
            .build();

    public MapVoteGUI(MapVote mapVote) {
        initGui(mapVote);
    }

    private void initGui(MapVote mapVote) {
        this.gui = new GUI("Map Voting", 54) {
            @Override
            public void addItems() {
                AtomicInteger counter = new AtomicInteger(0);
                mapVote.getTalley()
                        .entrySet()
                        .stream()
                        .map(e -> new GUIItem(
                                mapToItemFunction.apply(e.getKey(), e.getValue()),
                                p -> mapVote.vote(p, e.getKey()),
                                true))
                        .forEachOrdered(i -> add(counter.getAndIncrement(), i));
            }
        };
    }

    public void update() {

    }
}
