package me.ollie.games.api.events;

import lombok.AllArgsConstructor;
import me.ollie.games.games.AbstractGame;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class GamePlayerKillEvent<T extends AbstractGame> extends Event {

    private final Player killer;

    private final Player victim;

    private final T game;

    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }
}
