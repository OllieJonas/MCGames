package me.ollie.games.commands.test;

import me.ollie.games.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class DrybonyValleyCommand extends SubCommand {

    public DrybonyValleyCommand() {
        super("drybonyvalley", false, new String[]{"dv"});
    }

    @Override
    public void run(Player sender, String[] args) {
        sender.teleport(new Location(Bukkit.getWorld("sg_drybony_valley"), -1202, 20, 326));
        sender.setAllowFlight(true);
        sender.setFlying(true);
        sender.sendMessage(ChatColor.GRAY + "Teleported to drybony valley!");
    }
}
