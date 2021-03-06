package me.ollie.games.leaderboard;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.ollie.games.util.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.stream.IntStream;

public class LeaderboardHologram {

    private Hologram hologram;

    private final Location location;

    private final Plugin plugin;

    public LeaderboardHologram(Plugin plugin, Location location) {
        this.plugin = plugin;
        this.location = location;
    }


    private void buildInitialHologram() {
        hologram.appendTextLine(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Leaderboard");
        hologram.appendTextLine(ChatColor.GRAY + "Scores (Kill = 5 Points, Win = 20 Points)");
    }

    public void redraw(List<String> list) {
        removeLeaderboard();
        draw(list);
    }

    public void draw(List<String> list) {
        this.hologram = HologramsAPI.createHologram(plugin, location);
        buildInitialHologram();
        list.forEach(s -> {
            hologram.appendTextLine(s);
        });
    }

    public void removeLeaderboard() {
        hologram.delete();
        this.hologram = null;
    }
}
