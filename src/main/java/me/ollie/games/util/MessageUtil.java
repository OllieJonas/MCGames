package me.ollie.games.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

@UtilityClass
public class MessageUtil {

    public void broadcast(String message) {
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "Broadcast" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + message));
    }
}
