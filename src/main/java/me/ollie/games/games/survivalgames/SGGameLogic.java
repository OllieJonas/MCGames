package me.ollie.games.games.survivalgames;

import me.ollie.games.Games;
import me.ollie.games.leaderboard.Leaderboard;
import me.ollie.games.lobby.LobbyItems;
import me.ollie.games.util.Countdown;
import me.ollie.games.util.FireworkUtil;
import me.ollie.games.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class SGGameLogic {

    private final SurvivalGames survivalGames;

    public SGGameLogic(SurvivalGames survivalGames) {
        this.survivalGames = survivalGames;
    }

    public void startGame(Set<Player> players) {
        survivalGames.setPlayers(players);
        survivalGames.setAlivePlayers(players);

        players.forEach(p -> {
            survivalGames.getPlayerKills().put(p.getUniqueId(), 0); // fill playerkills
            LobbyItems.resetItems(p);
            p.setFlying(false);
            p.setAllowFlight(false);
            survivalGames.sendTitleCard(p);
        });

        List<Player> playerList = new ArrayList<>(players);
        List<Location> spawns = new ArrayList<>(survivalGames.getMap().getSpawnLocations());

        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).teleport(spawns.get(i));
        }

        survivalGames.setPhase(SurvivalGames.Phase.COUNTDOWN);
        survivalGames.setCurrentCountdown(new Countdown("Game starting in ", players, 30, this::startGracePeriod).setTitle(SurvivalGames.TITLE).start());
    }

    private void startGracePeriod() {
        survivalGames.setPhase(SurvivalGames.Phase.GRACE_PERIOD);
        survivalGames.getPlayers().forEach(p -> p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60 * 20, 5)));
        survivalGames.setCurrentCountdown(new Countdown("Resistance ends in ", survivalGames.getPlayers(), 60, () -> startChestRefill(new AtomicInteger(0))).setTitle(SurvivalGames.TITLE).setDisplaySubtitle(false).start());
    }

    private void startChestRefill(AtomicInteger counter) {
        counter.getAndIncrement();

        if (counter.get() > 4) { // 3 refills before deathmatch starts
            startDeathmatchCounter();
            return;
        }

        survivalGames.setPhase(SurvivalGames.Phase.GAME);
        survivalGames.setCurrentCountdown(new Countdown("Chest Refill No " + counter.get() + " in ", survivalGames.getPlayers(), 240, () -> {
            chestRefill();
            startChestRefill(counter);
        }).setTitle(SurvivalGames.TITLE).start());
    }

    private void chestRefill() {
        survivalGames.setLootedChests(new HashSet<>());
    }

    public void startDeathmatchCounter() {
        survivalGames.getCurrentCountdown().interrupt();
        survivalGames.setCurrentCountdown(new Countdown("Deathmatch starting in ", survivalGames.getPlayers(), 60, this::deathmatch).setTitle(SurvivalGames.TITLE).start());
    }

    public void deathmatch() {
        survivalGames.setPhase(SurvivalGames.Phase.DEATHMATCH_STARTING);
        teleportAllRemainingPlayersToSpawn();
        survivalGames.setCurrentCountdown(new Countdown("Deathmatch starts in ", survivalGames.getPlayers(), 10, () -> {
            survivalGames.setPhase(SurvivalGames.Phase.DEATHMATCH);
            chestRefill();
        }).setTitle(SurvivalGames.TITLE).start());
    }

    private void teleportAllRemainingPlayersToSpawn() {
        Set<Player> remainingPlayers = survivalGames.getAlivePlayers();
        List<Location> spawnLocations = new ArrayList<>(survivalGames.getMap().getSpawnLocations());
        int separator = (spawnLocations.size() - 1) / remainingPlayers.size();
        AtomicInteger count = new AtomicInteger(0);
        remainingPlayers.forEach(p -> p.teleport(spawnLocations.get(count.getAndAdd(separator))));
    }

    public Player endGame() {
        Set<Player> remainingPlayers = survivalGames.getAlivePlayers();
        if (remainingPlayers.size() > 1)
            throw new IllegalStateException("wow you dun fucked up (more than one remaining player on endgame)");
        Player winner = remainingPlayers.stream().findFirst().orElseThrow(() -> new IllegalStateException("well looks like noone won then rip"));
        survivalGames.broadcast(ChatColor.AQUA + winner.getName() + ChatColor.GRAY + " has won!");

        int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Games.getInstance(), () -> FireworkUtil.spawnFireworksAroundPlayer(winner), 0L, 20L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Games.getInstance(), () -> survivalGames.getPlayers().forEach(player -> {
            Bukkit.getScheduler().cancelTask(task);
            PlayerUtil.reset(player);
            player.teleport(Games.SPAWN);
        }), 10 * 20L);

        Leaderboard.getInstance().updateScores();

        return winner;
    }
}
