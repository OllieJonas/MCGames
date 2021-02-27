package me.ollie.games.lobby;

import lombok.Getter;
import me.ollie.games.Games;
import me.ollie.games.games.AbstractGame;
import me.ollie.games.games.MapCollectionFactory;
import me.ollie.games.gui.GUIManager;
import me.ollie.games.lobby.vote.MapVote;
import me.ollie.games.lobby.vote.MapVoteGUI;
import me.ollie.games.util.Countdown;
import me.ollie.games.util.GameBossBar;
import me.ollie.games.util.SetUtils;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

@Getter
public class Lobby {

    private static final Location GAMES_LOBBY_LOCATION = new Location(Bukkit.getWorld("lobby"), -898.5, 98.5, 1158.5);

    private static final GameBossBar WAITING_FOR_PLAYERS = new GameBossBar(
            BossBar.bossBar(Component.text(ChatColor.AQUA + "Waiting for players."), 1F, BossBar.Color.BLUE, BossBar.Overlay.NOTCHED_20),
            BossBar.bossBar(Component.text(ChatColor.AQUA + "Waiting for players.."), 1F, BossBar.Color.BLUE, BossBar.Overlay.NOTCHED_20),
            BossBar.bossBar(Component.text(ChatColor.AQUA + "Waiting for players..."), 1F, BossBar.Color.BLUE, BossBar.Overlay.NOTCHED_20));


    private AbstractGame game;

    private Countdown countdown;

    private GameBossBar currentBossBar;

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
        this.currentBossBar = WAITING_FOR_PLAYERS.run();
        this.state = State.READY;
        this.mapVote = new MapVote(MapCollectionFactory.getMapsFor(game));
        this.players = new HashSet<>();
        this.spawnPoint = GAMES_LOBBY_LOCATION;
        this.countdown = new Countdown("Voting ends in ", this.players, 30);
        this.countdown.start();
    }

    public void addPlayer(Player player) {
        players.add(player);
        hidePlayers(player);

        player.teleport(spawnPoint);
        countdown.addPlayer(player);
        // currentBossBar.showTo(player);

        LobbyItems.addItems(player);
        sendMessageToAll(player.getName() + " has joined!");
    }

    public void removePlayer(Player player) {
        players.remove(player);
        showPlayers(player); // show all players in hub

        LobbyItems.resetItems(player);
        countdown.removePlayer(player);

        // currentBossBar.hideFrom(player);

        sendMessageToAll(player.getName() + " has left!");
        player.sendMessage(ChatColor.GRAY + "You left the lobby!");
        player.teleport(Games.SPAWN);
    }


    private void hidePlayers(Player player) {
        SetUtils.difference(new HashSet<>(Bukkit.getOnlinePlayers()), players).forEach(t -> player.hidePlayer(Games.getInstance(), t));
        players.forEach(p -> p.showPlayer(Games.getInstance(), player));
    }

    private void showPlayers(Player player) {
        SetUtils.difference(new HashSet<>(Bukkit.getOnlinePlayers()), players).forEach(t -> player.showPlayer(Games.getInstance(), t));
        players.forEach(p -> p.hidePlayer(Games.getInstance(), player));
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

    public void openMapVotingGuiFor(Player player) {
        GUIManager.getInstance().openGuiFor(player, new MapVoteGUI(mapVote).getGui());
    }

    public enum State {
        READY(Material.EMERALD_BLOCK),
        STARTING(Material.GOLD_BLOCK),
        IN_GAME(Material.REDSTONE_BLOCK);

        @Getter
        private final Material material;

        State(Material material) {
            this.material = material;
        }

        public BiConsumer<Player, Integer> actionOnClickFactory() {
            if (this == READY || this == STARTING) {
                return (p, l) -> LobbyManager.getInstance().joinLobby(p, l);
            } else {
                return (p, l) -> p.sendMessage("Game already started! :(");
            }
        }
    }

}
