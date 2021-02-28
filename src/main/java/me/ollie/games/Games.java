package me.ollie.games;

import lombok.Getter;
import me.ollie.games.citizens.CitizenEvents;
import me.ollie.games.citizens.CitizensManager;
import me.ollie.games.commands.GameCommand;
import me.ollie.games.commands.test.TestCommand;
import me.ollie.games.events.ChatEvents;
import me.ollie.games.events.RandomBoringEvents;
import me.ollie.games.games.SpectatorItems;
import me.ollie.games.games.survivalgames.events.SGEvents;
import me.ollie.games.gui.GUIEvents;
import me.ollie.games.lobby.LobbyEvents;
import me.ollie.games.lobby.LobbyItems;
import me.ollie.games.util.BossBarManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public final class Games extends JavaPlugin {

    public static final Location SPAWN = new Location(Bukkit.getWorld("world"), 473.5, 10.5, 98.5, -180F, 0F);
    @Getter
    public static Games instance;

    @Override
    public void onEnable() {
        new CitizensManager();
        registerCommands();
        registerListeners();

        clearWeather();

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
    }

    private void clearWeather() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getWorlds().forEach(w -> w.setTime(6000L)), 0L, 100L);
    }
}
