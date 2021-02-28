package me.ollie.games.games.survivalgames.events;

import me.ollie.games.games.survivalgames.SurvivalGames;
import me.ollie.games.games.survivalgames.chest.SGChest;
import me.ollie.games.lobby.Lobby;
import me.ollie.games.lobby.LobbyManager;
import me.ollie.games.util.MessageUtil;
import me.ollie.games.util.MovementUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@SuppressWarnings("DuplicatedCode")
public class SGEvents implements Listener {

    @EventHandler
    public void spectatorsCantTakeDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Player)) return;
        Player player = (Player) entity;

        Lobby lobby = LobbyManager.getInstance().getLobbyFor(player);

        if (lobby == null)
            return;

        if (!lobby.getGame().getName().contains("Survival Games"))
            return;

        SurvivalGames survivalGames = (SurvivalGames) lobby.getGame();
        if (survivalGames.isSpectator(player))
            event.setCancelled(true);
    }

    @EventHandler
    public void countdownStopMovement(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Lobby lobby = LobbyManager.getInstance().getLobbyFor(player);

        if (lobby == null)
            return;

        if (!lobby.getGame().getName().contains("Survival Games"))
            return;

        SurvivalGames survivalGames = (SurvivalGames) lobby.getGame();

        if ((survivalGames.getPhase() == SurvivalGames.Phase.COUNTDOWN || survivalGames.getPhase() == SurvivalGames.Phase.DEATHMATCH_STARTING) && survivalGames.isAlive(player)) {
            if (MovementUtil.hasPositionShifted(event.getFrom(), event.getTo()))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void openChest(InventoryOpenEvent event) {
        HumanEntity entity = event.getPlayer();
        if (!(entity instanceof Player))
            return;

        Player player = (Player) entity;

        Lobby lobby = LobbyManager.getInstance().getLobbyFor(player);

        if (lobby == null) // ie. not in lobby
            return;

        if (!lobby.getGame().getName().contains("Survival Games")) // ie. lobby isn't survival games
            return;

        SurvivalGames survivalGames = (SurvivalGames) lobby.getGame();

        if (!survivalGames.getPhase().isInGame())
            return;

        if (survivalGames.isSpectator(player))
            event.setCancelled(true);

        if (!(event.getInventory().getHolder() instanceof Chest)) // ie. not a chest
            return;

        Location location = event.getInventory().getLocation();

        if ((survivalGames.isChestAlreadyLooted(location))) // ie. already looted
            return;

        Map<Integer, ItemStack> items = SGChest.buildItems();
        items.forEach((key, value) -> event.getInventory().setItem(key, value));

        survivalGames.addLootedChest(location);
    }

    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        Player victim = event.getEntity();

        Lobby lobby = LobbyManager.getInstance().getLobbyFor(victim);
        if (lobby == null)
            return;

        if (!lobby.getGame().getClass().isAssignableFrom(SurvivalGames.class))
            return;

        SurvivalGames survivalGames = (SurvivalGames) lobby.getGame();

        victim.setHealth(20D); // avoids death screen

        if (killer != null)
            survivalGames.handlePlayerKill(event, killer, victim);
        else
            survivalGames.handlePlayerDeath(event);
    }
}
