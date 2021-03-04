package me.ollie.games.leaderboard;

import me.ollie.games.api.events.GamePlayerKillEvent;
import me.ollie.games.games.survivalgames.SurvivalGames;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import sun.jvm.hotspot.ui.ObjectHistogramPanel;

public class LeaderboardEvents implements Listener {

    @EventHandler
    public void onKill(GamePlayerKillEvent<SurvivalGames> event) {

    }
}
