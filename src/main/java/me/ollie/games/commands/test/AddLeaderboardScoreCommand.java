package me.ollie.games.commands.test;

import me.ollie.games.commands.SubCommand;
import me.ollie.games.leaderboard.Leaderboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AddLeaderboardScoreCommand extends SubCommand {

    public AddLeaderboardScoreCommand() {
        super("addscore", true, new String[]{"add"});
    }


    @Override
    public void run(Player sender, String[] args) {
        if (args.length == 0 || args.length > 2) {
            sender.sendMessage(ChatColor.RED + "Invalid args - /test addscore <amount> <player>");
            return;
        }

        Player target = args.length == 1 ? sender : Bukkit.getPlayer(args[1]);
        int amount;
        if (target != null) {
            try {
                amount = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Not a number!");
                return;
            }
            Leaderboard.getInstance().addScore(target.getName(), amount);
            sender.sendMessage(ChatColor.GREEN + "Added " + amount + " to " + target.getName() + "!");
        }

    }
}
