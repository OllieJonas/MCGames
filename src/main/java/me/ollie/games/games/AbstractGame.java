package me.ollie.games.games;

import lombok.Getter;
import lombok.Setter;
import me.ollie.games.core.AbstractGameMap;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@Getter
public abstract class AbstractGame {


    protected final Set<Player> spectators;

    protected final String name;

    @Setter
    protected Set<Player> players;

    public AbstractGame(String name) {
        this.name = name;
        this.players = new HashSet<>();
        this.spectators = new HashSet<>();
    }

    public abstract void load(AbstractGameMap map);

    public abstract void startGame(Set<Player> players);

    public abstract void endGame();
}
