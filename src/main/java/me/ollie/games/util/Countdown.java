package me.ollie.games.util;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Countdown {

    private String message;

    private GameBossBar bossBar;

    private Collection<Player> audience;

    private int seconds;

    public Countdown(String message, Collection<Player> audience, int seconds) {
        this.message = message;
        this.audience = audience;
        this.seconds = seconds;
        this.bossBar = buildBossBar(message, seconds);
    }

    public void addPlayer(Player player) {
        audience.add(player);
        bossBar.showTo(player);
    }

    public void removePlayer(Player player) {
        audience.remove(player);
        bossBar.hideFrom(player);
    }

    public void start() {
        audience.forEach(p -> bossBar.showTo(p));
        bossBar.run();
    }

    private GameBossBar buildBossBar(String message, int seconds) {
        return new GameBossBar(1, TimeUnit.SECONDS,
                IntStream.rangeClosed(2, seconds)
                        .boxed()
                        .map(i -> BossBar.bossBar(Component.text(ChatColor.GRAY + message + ChatColor.AQUA + (seconds - i) + ChatColor.GRAY + " seconds!"), 1.0F - ((float) i / (float) seconds), BossBar.Color.PURPLE, BossBar.Overlay.PROGRESS)).collect(Collectors.toList()));
    }

    private class Task extends BukkitRunnable {

        @Override
        public void run() {

        }
    }
}
