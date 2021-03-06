package me.ollie.games.games.survivalgames.scoreboard;

import me.ollie.games.games.survivalgames.SurvivalGames;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class SGScoreboardManager {

    private SurvivalGames survivalGames;

    private final Map<Player, SGScoreboard> scoreboardMap;

    public SGScoreboardManager(SurvivalGames survivalGames) {
        this.survivalGames = survivalGames;
        this.scoreboardMap = new HashMap<>();
    }

    public void addPlayers(Collection<Player> players) {
        players.forEach(this::addPlayer);
    }

    public void addPlayer(Player player) {
        scoreboardMap.put(player, new SGScoreboard(player, getContentFor(player)));
    }

    public void redraw() {
        scoreboardMap.forEach((k, v) -> v.redraw(getContentFor(k)));
    }

    public List<String> getContentFor(Player player) {
        List<String> content = new ArrayList<>();
        content.add(ChatColor.WHITE + "Current Map: " + ChatColor.AQUA + survivalGames.getMap().getName());
        content.add(ChatColor.DARK_GRAY.toString() + " ");
        content.add(ChatColor.WHITE + "Players Remaining: " + ChatColor.AQUA + survivalGames.getAlivePlayers().size());
        content.add(ChatColor.RESET.toString() + " ");
        content.add(ChatColor.WHITE + "Kills: " + ChatColor.AQUA + survivalGames.getKills(player));
        return content;
    }

    public void destroy() {
        scoreboardMap.forEach((k, v) -> v.destroy());
        survivalGames = null;
    }
}
