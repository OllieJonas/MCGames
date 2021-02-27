package me.ollie.games.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.World;

@Getter
@AllArgsConstructor
public abstract class AbstractGameMap {

    protected final String name;

    protected final World world;

}
