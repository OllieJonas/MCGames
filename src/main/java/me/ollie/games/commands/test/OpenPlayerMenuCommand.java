package me.ollie.games.commands.test;

import me.ollie.games.commands.SubCommand;
import me.ollie.games.gui.GUIManager;
import me.ollie.games.gui.PlayerListGUI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class OpenPlayerMenuCommand extends SubCommand {

    public OpenPlayerMenuCommand() {
        super("playermenu", true, NO_ALIASES);
    }

    @Override
    public void run(Player sender, String[] args) {
        GUIManager.getInstance().openGuiFor(sender, new PlayerListGUI("Test sending messages",
                player -> new String[]{ChatColor.GRAY + "Click to send " + ChatColor.GREEN + player.getName() + ChatColor.GRAY + " a message!"},
                (s, t) -> t.sendMessage(ChatColor.AQUA + s.getName() + ChatColor.GRAY + " says hello!")).getGui());
    }
}
