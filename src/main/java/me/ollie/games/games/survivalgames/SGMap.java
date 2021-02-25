package me.ollie.games.games.survivalgames;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class SGMap {

    private Map<Location, Chest> chests;

    public SGMap() {
        this.chests = new HashMap<>();
    }
}
