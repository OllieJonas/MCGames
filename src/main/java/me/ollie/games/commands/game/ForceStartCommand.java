package me.ollie.games.commands.game;

import me.ollie.games.commands.SubCommand;
import me.ollie.games.lobby.Lobby;
import me.ollie.games.lobby.LobbyManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ForceStartCommand extends SubCommand {

    public ForceStartCommand() {
        super("forcestart", true, new String[]{"fs", "start"});
    }

    @Override
    public void run(Player sender, String[] args) {
        Lobby lobby = LobbyManager.getInstance().getLobbyFor(sender);
        if (lobby == null) {
            sender.sendMessage(ChatColor.RED + "You're not in a lobby! :(");
            return;
        }
        sender.sendMessage(ChatColor.GRAY + "Force starting game...");
        lobby.startEndingVoting();
    }
}
