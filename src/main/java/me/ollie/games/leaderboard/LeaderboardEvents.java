package me.ollie.games.leaderboard;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LeaderboardEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Leaderboard.getInstance().registerPlayer(event.getPlayer());
    }
}
