package me.ollie.games.games.survivalgames;

import lombok.Getter;
import me.ollie.games.core.AbstractGameMap;
import me.ollie.games.games.survivalgames.chest.SGChest;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class SGMap extends AbstractGameMap {

    private Set<Location> chests;

    private final Set<Location> spawnLocations;

    public SGMap(String name, World world, Set<Location> spawnLocations) {
        super(name, world);
        this.spawnLocations = spawnLocations;
    }

    private Set<Location> buildChestLocations() {
        return null;
    }
}
