package me.ollie.games.games.survivalgames;

import me.ollie.games.lobby.LobbyItems;
import me.ollie.games.util.Countdown;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SGGameLogic {

    private final SurvivalGames survivalGames;

    public SGGameLogic(SurvivalGames survivalGames) {
        this.survivalGames = survivalGames;
    }

    public void startGame(Set<Player> players) {
        survivalGames.setPlayers(players);
        players.forEach(p -> {
            survivalGames.getPlayerKills().put(p.getUniqueId(), 0); // fill playerkills
            LobbyItems.resetItems(p);
            survivalGames.sendTitleCard(p);
        });
        List<Player> playerList = new ArrayList<>(players);
        List<Location> spawns = new ArrayList<>(survivalGames.getMap().getSpawnLocations());

        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).teleport(spawns.get(i));
        }

        survivalGames.setPhase(SurvivalGames.Phase.COUNTDOWN);
        survivalGames.setCurrentCountdown(new Countdown("Game starting in ", players, 30, this::startGracePeriod).setTitle(SurvivalGames.TITLE).setDisplaySubtitle(false).start());
    }

    private void startGracePeriod() {
        survivalGames.setPhase(SurvivalGames.Phase.GRACE_PERIOD);
        survivalGames.getPlayers().forEach(p -> p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60 * 20, 5)));
        survivalGames.setCurrentCountdown(new Countdown("Resistance ends in ", survivalGames.getPlayers(), 60, this::startChestRefill).setTitle(SurvivalGames.TITLE).start());
    }

    private void startChestRefill() {
        survivalGames.setPhase(SurvivalGames.Phase.GAME);
        survivalGames.setCurrentCountdown(new Countdown("Chests refill in ", survivalGames.getPlayers(), 240, () -> {
            chestRefill();
            startChestRefill();
        }).setTitle(SurvivalGames.TITLE).start());
    }

    private void chestRefill() {
        survivalGames.setLootedChests(new HashSet<>());
    }

    public void deathmatch() {

    }

    public void endGame() {

    }
}
