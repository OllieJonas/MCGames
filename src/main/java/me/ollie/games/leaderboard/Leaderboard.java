package me.ollie.games.leaderboard;

import lombok.Getter;
import me.ollie.games.util.IntToOrdinalPosition;
import me.ollie.games.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Leaderboard {

    private static final float HEIGHT = 4.5F;

    private static final int KILL_POINTS_WEIGHTING = 5;

    private static final int WIN_POINTS_WEIGHTING = 20;

    private static final Supplier<Location> LEADERBOARD_LOCATION_LEFT = () -> new Location(Bukkit.getWorld("world"), 457.5, 4.5 + HEIGHT, 72.5, -90, 0);

    private static final Supplier<Location> LEADERBOARD_LOCATION_RIGHT = () -> new Location(Bukkit.getWorld("world"), 489.5, 4.5 + HEIGHT, 72.5, 90, 0);

    private Plugin plugin;

    private LeaderboardHologram hologramLeft;

    private LeaderboardHologram hologramRight;

    @Getter
    private static Leaderboard instance;

    @Getter
    private final Map<String, Integer> scores;

    public Leaderboard(Plugin plugin) {
        this.plugin = plugin;
        this.scores = new LinkedHashMap<>();
        this.hologramLeft = new LeaderboardHologram(plugin, LEADERBOARD_LOCATION_LEFT.get());
        // this.hologramRight = new LeaderboardHologram(plugin, LEADERBOARD_LOCATION_RIGHT.get());

        init();
        instance = this;
    }

    public void init() {
        Bukkit.getOnlinePlayers().forEach(p -> scores.put(p.getName(), 0));
        hologramLeft.draw(getLeaderboard(scores));
        // hologramRight.draw(getLeaderboard(scores));
    }

    public void registerPlayer(Player player) {
        if (!scores.containsKey(player.getName()))
            scores.put(player.getName(), 0);

        redraw();
    }

    public void addScores(Map<String, Integer> kills, Player winner) {

        for (Map.Entry<String, Integer> kill : kills.entrySet()) {
            scores.put(kill.getKey(), scores.get(kill.getKey()) + (kill.getValue() * KILL_POINTS_WEIGHTING));
        }

        scores.put(winner.getName(), scores.get(winner.getName()) + WIN_POINTS_WEIGHTING);

        redraw();
    }

    public void addScore(String playerName, int amount) {
        scores.put(playerName, scores.get(playerName) + amount);
        redraw();
    }

    public void redraw() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            hologramLeft.redraw(getLeaderboard(scores));
            // hologramRight.redraw(getLeaderboard(scores));
        }, 20L);

    }

    public List<String> getLeaderboard(Map<String, Integer> map) {
        AtomicInteger integer = new AtomicInteger(1);
        return map.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue() - e1.getValue())
                .map(e -> ChatColor.YELLOW + IntToOrdinalPosition.ordinal(integer.getAndIncrement()) +
                        ChatColor.GRAY + " - " + ChatColor.AQUA + e.getKey() + ChatColor.GRAY + " - " +
                        ChatColor.GOLD + e.getValue())
                .collect(Collectors.toList());
    }
}
