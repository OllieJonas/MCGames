package me.ollie.games.games.survivalgames.kit;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class KitManager {

    private final Collection<Player> players;
    private final Map<Player, Kit> kitSelections;

    public KitManager(Collection<Player> players) {
        this.players = players;
        this.kitSelections = new HashMap<>();
    }

    public void addKit(Player player, Kit kit) {
        if (kitSelections.get(player) != null && kitSelections.get(player).equals(kit)) {
            player.sendMessage(ChatColor.RED + "You have already selected this kit! :(");
            return;
        }

        player.sendMessage(ChatColor.GRAY + "You have selected the kit " + ChatColor.AQUA + kit.getName() + ChatColor.GRAY + "!");
        kitSelections.put(player, kit);
    }

    public void giveKits() {
        kitSelections.forEach(this::giveKit);

        // gives players that haven't selected a kit a random one
        players.stream().filter(p -> !kitSelections.containsKey(p)).collect(Collectors.toList()).forEach(p -> {
            p.sendMessage(ChatColor.GRAY + "You didn't select a kit, so you have been given a random one!");
            giveKit(p, KitRegistry.randomKit());
        });
    }

    private void giveKit(Player player, Kit kit) {
        player.sendMessage(ChatColor.GRAY + "You have received the kit " + ChatColor.AQUA + kit.getName() + ChatColor.GRAY + "!");
        player.getInventory().addItem(kit.getItems().toArray(new ItemStack[0]));
    }
}
