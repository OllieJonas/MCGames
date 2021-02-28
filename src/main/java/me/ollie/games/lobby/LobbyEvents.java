package me.ollie.games.lobby;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class LobbyEvents implements Listener {


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
                player.setVelocity(player.getLocation().getDirection().multiply(1.5));
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
