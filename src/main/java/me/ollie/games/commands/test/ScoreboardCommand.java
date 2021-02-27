package me.ollie.games.commands.test;

import me.ollie.games.Games;
import me.ollie.games.commands.SubCommand;
import me.ollie.games.util.GameScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreboardCommand extends SubCommand {

    public ScoreboardCommand() {
        super("scoreboard", true, new String[]{"sb"});
    }

    @Override
    public void run(Player sender, String[] args) {
        GameScoreboard scoreboard = new GameScoreboard("penis");
        scoreboard.show(sender);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Games.getInstance(), () -> {
            scoreboard.hide(sender);
        }, 100L);

    }
}
