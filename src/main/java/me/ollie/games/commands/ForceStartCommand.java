package me.ollie.games.commands;

import me.ollie.games.lobby.Lobby;
import me.ollie.games.lobby.LobbyManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ForceStartCommand extends SubCommand {

    public ForceStartCommand() {
        super("forcestart", true, new String[]{"fs"});
    }

    @Override
    public void run(Player sender, String[] args) {
        Lobby lobby = LobbyManager.getInstance().getLobbyFor(sender);
        sender.sendMessage(ChatColor.GRAY + "Force starting game...");
        lobby.startEndingVoting();
    }
}
