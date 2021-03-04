package me.ollie.games.leaderboard;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.ollie.games.util.IntToOrdinalPosition;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.function.Supplier;

public class LeaderboardHologram {

    private final Map<String, Integer> scores;

    private final Location location;

    private final Plugin plugin;

    private final Leaderboard leaderboard;

    public LeaderboardHologram(Plugin plugin, Leaderboard leaderboard, Location location) {
        this.plugin = plugin;
        this.scores = new HashMap<>();
        this.location = location;
        this.leaderboard = leaderboard;
    }

    public void addScore(String player, int amount) {
        if (!scores.containsKey(player)) {
            scores.put(player, 0);
            return;
        }

        scores.put(player, scores.get(player) + amount);
    }

    private Hologram buildHologram(Collection<? extends Player> players, Location location) {
        Hologram hologram = buildInitialHologram(location);
        return hologram;
    }


    private Hologram buildInitialHologram(Location location) {
        Hologram hologram = HologramsAPI.createHologram(plugin, location);
        hologram.appendTextLine(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Leaderboard");
        hologram.appendTextLine(ChatColor.GRAY + "Scores (Kill = 5 Points, Win = 20 Points)");
        return hologram;
    }

    private String getPlayerLine(int position, String player, int score) {
        return ChatColor.YELLOW + IntToOrdinalPosition.ordinal(position) + ChatColor.DARK_GRAY + " - " + ChatColor.AQUA + player + ChatColor.DARK_GRAY + " - " + ChatColor.YELLOW + score;
    }
}
