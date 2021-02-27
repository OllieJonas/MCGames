package me.ollie.games.commands;

import me.ollie.games.lobby.Lobby;
import me.ollie.games.lobby.LobbyManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class VoteCommand extends SubCommand {
    public VoteCommand() {
        super("vote", false, new String[]{"v"});
    }

    @Override
    public void run(Player sender, String[] args) {
        Lobby currLobby = LobbyManager.getInstance().getLobbyFor(sender);
        if (currLobby != null)
            currLobby.openMapVotingGuiFor(sender);
        else
            sender.sendMessage(ChatColor.RED + "You're not in a lobby!");
    }
}
