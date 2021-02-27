package me.ollie.games.commands;

import me.ollie.games.lobby.Lobby;
import me.ollie.games.lobby.LobbyManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LeaveCommand extends SubCommand {


    public LeaveCommand() {
        super("leave", false, new String[]{"l"});
    }

    @Override
    public void run(Player sender, String[] args) {
        Lobby lobby = LobbyManager.getInstance().getLobbyFor(sender);

        if (lobby != null) {
            LobbyManager.getInstance().leaveLobby(sender);
        } else {
            sender.sendMessage(ChatColor.RED + "You're not currently in a lobby! :(");
        }
    }
}
