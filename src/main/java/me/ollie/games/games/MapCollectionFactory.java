package me.ollie.games.games;

import lombok.experimental.UtilityClass;
import me.ollie.games.core.AbstractGameMap;
import me.ollie.games.games.survivalgames.SGMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@UtilityClass
public class MapCollectionFactory {

    public Collection<AbstractGameMap> getMapsFor(AbstractGame game) {
        if (game.getName().equals("Survival Games")) {
            World turbulence = Bukkit.getWorld("sg_turbulence");

            return List.of(new SGMap("Turbulence", turbulence, Set.of(
                    new Location(turbulence, 1078.5, 53.5, -46.5, 170, 0),
                    new Location(turbulence, 1084.5, 53.5, -48.5, 152, 0),
                    new Location(turbulence, 1089.5, 53.5, -51.5, 133, 0),
                    new Location(turbulence, 1093.5, 53.5, -55.5, 126, 0),
                    new Location(turbulence, 1096.5, 53.5, -60.5, 112, 0),
                    new Location(turbulence, 1097.5, 53.5, -65.5, 96, 0),
                    new Location(turbulence, 1097.5, 53.5, -71.5, 80, 0),
                    new Location(turbulence, 1095.5, 53.5, -78.5, 62, 0),
                    new Location(turbulence, 1092.5, 53.5, -82.5, 49, 0),
                    new Location(turbulence, 1087.5, 53.5, -86.5, 36, 0),
                    new Location(turbulence, 1083.5, 53.5, -89.5, 23, 0),
                    new Location(turbulence, 1078.5, 53.5, -90.5, 9, 0),
                    new Location(turbulence, 1072.5, 53.5, -90.5, -9, 0),
                    new Location(turbulence, 1067.5, 53.5, -89.5, -24, 0),
                    new Location(turbulence, 1061.5, 53.5, -85.5, -40, 0),
                    new Location(turbulence, 1057.5, 53.5, -81.5, -57, 0),
                    new Location(turbulence, 1054.5, 53.5, -76.5, -69, 0),
                    new Location(turbulence, 1053.5, 53.5, -71.5, -81, 0),
                    new Location(turbulence, 1053.5, 53.5, -65.5, -99, 0),
                    new Location(turbulence, 1055.5, 53.5, -59.5, -115, 0),
                    new Location(turbulence, 1058.5, 53.5, -65.5, -129, 0),
                    new Location(turbulence, 1062.5, 53.5, -50.5, -144, 0),
                    new Location(turbulence, 1067.5, 53.5, -47.5, -159, 0),
                    new Location(turbulence, 1072.5, 53.5, -46.5, -170, 0)
            )));
        } else {
            throw new IllegalArgumentException("this game doesn't exist boss big sad");
        }
    }
}
