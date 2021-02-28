package me.ollie.games.games.survivalgames;

import me.ollie.games.core.AbstractGameMap;
import me.ollie.games.games.AbstractGame;
import me.ollie.games.util.Countdown;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;

public class SurvivalGames extends AbstractGame {

    enum Phase {
        COUNTDOWN,
        GRACE_PERIOD,
        GAME,
        CHEST_REFILL,
        DEATHMATCH,
        END;
    }

    private final Set<Location> lootedChests;

    private Set<Player> players;

    private Phase phase;

    private Countdown currentCountdown;

    private SGMap map;

    public SurvivalGames() {
        super("Survival Games");
        this.players = new HashSet<>();
        this.lootedChests = new HashSet<>();
        this.phase = Phase.COUNTDOWN;
    }

    @Override
    public void load(AbstractGameMap map) {
        if (map.getClass().isAssignableFrom(SGMap.class))
            throw new IllegalStateException("this is seriously bad wtf are you doing lmfaooooo (map isnt assignable to sgmap)");
        this.map = (SGMap) map;
    }

    @Override
    public void startGame(Set<Player> players) {
        this.players = players;
        this.currentCountdown = new Countdown("Game starting ", players, 30, this::startGracePeriod).start();
    }

    private void startGracePeriod() {
        players.forEach(p -> p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 3)));
        this.currentCountdown = new Countdown("Resistance ends ", players, 60, this::startGracePeriod).start();
    }

    private void startChestRefill() {
        this.currentCountdown = new Countdown("Chests refill ", players, 240, this::startChestRefill).start();
    }

    private void deathmatch() {

    }

    @Override
    public void endGame() {

    }
}
