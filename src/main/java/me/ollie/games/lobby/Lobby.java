package me.ollie.games.lobby;

import lombok.Getter;
import me.ollie.games.games.AbstractGame;
import me.ollie.games.games.MapCollectionFactory;
import me.ollie.games.lobby.vote.MapVote;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

@Getter
public class Lobby {

    public enum State {
        READY(Material.EMERALD_BLOCK),
        STARTING(Material.GOLD_BLOCK),
        IN_GAME(Material.REDSTONE_BLOCK);

        @Getter
        private final Material material;

        State(Material material) {
            this.material = material;
        }

        public BiConsumer<Player, Lobby> actionOnClickFactory() {
            if (this == READY || this == STARTING) {
                return (p, l) -> l.addPlayer(p);
            } else {
                return (p, l) -> p.sendMessage("Game already started! :(");
            }
        }
    }

    private AbstractGame game;

    @Getter
    private State state;

    private MapVote mapVote;

    private int maxPlayers = 24;

    private Location spawnPoint;

    private final Set<Player> players;

    private final boolean requireForceStart;

    public Lobby(AbstractGame game, boolean requireForceStart) {
        this.game = game;
        this.requireForceStart = requireForceStart;
        this.state = State.READY;
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
