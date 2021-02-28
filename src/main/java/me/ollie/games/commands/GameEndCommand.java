package me.ollie.games.commands;

import me.ollie.games.lobby.Lobby;
import me.ollie.games.lobby.LobbyManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GameEndCommand extends SubCommand {


    public GameEndCommand() {
        super("gameend", true, new String[]{"end"});
    }

    @Override
    public void run(Player sender, String[] args) {
        Lobby lobby = LobbyManager.getInstance().getLobbyFor(sender);

        if (lobby == null) {
            sender.sendMessage(ChatColor.GRAY + "yikedoodledo lobby is null");
            return;
        }
        sender.sendMessage(ChatColor.GRAY + "Force ending game... (SHOULD ONLY BE USED FOR TESTING OTHERWISE YA DUN FUCKED UP BUDDY)");
        lobby.getGame().endGame();
    }
}
