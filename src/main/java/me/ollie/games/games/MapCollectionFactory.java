package me.ollie.games.games;

import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import me.ollie.games.core.AbstractGameMap;
import me.ollie.games.games.survivalgames.SGMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
@UtilityClass
public class MapCollectionFactory {

    private List<SGMap> sgMaps;

    public void initMaps() {
        sgMaps = new ArrayList<>();
        World turbulence = Bukkit.getWorld("sg_turbulence");
        World breezeIsland = Bukkit.getWorld("sg_breeze_island_2");
        World vareide = Bukkit.getWorld("sg_vareide");
        World drybonyValley = Bukkit.getWorld("sg_drybony_valley");

        sgMaps.add(new SGMap("Turbulence", turbulence, Lists.newArrayList(
                new Location(turbulence, 1078.5, 53.5, -46.5, 170, 0),
                new Location(turbulence, 1084.5, 53.5, -48.5, 152, 0),
                new Location(turbulence, 1089.5, 53.5, -51.5, 133, 0),
                new Location(turbulence, 1093.5, 53.5, -55.5, 126, 0),
                new Location(turbulence, 1096.5, 53.5, -60.5, 112, 0),
                new Location(turbulence, 1097.5, 53.5, -65.5, 96, 0),
                new Location(turbulence, 1097.5, 53.5, -71.5, 80, 0),
                new Location(turbulence, 1095.5, 53.5, -77.5, 62, 0),
                new Location(turbulence, 1092.5, 53.5, -82.5, 49, 0),
                new Location(turbulence, 1088.5, 53.5, -86.5, 36, 0),
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
                new Location(turbulence, 1058.5, 53.5, -54.5, -129, 0),
                new Location(turbulence, 1062.5, 53.5, -50.5, -144, 0),
                new Location(turbulence, 1067.5, 53.5, -47.5, -159, 0),
                new Location(turbulence, 1072.5, 53.5, -46.5, -170, 0)
        )));

        sgMaps.add(new SGMap("Breeze Island 2", breezeIsland, Lists.newArrayList(
                new Location(breezeIsland, -5.5, 67.5, 32.5, -105, -8),
                new Location(breezeIsland, -3.5, 67.5, 37.5, -120, -8),
                new Location(breezeIsland, 0.5, 67.5, 42.5, -135, -8),
                new Location(breezeIsland, 5.5, 67.5, 46.5, -150, -8),
                new Location(breezeIsland, 10.5, 67.5, 48.5, -165, -8),
                new Location(breezeIsland, 16.5, 67.5, 49.5, -180, -8),
                new Location(breezeIsland, 22.5, 67.5, 48.5, 165, -8),
                new Location(breezeIsland, 27.5, 67.5, 46.5, 150, -8),
                new Location(breezeIsland, 32.5, 67.5, 42.5, 135, -8),
                new Location(breezeIsland, 36.5, 67.5, 37.5, 120, -8),
                new Location(breezeIsland, 38.5, 67.5, 32.5, 105, -8),
                new Location(breezeIsland, 39.5, 67.5, 26.5, 90, -8),
                new Location(breezeIsland, 38.5, 67.5, 20.5, 75, -8),
                new Location(breezeIsland, 36.5, 67.5, 15.5, 60, -8),
                new Location(breezeIsland, 32.5, 67.5, 10.5, 45, -8),
                new Location(breezeIsland, 27.5, 67.5, 6.5, 30, -8),
                new Location(breezeIsland, 22.5, 67.5, 4.5, 15, -8),
                new Location(breezeIsland, 16.5, 67.5, 3.5, 0, -8),
                new Location(breezeIsland, 10.5, 67.5, 4.5, -15, -8),
                new Location(breezeIsland, 5.5, 67.5, 6.5, -30, -8),
                new Location(breezeIsland, 0.5, 67.5, 10.5, -45, -8),
                new Location(breezeIsland, -3.5, 67.5, 15.5, -60, -8),
                new Location(breezeIsland, -5.5, 67.5, 20.5, -75, -8),
                new Location(breezeIsland, -6.5, 67.5, 26.5, -90, -8)
        )));

        sgMaps.add(new SGMap("Vareide", vareide, Lists.newArrayList(
                new Location(vareide, -1582.5, 60.5, -634.5, -15, 0),
                new Location(vareide, -1588.5, 60.5, -632.5, -30, 0),
                new Location(vareide, -1594.5, 60.5, -628.5, -45, 0),
                new Location(vareide, -1598.5, 60.5, -622.5, -65, 0),
                new Location(vareide, -1600.5, 60.5, -616.5, -75, 0),
                new Location(vareide, -1601.5, 60.5, -610.5, -90, 0),
                new Location(vareide, -1600.5, 60.5, -604.5, -105, 0),
                new Location(vareide, -1598.5, 60.5, -598.5, -120, 0),
                new Location(vareide, -1594.5, 60.5, -592.5, -135, 0),
                new Location(vareide, -1588.5, 60.5, -588.5, -150, 0),
                new Location(vareide, -1582.5, 60.5, -586.5, -165, 0),
                new Location(vareide, -1576.5, 60.5, -585.5, -180, 0),
                new Location(vareide, -1570.5, 60.5, -586.5, 165, 0),
                new Location(vareide, -1564.5, 60.5, -588.5, 150, 0),
                new Location(vareide, -1558.5, 60.5, -592.5, 135, 0),
                new Location(vareide, -1554.5, 60.5, -598.5, 120, 0),
                new Location(vareide, -1552.5, 60.5, -604.5, 105, 0),
                new Location(vareide, -1551.5, 60.5, -610.5, 90, 0),
                new Location(vareide, -1552.5, 60.5, -616.5, 75, 0),
                new Location(vareide, -1554.5, 60.5, -622.5, 60, 0),
                new Location(vareide, -1558.5, 60.5, -628.5, 45, 0),
                new Location(vareide, -1564.5, 60.5, -632.5, 30, 0),
                new Location(vareide, -1570.5, 60.5, -634.5, 15, 0),
                new Location(vareide, -1576.5, 60.5, -635.5, 0, 0)
        )));

        sgMaps.add(new SGMap("Drybony Valley", drybonyValley, Lists.newArrayList(
                new Location(drybonyValley, -1208.5 , 18.5 , 312.5 , -15 , 0),
                new Location(drybonyValley, -1213.5 , 18.5 , 314.5 , -30 , 0),
                new Location(drybonyValley, -1217.5 , 18.5 , 317.5 , -44 , 0),
                new Location(drybonyValley, -1220.5 , 18.5 , 321.5 , -58 , 0),
                new Location(drybonyValley, -1222.5 , 18.5 , 326.5 , -75 , 0),
                new Location(drybonyValley, -1223.5 , 18.5 , 331.5 , -90 , 0),
                new Location(drybonyValley, -1222.5 , 18.5 , 336.5 , -104 , 0),
                new Location(drybonyValley, -1220.5 , 18.5 , 341.5 , -121 , 0),
                new Location(drybonyValley, -1217.5 , 18.5 , 345.5 , -133 , 0),
                new Location(drybonyValley, -1213.5 , 18.5 , 348.5 , -149 , 0),
                new Location(drybonyValley, -1208.5 , 18.5 , 350.5 , -165 , 0),
                new Location(drybonyValley, -1203.5 , 18.5 , 351.5 , -180 , 0),
                new Location(drybonyValley, -1198.5 , 18.5 , 350.5 , 164 , 0),
                new Location(drybonyValley, -1193.5 , 18.5 , 348.5 , 149 , 0),
                new Location(drybonyValley, -1189.5 , 18.5 , 345.5 , 137 , 0),
                new Location(drybonyValley, -1186.5 , 18.5 , 341.5 , 121 , 0),
                new Location(drybonyValley, -1184.5 , 18.5 , 336.5 , 104 , 0),
                new Location(drybonyValley, -1183.5 , 18.5 , 331.5 , 91 , 0),
                new Location(drybonyValley, -1184.5 , 18.5 , 326.5 , 76 , 0),
                new Location(drybonyValley, -1186.5 , 18.5 , 321.5 , 59 , 0),
                new Location(drybonyValley, -1189.5 , 18.5 , 317.5 , 46 , 0),
                new Location(drybonyValley, -1193.5 , 18.5 , 314.5 , 31 , 0),
                new Location(drybonyValley, -1198.5 , 18.5 , 312.5 , 14 , 0),
                new Location(drybonyValley, -1203.5 , 18.5 , 311.5 , 0 , 0)
        )));
    }

    public Collection<AbstractGameMap> getMapsFor(String name) {
        List<AbstractGameMap> maps;

        if (name.equals("Survival Games")) {
            maps = new ArrayList<>(sgMaps);
        } else {
            throw new IllegalArgumentException("Game doesn't exist!");
        }

        if (maps.size() > 4) {
            Collections.shuffle(maps);
            return maps.subList(0, 4);
        } else {
            return maps;
        }
    }
    public Collection<AbstractGameMap> getMapsFor(AbstractGame game) {
        return getMapsFor(game.getName());
    }
}
