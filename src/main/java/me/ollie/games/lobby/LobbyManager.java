package me.ollie.games.lobby;

import lombok.Getter;
import me.ollie.games.games.AbstractGame;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class LobbyManager {

    @Getter
    private static final LobbyManager instance = new LobbyManager();
    @Getter
    private final Map<Integer, Lobby> lobbies;
    private Map<UUID, Integer> players;
    private int noLobbies;

    public LobbyManager() {
        this.lobbies = new HashMap<>();
        this.players = new HashMap<>();
    }

    public void createLobby(AbstractGame game, boolean requiresForceStart) {
        lobbies.put(noLobbies++, new Lobby(game, requiresForceStart));
    }

    public void removeLobby(int lobby) {
        lobbies.remove(lobby);
        this.players = players.entrySet().stream().filter(e -> e.getValue() != lobby).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void joinLobby(Player player, int id) {

        if (isInLobby(player))
            leaveLobby(player);

        System.out.println("Join Lobby ID: " + id);
        players.put(player.getUniqueId(), id);
        Lobby lobby = lobbies.get(id);

        if (lobby == null) {
            player.sendMessage(ChatColor.RED + "Can't find lobby! Attempted Lobby ID: " + id + ". Lobby IDs: " + lobbies.keySet().stream().map(String::valueOf).collect(Collectors.joining(", ")));
            return;
        }

        lobby.addPlayer(player);
    }

    public void leaveLobby(Player player) {

        int id = players.get(player.getUniqueId());
        Lobby lobby = lobbies.get(id);

        if (lobby == null) {
            player.sendMessage(ChatColor.RED + "Can't find lobby!");
            return;
        }

        players.remove(player.getUniqueId());

        lobby.removePlayer(player);
    }

    public Lobby getLobbyFor(Player player) {
        return lobbies.get(players.get(player.getUniqueId()));
    }

    public int getLobbyIdFor(Player player) {
        return players.get(player.getUniqueId());
    }

    public boolean isInLobby(Player player) {
        return players.containsKey(player.getUniqueId()) && !getLobbyFor(player).getState().isInGame();
    }

    public boolean isInGame(Player player) {
        return players.containsKey(player.getUniqueId()) && getLobbyFor(player).getState().isInGame();
    }
}
