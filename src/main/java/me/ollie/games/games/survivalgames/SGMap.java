package me.ollie.games.games.survivalgames;

import lombok.Getter;
import me.ollie.games.core.AbstractGameMap;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Set;

@Getter
public class SGMap extends AbstractGameMap {

    private final Set<Location> spawnLocations;

    public SGMap(String name, World world, Set<Location> spawnLocations) {
        super(name, world);
        this.spawnLocations = spawnLocations;
    }
}
