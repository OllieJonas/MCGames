package me.ollie.games.util;

import lombok.experimental.UtilityClass;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@UtilityClass
public class PlayerUtil {

    public void reset(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setInvisible(false);
        player.setHealth(20D);
        player.setFireTicks(0);
    }
}
