package me.ollie.games.lobby;

import me.ollie.games.Games;
import me.ollie.games.util.MessageUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class LobbyEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().teleport(Games.SPAWN.get());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!LobbyManager.getInstance().isInLobby(player)) return;

        Lobby lobby = LobbyManager.getInstance().getLobbyFor(player);
        lobby.removePlayer(player);
    }


    // prevent people taking damage
    @EventHandler
    public void onVoidDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) return;

        Player player = (Player) entity;

        if (!LobbyManager.getInstance().isInLobby(player)) return;

        Lobby lobby = LobbyManager.getInstance().getLobbyFor(player);

        event.setCancelled(true);

        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            player.teleport(lobby.getSpawnPoint()); // prevent people from just yeeting off the edge
        }
    }

    // ----------- DOUBLE JUMP --------------- //
    @EventHandler
    public void doubleJumpToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (LobbyManager.getInstance().isInLobby(player)) {
            if (player.getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
                player.setAllowFlight(false);
                player.setFlying(false);
                player.setVelocity(player.getLocation().getDirection().multiply(1.5).add(new Vector(0, 1, 0)));
            }
        }
    }

    @EventHandler
    public void doubleJumpMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (LobbyManager.getInstance().isInLobby(player)) {
            if (player.getGameMode() != GameMode.CREATIVE && player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR && !player.isFlying())
                player.setAllowFlight(true);
        }
    }
}
