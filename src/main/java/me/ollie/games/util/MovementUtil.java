package me.ollie.games.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;

@UtilityClass
public class MovementUtil {

    public boolean hasPositionShifted(Location loc1, Location loc2) {
        return loc1.getX() != loc2.getX() || loc1.getZ() != loc2.getZ();
    }
}
