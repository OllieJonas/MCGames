package me.ollie.games.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class GameScoreboard {

    private Scoreboard scoreboard;

    private Objective objective;

    public GameScoreboard(String title) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("test", "dummy", Component.text(ChatColor.AQUA + "" + ChatColor.BOLD + title));
    }

    public void show(Player player) {
        player.setScoreboard(scoreboard);
    }

    public void hide(Player sender) {
        sender.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
}
