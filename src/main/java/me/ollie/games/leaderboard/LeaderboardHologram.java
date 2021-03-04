package me.ollie.games.leaderboard;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.ollie.games.Games;
import me.ollie.games.util.IntToOrdinalPosition;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class LeaderboardHologram {

    private static final int HEIGHT = 4;

    private static final Supplier<Location> LEADERBOARD_LOCATION = () -> new Location(Bukkit.getWorld("world"), 457.5, 4.5 + HEIGHT, 72.5);

    private Hologram hologram;

    private Map<String, Integer> scores;

    private Plugin plugin;

    public LeaderboardHologram(Plugin plugin) {
        this.plugin = plugin;
        this.hologram = buildInitialHologram(Bukkit.getOnlinePlayers());
        this.scores = new HashMap<>();
        Bukkit.getOnlinePlayers().forEach(p -> hologram.getVisibilityManager().showTo(p));
    }

    public void addScore(String player, int amount) {
        if (!scores.containsKey(player)) {
            scores.put(player, 0);
            return;
        }

        scores.put(player, scores.get(player) + amount);
    }


    private Hologram buildInitialHologram(Collection<? extends Player> players) {
        Hologram hologram = HologramsAPI.createHologram(plugin, LEADERBOARD_LOCATION.get());
        hologram.appendTextLine(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Leaderboard");
        hologram.appendTextLine(ChatColor.GRAY + "Scores (Kill = 5 Points, Win = 20 Points)");
        AtomicInteger counter = new AtomicInteger(0);
        players.forEach(p -> hologram.appendTextLine(getPlayerLine(counter.incrementAndGet(), p, 0)));
        return hologram;
    }

    private String getPlayerLine(int position, Player player, int score) {
        return ChatColor.YELLOW + IntToOrdinalPosition.ordinal(position) + ChatColor.DARK_GRAY + " - " + ChatColor.AQUA + player.getName() + ChatColor.DARK_GRAY + " - " + ChatColor.YELLOW + score;
    }
}
