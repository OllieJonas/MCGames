package me.ollie.games.commands.test;

import me.ollie.games.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClearInventoryCommand extends SubCommand {

    public ClearInventoryCommand() {
        super("clearinventory", true, new String[]{"ci"});
    }

    @Override
    public void run(Player sender, String[] args) {
        sender.sendMessage(ChatColor.GRAY + "Cleared inventory!");
        sender.getInventory().clear();
    }
}
