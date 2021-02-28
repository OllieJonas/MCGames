package me.ollie.games.api.events;

import lombok.AllArgsConstructor;
import me.ollie.games.games.AbstractGame;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class GamePlayerKillEvent<T extends AbstractGame> extends Event {

    private Player killer;

    private Player victim;

    private T game;

    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }
}
