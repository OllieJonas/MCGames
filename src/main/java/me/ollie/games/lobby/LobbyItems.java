package me.ollie.games.lobby;

import me.ollie.games.Games;
import me.ollie.games.util.FireworkUtil;
import me.ollie.games.util.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class LobbyItems implements Listener {

    private static final ItemStack VOTE_ITEM = new ItemStackBuilder(Material.BOOK)
            .withName(ChatColor.AQUA + "Vote for a Map (Right Click)")
            .withLore(ChatColor.GRAY + "Right click me to vote for a map! :)")
            .build();

    private static final ItemStack LEAVE_ITEM = new ItemStackBuilder(Material.BARRIER)
            .withName(ChatColor.RED + "Leave (Right Click)")
            .withLore(ChatColor.GRAY + "Right click me to leave the game! :(")
            .build();

    private static final ItemStack KITTEN_CANNON_ITEM = new ItemStackBuilder(Material.STICK)
            .withName(ChatColor.AQUA + "Kitten Cannon (Right Click)")
            .withLore(ChatColor.GRAY + "Right click to send a kitten to the shadow realm")
            .build();

    public static void addItems(Player player) {
        player.getInventory().setItem(0, VOTE_ITEM);
        player.getInventory().setItem(8, LEAVE_ITEM);
        player.getInventory().setItem(4, KITTEN_CANNON_ITEM);
    }

    public static void resetItems(Player player) {
        player.getInventory().clear(0);
        player.getInventory().clear(4);
        player.getInventory().clear(8);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() == null)
            return;

        LobbyManager lobbyManager = LobbyManager.getInstance();
        Player player = event.getPlayer();

        if (!lobbyManager.isInLobby(player))
            return;

        ItemStack item = event.getItem();

        if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("Leave"))
            lobbyManager.leaveLobby(player);

        else if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("Vote for a Map"))
            lobbyManager.getLobbyFor(player).openMapVotingGuiFor(player);

        else if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("Kitten Cannon"))
            kittenCannon(player);
    }

    private void kittenCannon(Player player) {
        Ocelot ocelot = player.getWorld().spawn(player.getEyeLocation(), Ocelot.class);
        ocelot.setVelocity(player.getEyeLocation().getDirection().multiply(2));
        Games.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Games.getInstance(), () -> {
            Location location = ocelot.getLocation();
            ocelot.remove();
            FireworkUtil.spawnRandomFirework(location, true);
        }, 30L);
    }
}
