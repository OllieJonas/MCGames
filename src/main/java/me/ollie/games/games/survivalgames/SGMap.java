package me.ollie.games.games.survivalgames;

import me.ollie.games.games.survivalgames.chest.SGChest;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SGMap {

    private World world;

    private Map<Location, SGChest> chests;

    private Set<Location> spawnLocations;

    public SGMap() {
        this.chests = new HashMap<>();
    }
}
