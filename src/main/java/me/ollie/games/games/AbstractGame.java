package me.ollie.games.games;

import lombok.Getter;
import me.ollie.games.api.events.GameChangeStateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractGame {

    public enum GameState {
        INIT("Initialising"),
        WAITING("Waiting"),
        LOBBY("In Lobby"),
        STARTING("Starting!"),
        LIVE("Live!"),
        END("Ended!"),
        RESETTING("Resetting...");

        @Getter
        private final String description;

        @Getter
        private static final GameState[] VALUES = values();

        GameState(String description) {
            this.description = description;
        }

        public GameState next() {
            return VALUES[ordinal() + 1];
        }

        public GameState prev() {
            return VALUES[ordinal() + 1];
        }
    }

    @Getter
    private final String name;

    private final Set<Player> players;

    private GameState state;

    public AbstractGame(String name) {
        this.name = name;
        this.players = new HashSet<>();
        this.state = GameState.INIT;
    }

    public abstract void load();

    public abstract void startGame();

    public abstract void endGame();

    public void nextPhase() {
        this.state = state.next();
        Bukkit.getPluginManager().callEvent(new GameChangeStateEvent(this));
    }

    public void prevPhase() {
        this.state = state.prev();
        Bukkit.getPluginManager().callEvent(new GameChangeStateEvent(this));
    }
}
