package me.ollie.games.games;

import me.ollie.games.games.survivalgames.SurvivalGames;
import me.ollie.games.gui.GUIManager;
import me.ollie.games.gui.PlayerListGUI;
import me.ollie.games.lobby.LobbyManager;
import me.ollie.games.util.ItemStackBuilder;
import me.ollie.games.util.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SpectatorItems implements Listener {

    public static final ItemStack SPECTATE_ITEM = new ItemStackBuilder(Material.ENCHANTED_BOOK).withName("&bSpectate Others (Right Click)").withLore("&7Right click to spectate other players!").build();

    public static void give(Player player) {
        player.sendMessage("given items");
        player.getInventory().clear();
        player.getInventory().setItem(0, SPECTATE_ITEM);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        MessageUtil.broadcast("event called");
        if (event.getItem() == null)
            return;

        MessageUtil.broadcast("item not null");

        LobbyManager lobbyManager = LobbyManager.getInstance();
        Player player = event.getPlayer();

        if (!lobbyManager.isInGame(player)) {
            MessageUtil.broadcast("not in game");
            return;
        }

        ItemStack item = event.getItem();


        if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("Spectate Others")) {
            MessageUtil.broadcast("opening menu");
            GUIManager.getInstance().openGuiFor(player, spectateGUI((SurvivalGames) lobbyManager.getLobbyFor(player).getGame()).getGui());
        }
    }

    private PlayerListGUI spectateGUI(SurvivalGames game) {
        return new PlayerListGUI("Spectate - Press ESC to Exit", game::getAlivePlayers, p -> new String[]{ChatColor.GRAY + "Click to spectate " + ChatColor.AQUA + p.getName()}, (p, t) -> p.teleport(t.getLocation()));
    }
}
