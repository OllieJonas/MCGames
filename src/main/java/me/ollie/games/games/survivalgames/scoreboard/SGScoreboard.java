package me.ollie.games.games.survivalgames.scoreboard;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.List;

public class SGScoreboard {

    private final Player player;

    private List<String> content;

    private final String title;

    private Scoreboard scoreboard;

    private Objective objective;

    private static final ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

    private static final Scoreboard BLANK_SCOREBOARD = scoreboardManager.getNewScoreboard();

    public SGScoreboard(Player player, List<String> initialContent) {
        this.player = player;
        this.content = initialContent;
        this.title = ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Survival Games";
        this.scoreboard = initScoreboard();

        show();
    }

    public void show() {
        draw();
        player.setScoreboard(scoreboard);
    }

    public void hide() {
        player.setScoreboard(BLANK_SCOREBOARD);
    }

    public void redraw(List<String> content) {
        clear();
        this.content = content;
        draw();
    }

    public void draw() {
        for (int i = 0; i < content.size(); i++) {
            String line = content.get(i);
            int key = (content.size() - (i + 1));
            Score score = objective.getScore(line);
            score.setScore(key);
        }
    }

    public void clear() {
        for (String s : content) {
            scoreboard.resetScores(s);
        }
    }

    private Scoreboard initScoreboard() {
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        objective = scoreboard.registerNewObjective("Game Stats", "", Component.text(title));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        return scoreboard;
    }

    public void destroy() {
        player.setScoreboard(BLANK_SCOREBOARD);
        scoreboard = null;
    }
}
