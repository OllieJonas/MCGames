package me.ollie.games.leaderboard;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Leaderboard {

    @Getter
    private static Leaderboard instance = new Leaderboard();

    private final Map<String, Integer> scores;

    public Leaderboard() {
        this.scores = new HashMap<>();
    }

    public void registerPlayer(Player player) {
        scores.put(player.getName(), 0);
    }

    public List<String> getLeaderboard() {
        return scores.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public void updateScores() {

    }
}
