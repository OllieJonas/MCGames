package me.ollie.games.lobby;

import me.ollie.games.games.AbstractGame;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Lobby {

    private final AbstractGame game;

    private final Set<Player> players;

    public Lobby(AbstractGame game) {
        this.game = game;
        this.players = new HashSet<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
        sendMessageToAll(player.getName() + " has joined!");
    }

    public void removePlayer(Player player) {
        players.remove(player);
        sendMessageToAll(player.getName() + " has left!");
    }

    public void sendMessageToAll(String message) {
        players.forEach(p -> p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + game.getName() + ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + message));
    }
}
