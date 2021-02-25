package me.ollie.games.lobby;

import lombok.Getter;
import me.ollie.games.games.AbstractGame;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LobbyManager {

    @Getter
    private final Map<Integer, Lobby> lobbies;

    private int noLobbies;

    private final Map<Player, Lobby> players;

    @Getter
    private static final LobbyManager instance = new LobbyManager();

    public LobbyManager() {
        this.lobbies = new HashMap<>();
        this.players = new HashMap<>();
    }

    public void createLobby(AbstractGame game, boolean requiresForceStart) {
        lobbies.put(noLobbies++, new Lobby(game, requiresForceStart));
    }

    public void joinLobby(Player player, int id) {
        players.put(player, lobbies.get(id));
        Lobby lobby = lobbies.get(id);

        if (lobby == null) {
            player.sendMessage(ChatColor.RED + "Can't find lobby!");
            return;
        }

        lobby.addPlayer(player);
    }

    public void leaveLobby(Player player, int id) {
        players.remove(player);

        Lobby lobby = lobbies.get(id);

        if (lobby == null) {
            player.sendMessage(ChatColor.RED + "Can't find lobby!");
            return;
        }

        lobby.removePlayer(player);
    }
}
