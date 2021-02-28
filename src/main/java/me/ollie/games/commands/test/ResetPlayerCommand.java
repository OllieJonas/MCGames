package me.ollie.games.commands.test;

import me.ollie.games.commands.SubCommand;
import me.ollie.games.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ResetPlayerCommand extends SubCommand {

    public ResetPlayerCommand() {
        super("resetplayer", true, new String[]{"rp"});
    }
    @Override
    public void run(Player sender, String[] args) {
        if (args.length == 0) {
            PlayerUtil.reset(sender);
            sender.sendMessage(ChatColor.GRAY + "You have been reset!");
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null)
                sender.sendMessage(ChatColor.GRAY + "Player is null!");
            else {
                PlayerUtil.reset(target);
                sender.sendMessage(ChatColor.GRAY + "Reset " + ChatColor.AQUA + args[0]);
                target.sendMessage(ChatColor.GRAY + "You have been reset!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid arguments!");
        }
    }
}
