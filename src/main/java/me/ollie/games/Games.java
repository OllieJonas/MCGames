package me.ollie.games;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import lombok.Getter;
import me.ollie.games.citizens.CitizenEvents;
import me.ollie.games.citizens.CitizensManager;
import me.ollie.games.commands.game.GameCommand;
import me.ollie.games.commands.test.TestCommand;
import me.ollie.games.events.ChatEvents;
import me.ollie.games.events.RandomBoringEvents;
import me.ollie.games.games.MapCollectionFactory;
import me.ollie.games.games.SpectatorEvents;
import me.ollie.games.games.SpectatorItems;
import me.ollie.games.games.survivalgames.GracePeriodItems;
import me.ollie.games.games.survivalgames.chest.ChestItemEvents;
import me.ollie.games.games.survivalgames.events.SGEvents;
import me.ollie.games.gui.GUIEvents;
import me.ollie.games.leaderboard.Leaderboard;
import me.ollie.games.leaderboard.LeaderboardEvents;
import me.ollie.games.lobby.LobbyEvents;
import me.ollie.games.lobby.LobbyItems;
import me.ollie.games.util.BossBarManager;
import me.ollie.games.util.MiscUtil;
import me.ollie.games.util.potion.PotionEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Supplier;

public final class Games extends JavaPlugin {

    public static final Supplier<Location> SPAWN = () -> new Location(Bukkit.getWorld("world"), 473.5, 10.5, 98.5, -180F, 0F);

    @Getter
    public static Games instance;

    @Override
    public void onEnable() {
        new CitizensManager();
        registerCommands();
        registerListeners();

        MapCollectionFactory.initMaps();

        new Leaderboard(this);
        MiscUtil.disableAchievements();

        buildWelcomeHologram();
        buildMemeHologram();
        buildInformationHolograms();

        alwaysDay();

        instance = this;

    }

    @Override
    public void onDisable() {
        BossBarManager.getInstance().destroy();
        CitizensManager.getInstance().destroy();
    }

    @SuppressWarnings("ConstantConditions")
    private void registerCommands() {
        getCommand("game").setExecutor(new GameCommand());
        getCommand("test").setExecutor(new TestCommand());
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ChatEvents(), this);
        Bukkit.getPluginManager().registerEvents(new GUIEvents(), this);
        Bukkit.getPluginManager().registerEvents(new LobbyItems(), this);
        Bukkit.getPluginManager().registerEvents(new RandomBoringEvents(), this);
        Bukkit.getPluginManager().registerEvents(new LobbyEvents(), this);
        Bukkit.getPluginManager().registerEvents(new SGEvents(), this);
        Bukkit.getPluginManager().registerEvents(new SpectatorItems(), this);
        Bukkit.getPluginManager().registerEvents(new CitizenEvents(), this);
        Bukkit.getPluginManager().registerEvents(new GracePeriodItems(), this);
        Bukkit.getPluginManager().registerEvents(new SpectatorEvents(), this);
        Bukkit.getPluginManager().registerEvents(new LeaderboardEvents(), this);
        Bukkit.getPluginManager().registerEvents(new PotionEvents(), this);
        Bukkit.getPluginManager().registerEvents(new ChestItemEvents(), this);
    }

    private void alwaysDay() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getWorlds().forEach(w -> w.setTime(6000L)), 0L, 100L);
    }

    private void buildWelcomeHologram() {
        Hologram hologram = HologramsAPI.createHologram(this, new Location(Bukkit.getWorld("world"), 473.5, 12.5, 93.5));
        hologram.appendTextLine(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "hihihi uwu");
        hologram.appendTextLine(ChatColor.GRAY + "To play, please walk forward to the NPC");
        hologram.appendTextLine(ChatColor.GRAY + "and right click them!");
        hologram.appendTextLine(ChatColor.GRAY + "(or you can type /game list)");
    }

    private void buildMemeHologram() {
        Hologram hologram = HologramsAPI.createHologram(this, new Location(Bukkit.getWorld("world"), 473.5, 6, 96.5));
        hologram.appendTextLine(ChatColor.GRAY + "According to all known laws of aviation,");
        hologram.appendTextLine(ChatColor.GRAY + "There's no way a bee should be able to fly.");
        hologram.appendTextLine(ChatColor.GRAY + "Its wings are too small to get its fat little body off the ground.");
        hologram.appendTextLine(ChatColor.GRAY + "The bee, of course, flies anyways.");
        hologram.appendTextLine(ChatColor.GRAY + "Because bees don't care what humans think is impossible.");
    }

    private void buildInformationHolograms() {
        buildHowToPlayHologram();
        buildCompassHologram();
        buildKitsHologram();
    }

    private void buildHowToPlayHologram() {
        Hologram hologram = HologramsAPI.createHologram(this, new Location(Bukkit.getWorld("world"), 489.5, 4.5 + 4, 72.5, 90, 0));
        hologram.appendTextLine(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "How to Play");
        hologram.appendTextLine(ChatColor.GRAY + "Up to 24 players will be spawned onto a map with nothing.");
        hologram.appendTextLine(ChatColor.GRAY + "Your goal is simple: " + ChatColor.AQUA + "Be the last person standing.");
        hologram.appendTextLine(ChatColor.GRAY + "There will be" + ChatColor.AQUA + " chests " + ChatColor.GRAY + "dotted all around the map, all containing loot.");
        hologram.appendTextLine(ChatColor.GRAY + "Use this loot to" + ChatColor.AQUA + " kill " + ChatColor.GRAY + "the other players and be the last person standing!");
        hologram.appendTextLine(" ");
        hologram.appendTextLine(ChatColor.GRAY + "Please don't go in teams of larger than " + ChatColor.AQUA + "2" + ChatColor.GRAY + " people.");
        hologram.appendTextLine(ChatColor.GRAY + "This can make it unfair on others :(");

    }

    private void buildCompassHologram() {
        Hologram hologram = HologramsAPI.createHologram(this, new Location(Bukkit.getWorld("world"), 488.5, 4 + 2, 69.5));
        hologram.appendTextLine(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Compass");
        hologram.appendTextLine(ChatColor.GRAY + "If you find a " + ChatColor.AQUA + "compass" + ChatColor.GRAY + ", right click it and it will");
        hologram.appendTextLine(ChatColor.GRAY + "point to the " + ChatColor.AQUA + "second closest person" + ChatColor.GRAY + " to you.");

        hologram.appendItemLine(new ItemStack(Material.COMPASS));
    }

    private void buildKitsHologram() {
        Hologram hologram = HologramsAPI.createHologram(this, new Location(Bukkit.getWorld("world"), 488.5, 4 + 2, 75.5));
        hologram.appendTextLine(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Kits");
        hologram.appendTextLine(ChatColor.GRAY + "When loaded into the map, right click the " + ChatColor.AQUA + "bow" + ChatColor.GRAY);
        hologram.appendTextLine(ChatColor.GRAY + "to select a kit! You will be given your kit");
        hologram.appendTextLine( ChatColor.AQUA + "1 minute " + ChatColor.GRAY + "after the game starting");
        hologram.appendItemLine(new ItemStack(Material.BOW));
    }
}
