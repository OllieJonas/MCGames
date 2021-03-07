package me.ollie.games.games.survivalgames;

import me.ollie.games.Games;
import me.ollie.games.leaderboard.Leaderboard;
import me.ollie.games.lobby.LobbyItems;
import me.ollie.games.lobby.LobbyManager;
import me.ollie.games.util.Countdown;
import me.ollie.games.util.FireworkUtil;
import me.ollie.games.util.MessageUtil;
import me.ollie.games.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class SGGameLogic {

    private final SurvivalGames survivalGames;

    public SGGameLogic(SurvivalGames survivalGames) {
        this.survivalGames = survivalGames;
    }

    public void startGame(Set<Player> players) {
        survivalGames.setPlayers(new HashSet<>(players)); // new hashset bc weird pass-by-reference stuff w the original players set
        survivalGames.setAlivePlayers(new HashSet<>(players));

        players.forEach(p -> {
            survivalGames.getPlayerKills().put(p.getName(), 0); // fill playerkills
            LobbyItems.resetItems(p);
            GracePeriodItems.give(p);
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
        survivalGames.getPlayers().forEach(p -> {
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60 * 20, 5));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60 * 20, 0));
            GracePeriodItems.remove(p);
        });
        survivalGames.setCurrentCountdown(new Countdown("Resistance ends in ", survivalGames.getPlayers(), 60, () -> {
            startChestRefill(new AtomicInteger(0));
            survivalGames.getKitManager().giveKits();
        }).setTitle(SurvivalGames.TITLE).setDisplaySubtitle(false).start());
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
        survivalGames.getCurrentCountdown().destroy();
        survivalGames.setCurrentCountdown(new Countdown("Deathmatch starting in ", survivalGames.getPlayers(), 60, this::deathmatch).setTitle(SurvivalGames.TITLE).start());
    }

    public void deathmatch() {
        survivalGames.setPhase(SurvivalGames.Phase.DEATHMATCH_STARTING);
        teleportAllRemainingPlayersToSpawn();
        survivalGames.getCurrentCountdown().destroy();
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

    public Player endGame(boolean forced) {
        survivalGames.setPhase(SurvivalGames.Phase.END);

        Set<Player> remainingPlayers = survivalGames.getAlivePlayers();

        if (remainingPlayers.size() > 1 && !forced)
            throw new IllegalStateException("wow you dun fucked up (more than one remaining player on endgame)");

        Player winner = remainingPlayers.stream().findFirst().orElseThrow(() -> new IllegalStateException("well looks like noone won then rip"));

        survivalGames.broadcast(ChatColor.AQUA + winner.getName() + ChatColor.GRAY + " has won!");

        int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Games.getInstance(), () -> FireworkUtil.spawnFireworksAroundPlayer(winner), 0L, 20L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Games.getInstance(), () -> {

            survivalGames.getPlayers().forEach(player -> {
                Bukkit.getScheduler().cancelTask(task);
                PlayerUtil.reset(player);
                player.teleport(Games.SPAWN.get());
            });

            LobbyManager.getInstance().removeLobby(LobbyManager.getInstance().getLobbyIdFor(winner));
        }, 10 * 40L);

        survivalGames.getMap().getWorld().getEntities().stream().filter(f -> f.getType() == EntityType.WOLF || f.getType() == EntityType.HORSE).forEach(Entity::remove);
        Leaderboard.getInstance().addScores(survivalGames.getPlayerKills(), winner);
        survivalGames.getCurrentCountdown().destroy();
        return winner;
    }
}
