package me.ollie.games.api.events;

import lombok.Getter;
import me.ollie.games.games.AbstractGame;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GameChangeStateEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @Getter
    private final AbstractGame game;

    public GameChangeStateEvent(AbstractGame game) {
        this.game = game;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
