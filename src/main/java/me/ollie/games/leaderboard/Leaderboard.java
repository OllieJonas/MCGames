package me.ollie.games.leaderboard;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Leaderboard {

    private static final int HEIGHT = 4;

    private static final Supplier<Location> LEADERBOARD_LOCATION_LEFT = () -> new Location(Bukkit.getWorld("world"), 457.5, 4.5 + HEIGHT, 72.5, -90, 0);

    private static final Supplier<Location> LEADERBOARD_LOCATION_RIGHT = () -> new Location(Bukkit.getWorld("world"), 489.5, 4.5 + HEIGHT, 72.5, 90, 0);

    private Plugin plugin;

    private Set<Player> players;

    private LeaderboardHologram hologramLeft;

    private LeaderboardHologram hologramRight;

    @Getter
    private static Leaderboard instance;

    @Getter
    private final Map<String, Integer> scores;

    public Leaderboard(Plugin plugin) {
        this.plugin = plugin;
        this.scores = new HashMap<>();
        this.players = new HashSet<>(Bukkit.getOnlinePlayers());
        this.hologramLeft = new LeaderboardHologram(plugin, this, LEADERBOARD_LOCATION_LEFT.get());
        this.hologramRight = new LeaderboardHologram(plugin, this, LEADERBOARD_LOCATION_RIGHT.get());

        instance = this;
    }

    public void registerPlayer(Player player) {
        scores.put(player.getName(), 0);
    }

    public List<String> getLeaderboard() {
        return scores.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public void addScores(Map<Player, Integer> kills, Player winner) {

        for (Map.Entry<Player, Integer> kill : kills.entrySet()) {

        }
    }
}
