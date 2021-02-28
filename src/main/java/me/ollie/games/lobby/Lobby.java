package me.ollie.games.lobby;

import lombok.Getter;
import me.ollie.games.Games;
import me.ollie.games.core.AbstractGameMap;
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

    private final GameBossBar waitingForPlayers;

    @Getter
    private State state;

    private final MapVote mapVote;

    private final int maxPlayers = 24;

    private int minimumPlayersToStart = 15;

    private final Location spawnPoint;

    private final Set<Player> players;

    private final boolean requireForceStart;

    public Lobby(AbstractGame game, boolean requireForceStart) {
        this.game = game;
        this.requireForceStart = requireForceStart;
        this.waitingForPlayers = WAITING_FOR_PLAYERS.run();
        this.state = State.WAITING;
        this.mapVote = new MapVote(MapCollectionFactory.getMapsFor(game));
        this.players = new HashSet<>();
        this.countdown = new Countdown("random lmao just gotta fill this biz weewoo :))", new HashSet<>(), 1);
        this.spawnPoint = GAMES_LOBBY_LOCATION;
    }

    public void addPlayer(Player player) {
        players.add(player);
        hidePlayers(player);

        player.teleport(spawnPoint);
        countdown.addPlayer(player);

        waitingForPlayers.showTo(player);

        LobbyItems.addItems(player);
        sendMessageToAll(ChatColor.AQUA + player.getName() + ChatColor.GRAY + " has joined! (" + ChatColor.AQUA + players.size() + ChatColor.GRAY + " / " + ChatColor.AQUA + maxPlayers + ChatColor.GRAY + ")");

        if (!requireForceStart && players.size() >= minimumPlayersToStart)
            startEndingVoting();
    }

    public void removePlayer(Player player) {
        players.remove(player);
        showPlayers(player); // show all players in hub

        LobbyItems.resetItems(player);

        if (state.hasCountdown())
            countdown.removePlayer(player);

        waitingForPlayers.hideFrom(player);

        sendMessageToAll(ChatColor.AQUA + player.getName() + ChatColor.GRAY + " has left! (" + ChatColor.AQUA + players.size() + ChatColor.GRAY + " / " + ChatColor.AQUA + maxPlayers + ChatColor.GRAY + ")");
        player.sendMessage(ChatColor.GRAY + "You left the lobby!");
        player.teleport(Games.SPAWN);
    }

    public void startEndingVoting() {
        this.state = State.VOTING_ENDING;
        waitingForPlayers.destroy();
        this.countdown = new Countdown("Voting ends in ", this.players, 30, this::endVoting).start();
    }

    public void endVoting() {
        this.mapVote.setCanVote(false);
        AbstractGameMap selectedMap = mapVote.getLeadingMap();
        players.forEach(p -> p.sendMessage(titleMessage("Voting has ended! Selected map: " + ChatColor.AQUA + selectedMap.getName())));
        game.load(selectedMap);
        gameStarting();
    }

    public void gameStarting() {
        this.state = State.STARTING;
        this.countdown = new Countdown("Teleporting to map in ", this.players, 30, this::startGame).start();
    }

    public void startGame() {
        this.state = State.IN_GAME;
        game.startGame(players);
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
        players.forEach(p -> p.sendMessage(titleMessage(message)));
    }

    public void openMapVotingGuiFor(Player player) {
        GUIManager.getInstance().openGuiFor(player, new MapVoteGUI(mapVote).getGui());
    }

    public void setSpectator(Player player) {

    }

    public enum State {

        WAITING(Material.EMERALD_BLOCK),
        VOTING_ENDING(Material.GOLD_BLOCK),
        STARTING(Material.GOLD_BLOCK),
        IN_GAME(Material.REDSTONE_BLOCK);

        @Getter
        private final Material material;

        State(Material material) {
            this.material = material;
        }

        public BiConsumer<Player, Integer> actionOnClickFactory() {
            if (this == WAITING || this == VOTING_ENDING || this == STARTING) {
                return (p, l) -> LobbyManager.getInstance().joinLobby(p, l);
            } else {
                return (p, l) -> p.sendMessage("Game already started! :(");
            }
        }

        public boolean hasCountdown() {
            return this == VOTING_ENDING || this == STARTING;
        }

        public boolean isInGame() {
            return this == IN_GAME;
        }
    }

    public String titleMessage(String message) {
        return ChatColor.DARK_AQUA + "" + ChatColor.BOLD + game.getName() + ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + message;
    }

}
