package me.ollie.games.lobby;

import me.ollie.games.gui.GUIManager;
import me.ollie.games.lobby.vote.MapVoteGUI;
import me.ollie.games.util.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class LobbyItems implements Listener {

    private static final ItemStack VOTE_ITEM = new ItemStackBuilder(Material.COMPASS)
            .withName(ChatColor.GREEN + "Vote for a Map (Right Click)")
            .withLore(ChatColor.GRAY + "Right click me to vote for a map! :)")
            .build();

    private static final ItemStack LEAVE_ITEM = new ItemStackBuilder(Material.BARRIER)
            .withName(ChatColor.RED + "Leave (Right Click)")
            .withLore(ChatColor.GRAY + "Right click me to leave the game! :(")
            .build();

    public static void addItems(Player player) {
        player.getInventory().setItem(0, VOTE_ITEM);
        player.getInventory().setItem(8, LEAVE_ITEM);
    }

    public void resetItems(Player player) {
        player.getInventory().clear(0);
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

        if (item.getItemMeta().getDisplayName().contains("Leave"))
            lobbyManager.leaveLobby(player);

        else if (item.getItemMeta().getDisplayName().contains("Vote for a Map"))
            GUIManager.getInstance().openGuiFor(player, new MapVoteGUI().getGui());
    }
}
