package me.ollie.games.games.survivalgames;

import me.ollie.games.games.AbstractGame;
import me.ollie.games.games.survivalgames.kit.KitGUI;
import me.ollie.games.games.survivalgames.kit.KitManager;
import me.ollie.games.gui.GUIManager;
import me.ollie.games.lobby.Lobby;
import me.ollie.games.lobby.LobbyManager;
import me.ollie.games.util.FireworkUtil;
import me.ollie.games.util.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("DuplicatedCode")
public class GracePeriodItems implements Listener {

    private static final ItemStack FIREWORK_ITEM = new ItemStackBuilder(Material.FIRE_CHARGE).withAmount(3).withName("&bLaunch Firework (Right Click)").withLore(ChatColor.GRAY + "Right click to launch fireworks! :)").build();

    private static final ItemStack KIT_ITEM = new ItemStackBuilder(Material.BOW).withName("&bSelect Kit (Right Click)").withLore(ChatColor.GRAY + "Right click to select a kit!").build();

    public static void give(Player player) {
        player.getInventory().setItem(0, KIT_ITEM);
        player.getInventory().setItem(8, FIREWORK_ITEM);
    }

    public static void remove(Player player) {
        player.sendMessage(SurvivalGames.TITLE + " You will receive your kit in " + ChatColor.AQUA + "1 minute");
        player.getInventory().clear(0);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() == null)
            return;

        LobbyManager lobbyManager = LobbyManager.getInstance();
        Player player = event.getPlayer();

        if (!lobbyManager.isInGame(player))
            return;

        ItemStack item = event.getItem();

        if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("Launch Firework")) {
            int amount = item.getAmount();

            if (amount == 1)
                player.getInventory().remove(event.getItem());
            else
                item.setAmount(amount - 1);

            FireworkUtil.spawnFireworksAroundPlayer(player);
            player.sendMessage(ChatColor.GRAY + "Launched fireworks!");
        } else if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("Select Kit")) {
            Lobby lobby = lobbyManager.getLobbyFor(player);
            AbstractGame game = lobby.getGame();

            if (game instanceof SurvivalGames) {
                SurvivalGames survivalGames = (SurvivalGames) game;
                KitManager manager = survivalGames.getKitManager();
                if (manager != null) {
                    GUIManager.getInstance().openGuiFor(player, new KitGUI(manager).getGui());
                }
            }
        }

    }
}
