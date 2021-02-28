package me.ollie.games.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class VectorUtils {

    public List<Location> buildCircleAround(Location location, int radius, int points) {
        List<Location> locations = new ArrayList<>();

        for (int i = 0; i < 360; i += 360 / points) {
            double angle = i * Math.PI / 180;
            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);

            locations.add(location.clone().add(x, 1, z));
        }
        return locations;
    }
}
