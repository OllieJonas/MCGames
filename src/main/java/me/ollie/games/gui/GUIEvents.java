package me.ollie.games.gui;

import me.ollie.games.Games;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

public class GUIEvents implements Listener {

    @EventHandler
    public void onInteract(InventoryClickEvent event) {

        HumanEntity entity = event.getWhoClicked();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            GUI gui = GUIManager.getInstance().getGuiFor(player);

            if (gui == null)
                return;

            event.setCancelled(true);

            int slot = event.getSlot();
            GUIItem item = gui.getItem(slot);

            if (item == null)
                return;

            if (event.getInventory().getType() != InventoryType.CHEST) // weird thing in paper where it considers your hotbar n stuff as well
                return;

            item.getAction().accept(player, event.getCurrentItem());

            if (item.isItemClosesMenu())
                GUIManager.getInstance().closeGuiFor(player);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().getType() != InventoryType.CHEST)
            return;

        HumanEntity entity = event.getPlayer();
        if (entity instanceof Player) {
            Player player = (Player) entity;

            GUI gui = GUIManager.getInstance().getGuiFor(player);

            if (gui != null) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Games.getInstance(), () -> GUIManager.getInstance().closeGuiFor(player), 5L);
            }
        }
    }
}
