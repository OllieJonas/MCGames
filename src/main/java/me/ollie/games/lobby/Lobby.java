package me.ollie.games.lobby;

import lombok.Getter;
import me.ollie.games.games.AbstractGame;
import me.ollie.games.games.MapCollectionFactory;
import me.ollie.games.lobby.vote.MapVote;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Lobby {

    private AbstractGame game;

    private MapVote mapVote;

    private int maxPlayers = 24;

    private Location spawnPoint;

    private final Set<Player> players;

    private final boolean requireForceStart;

    public Lobby(AbstractGame game, boolean requireForceStart) {
        this.game = game;
        this.requireForceStart = requireForceStart;
        this.mapVote = new MapVote(MapCollectionFactory.getMapsFor(game));
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

    public void reload() {

    }

    public void setGame(AbstractGame game) {
        this.game = game;
        reload();
    }

    public void sendMessageToAll(String message) {
        players.forEach(p -> p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + game.getName() + ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + message));
    }
}
