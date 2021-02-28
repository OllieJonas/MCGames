package me.ollie.games.games.survivalgames;

import lombok.Getter;
import lombok.Setter;
import me.ollie.games.api.events.GamePlayerKillEvent;
import me.ollie.games.core.AbstractGameMap;
import me.ollie.games.games.AbstractGame;
import me.ollie.games.util.Countdown;
import me.ollie.games.util.MessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.*;

@Getter
@Setter
public class SurvivalGames extends AbstractGame {

    static final String TITLE = ChatColor.DARK_AQUA + "Survival Games";

    private SGGameLogic gameLogic;

    private Set<Location> lootedChests;

    private Set<Player> players;

    private Set<Player> alivePlayers;

    private Set<Player> disconnectedPlayers;

    private Map<UUID, Integer> playerKills;

    @Setter
    private Phase phase;

    @Setter
    private Countdown currentCountdown;

    private SGMap map;

    public SurvivalGames() {
        super("Survival Games");
        this.players = new HashSet<>();
        this.alivePlayers = new HashSet<>();
        this.lootedChests = new HashSet<>();
        this.playerKills = new HashMap<>();
        this.phase = Phase.PRE_INIT;
        this.gameLogic = new SGGameLogic(this);
    }

    @Override
    public void load(AbstractGameMap map) {
        MessageUtil.broadcast("am here boss");
        if (!map.getClass().isAssignableFrom(SGMap.class))
            throw new IllegalStateException("this is seriously bad wtf are you doing lmfaooooo (map isnt assignable to sgmap)");

        this.map = (SGMap) map;
    }

    @Override
    public void startGame(Set<Player> players) {
        gameLogic.startGame(players);
    }

    @Override
    public void endGame() {
        gameLogic.endGame();
    }

    public void addLootedChest(Location location) {
        lootedChests.add(location);
    }

    public void handlePlayerKill(PlayerDeathEvent event, Player killer, Player victim) {
        Bukkit.getPluginManager().callEvent(new GamePlayerKillEvent<>(killer, victim, this));
        playerKills.put(killer.getUniqueId(), playerKills.get(killer.getUniqueId()) + 1);

        int remainingPlayers = alivePlayers.size();
        broadcast(ChatColor.AQUA + victim.getName() + ChatColor.GRAY + " has been slain by " + ChatColor.AQUA + killer.getName() + "! " + ChatColor.GRAY + "There are " + ChatColor.AQUA + remainingPlayers + ChatColor.GRAY + " remaining!");

        doHandleDeath(event);
    }

    public void winner(Player player) {
        player.sendMessage("you dun did it boi");
        endGame();
    }

    public boolean isChestAlreadyLooted(Location location) {
        return lootedChests.contains(location);
    }

    public void broadcast(String message) {
        players.forEach(p -> p.sendMessage(ChatColor.DARK_AQUA + "Survival Games " + ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + message));
    }

    public void handlePlayerDeath(PlayerDeathEvent event) {
        String message = event.getDeathMessage();
    }

    private void doHandleDeath(PlayerDeathEvent event) {
        int remainingPlayers = alivePlayers.size();

        if (remainingPlayers == 2) { // deathmatch
            currentCountdown.interrupt();
            this.currentCountdown = new Countdown("Deathmatch starts in ", players, 60, gameLogic::deathmatch);
        }

        if (remainingPlayers == 1) {
            endGame();
        }
    }

    public void sendTitleCard(Player p) {
        p.showTitle(Title.title(Component.text(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Survival Games"), Component.text(ChatColor.AQUA + "by ya boy")));
    }


    public enum Phase {
        PRE_INIT,
        COUNTDOWN,
        GRACE_PERIOD,
        GAME,
        DEATHMATCH,
        END;

        public boolean isInGame() {
            return this == GRACE_PERIOD || this == GAME;
        }
    }
}
