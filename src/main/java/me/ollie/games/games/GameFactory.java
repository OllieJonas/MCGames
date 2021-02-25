package me.ollie.games.games;

import me.ollie.games.games.survivalgames.SurvivalGames;

import java.util.*;
import java.util.function.Supplier;

public class GameFactory {

    private static final List<String> gameNames = new ArrayList<>();
    private static final Map<String, Supplier<? extends AbstractGame>> games = new HashMap<>();

    static {
        addGame("sg", SurvivalGames::new);
    }

    private static void addGame(String name, Supplier<? extends AbstractGame> game) {
        gameNames.add(name);
        games.put(name, game);
    }

    public static AbstractGame getGame(String name) {

        if (name.equalsIgnoreCase("random"))
            return getRandomGame();
        else
            return doGetGame(name);
    }

    public static AbstractGame getRandomGame() {
        Random random = new Random();
        int randInt = random.nextInt(games.size());
        return doGetGame(gameNames.get(randInt));
    }

    private static AbstractGame doGetGame(String name) {
        Supplier<? extends AbstractGame> game = games.get(name.toLowerCase());
        return game != null ? game.get() : getRandomGame();
    }

}
