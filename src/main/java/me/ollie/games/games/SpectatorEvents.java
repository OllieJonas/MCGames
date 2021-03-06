package me.ollie.games.games;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import me.ollie.games.games.survivalgames.SurvivalGames;
import me.ollie.games.lobby.LobbyManager;
import me.ollie.games.util.MessageUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

@SuppressWarnings("DuplicatedCode")
public class SpectatorEvents implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerPostRespawnEvent event) {

        LobbyManager lobbyManager = LobbyManager.getInstance();
        Player player = event.getPlayer();

        if (!lobbyManager.isInGame(player))
            return;

        AbstractGame abstractGame = lobbyManager.getLobbyFor(player).getGame();

        if (abstractGame instanceof SurvivalGames) {
            SurvivalGames survivalGames = (SurvivalGames) abstractGame;
            if (survivalGames.isSpectator(player))
                SpectatorItems.give(player);
        }
    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity entityDamager = event.getDamager();

        if (!(entityDamager instanceof Player))
            return;

        Player damager = (Player) entityDamager;

        LobbyManager lobbyManager = LobbyManager.getInstance();

        if (!lobbyManager.isInGame(damager))
            return;

        AbstractGame abstractGame = lobbyManager.getLobbyFor(damager).getGame();

        if (abstractGame instanceof SurvivalGames) {
            SurvivalGames survivalGames = (SurvivalGames) abstractGame;
            if (survivalGames.isSpectator(damager))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(PlayerAttemptPickupItemEvent event) {
        Player player = event.getPlayer();

        LobbyManager lobbyManager = LobbyManager.getInstance();

        if (!lobbyManager.isInGame(player))
            return;

        AbstractGame abstractGame = lobbyManager.getLobbyFor(player).getGame();

        if (abstractGame instanceof SurvivalGames) {
            SurvivalGames survivalGames = (SurvivalGames) abstractGame;
            if (survivalGames.isSpectator(player))
                event.setCancelled(true);
        }
    }

    public void dropBlocks(PlayerDropItemEvent event) {
        Player dropper = event.getPlayer();
        LobbyManager lobbyManager = LobbyManager.getInstance();

        if (!lobbyManager.isInGame(dropper))
            return;

        AbstractGame abstractGame = lobbyManager.getLobbyFor(dropper).getGame();

        if (abstractGame instanceof SurvivalGames) {
            SurvivalGames survivalGames = (SurvivalGames) abstractGame;
            if (survivalGames.isSpectator(dropper)) {
                MessageUtil.broadcast("cancelled");
                event.setCancelled(true);
            }
        }
    }
}
