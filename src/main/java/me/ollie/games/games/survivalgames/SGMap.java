package me.ollie.games.games.survivalgames;

import lombok.Builder;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SGMap {

    private World world;

    private Map<Location, Chest> chests;

    private Set<Location> spawnLocations;

    public SGMap() {
        this.chests = new HashMap<>();
    }
}
