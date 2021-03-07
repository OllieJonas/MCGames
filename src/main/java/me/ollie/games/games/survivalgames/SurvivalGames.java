package me.ollie.games.games.survivalgames;

import lombok.Getter;
import lombok.Setter;
import me.ollie.games.core.AbstractGameMap;
import me.ollie.games.games.AbstractGame;
import me.ollie.games.games.SpectatorItems;
import me.ollie.games.games.survivalgames.kit.KitManager;
import me.ollie.games.games.survivalgames.scoreboard.SGScoreboardManager;
import me.ollie.games.lobby.LobbyManager;
import me.ollie.games.util.Countdown;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

@Getter
@Setter
public class SurvivalGames extends AbstractGame {

    static final String TITLE = ChatColor.DARK_AQUA + "Survival Games";

    private SGGameLogic gameLogic;

    private Set<Location> lootedChests;

    private Set<Player> alivePlayers;

    private Set<Player> disconnectedPlayers;

    private Map<String, Integer> playerKills;

    private KitManager kitManager;

    private Phase phase;

    private Countdown currentCountdown;

    private SGMap map;

    private SGScoreboardManager scoreboardManager;

    public SurvivalGames() {
        super("Survival Games");
        this.alivePlayers = new HashSet<>();
        this.lootedChests = new HashSet<>();
        this.playerKills = new HashMap<>();
        this.phase = Phase.PRE_INIT;
        this.gameLogic = new SGGameLogic(this);
    }

    @Override
    public void load(AbstractGameMap map) {
        if (!map.getClass().isAssignableFrom(SGMap.class))
            throw new IllegalStateException("this is seriously bad wtf are you doing lmfaooooo (map isnt assignable to sgmap)");

        this.map = (SGMap) map;
    }

    @Override
    public void startGame(Set<Player> players) {
        this.kitManager = new KitManager(players);
        this.scoreboardManager = new SGScoreboardManager(this);
        gameLogic.startGame(players);
        scoreboardManager.addPlayers(players);
    }

    @Override
    public void endGame(boolean forced) {
        currentCountdown.destroy();
        scoreboardManager.destroy();
        Player winner = gameLogic.endGame(forced);
    }

    public void addLootedChest(Location location) {
        lootedChests.add(location);
    }

    public boolean isChestAlreadyLooted(Location location) {
        return lootedChests.contains(location);
    }

    public void broadcast(String message) {
        players.forEach(p -> p.sendMessage(ChatColor.DARK_AQUA + "Survival Games " + ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + message));
    }

    public void handlePlayerKill(PlayerDeathEvent event, Player killer, Player victim) {
        alivePlayers.remove(event.getEntity());

        // Bukkit.getPluginManager().callEvent(new GamePlayerKillEvent<>(killer, victim, this));
        int newKills = playerKills.get(killer.getName()) + 1;

        killer.sendMessage(ChatColor.GRAY + "Kill! Total Kills: " + ChatColor.AQUA + newKills);
        killer.sendMessage(ChatColor.GRAY + "Applied " + ChatColor.AQUA + "Strength I" + ChatColor.GRAY + " for " + ChatColor.AQUA + "5 seconds!");
        killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5 * 20, 0));

        playerKills.put(killer.getName(), newKills);

        int remainingPlayers = alivePlayers.size();
        broadcast(ChatColor.AQUA + victim.getName() + ChatColor.GRAY + " has been slain by " + ChatColor.AQUA + killer.getName() + "! " + ChatColor.GRAY + "There are " + ChatColor.AQUA + remainingPlayers + ChatColor.GRAY + " remaining!");
        event.setDeathMessage("");
        doHandleDeath(event);
    }

    public void handlePlayerDeath(PlayerDeathEvent event) {
        alivePlayers.remove(event.getEntity());

        String message = event.getDeathMessage();
        broadcast(ChatColor.AQUA + message + "! " + ChatColor.GRAY + "There are " + ChatColor.AQUA + alivePlayers.size() + ChatColor.GRAY + " players remaining!");

        doHandleDeath(event);
    }

    private void doHandleDeath(PlayerDeathEvent event) {
        event.setDeathMessage("");

        int remainingPlayers = alivePlayers.size();

        setSpectator(event.getEntity());

        scoreboardManager.redraw();

        if (remainingPlayers == 2) { // deathmatch
            gameLogic.startDeathmatchCounter();
        }

        if (remainingPlayers == 1) {
            endGame();
        }
    }

    public void setSpectator(Player player) {
        player.showTitle(Title.title(Component.text(ChatColor.RED + "You died! :("), Component.text(ChatColor.GRAY + "You can spectate others by right clicking the clock")));
        player.setAllowFlight(true);
        player.setGameMode(GameMode.CREATIVE);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60 * 60 * 20, 1));
        spectators.add(player);
        SpectatorItems.give(player);
    }

    public void sendTitleCard(Player p) {
        p.showTitle(Title.title(Component.text(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Survival Games"), Component.text(ChatColor.AQUA + "by ya boy")));
    }

    public boolean isAlive(Player player) {
        return alivePlayers.contains(player);
    }

    public boolean isSpectator(Player player) {
        return spectators.contains(player);
    }

    public int getKills(Player player) {
        return playerKills.get(player.getName());
    }

    public enum Phase {
        PRE_INIT,
        COUNTDOWN,
        GRACE_PERIOD,
        GAME,
        DEATHMATCH_STARTING,
        DEATHMATCH,
        END;

        public boolean isInGame() {
            return this == GRACE_PERIOD || this == GAME;
        }
    }
}
