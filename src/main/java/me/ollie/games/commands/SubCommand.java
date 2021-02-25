package me.ollie.games.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public abstract class SubCommand {

    protected static final String[] NO_ALIASES = new String[0];

    private final String name;

    private final boolean requiresOp;

    private final String[] aliases;

    public abstract void run(Player sender, String[] args);
}
