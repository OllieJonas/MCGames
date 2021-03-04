package me.ollie.games.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

@UtilityClass
public class MiscUtil {

    public void disableAchievements() {
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "gamerule announceAchievements false");
    }
}
