package me.ollie.games.games.survivalgames.chest;

import me.ollie.games.games.AbstractGame;
import me.ollie.games.games.survivalgames.SurvivalGames;
import me.ollie.games.lobby.LobbyManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class ChestItemEvents implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() == null)
            return;

        LobbyManager lobbyManager = LobbyManager.getInstance();
        Player player = event.getPlayer();

        if (!lobbyManager.isInGame(player)) {
            return;
        }

        AbstractGame abstractGame = lobbyManager.getLobbyFor(player).getGame();
        if (abstractGame instanceof SurvivalGames) {
            SurvivalGames survivalGames = (SurvivalGames) abstractGame;
            ItemStack item = event.getItem();

            if (item.getType() == Material.COMPASS) {
                Player secondFurthest = findSecondNearestPlayer(survivalGames.getAlivePlayers(), player);
                player.sendMessage(ChatColor.GRAY + "Now tracking " + ChatColor.AQUA + secondFurthest.getName() + ChatColor.GRAY + "!");
                player.setCompassTarget(secondFurthest.getLocation());
            }
        }

    }

    public Player findSecondNearestPlayer(Collection<Player> players, Player player) {
        double furthestDistance = Double.MAX_VALUE;
        double secondFurthestDistance = Double.MAX_VALUE;

        Player closest = player;
        Player secondClosest = player;

        for (Player target : players) {
            if (target != player) {
                double distance = Math.abs(player.getLocation().distanceSquared(target.getLocation()));
                if (distance < furthestDistance) {
                    secondClosest = closest;
                    closest = target;

                    secondFurthestDistance = furthestDistance;
                    furthestDistance = distance;
                }

                if (distance < secondFurthestDistance && distance != furthestDistance) {
                    secondFurthestDistance = distance;
                    secondClosest = target;
                }
            }
        }
        return secondClosest;
    }
}
