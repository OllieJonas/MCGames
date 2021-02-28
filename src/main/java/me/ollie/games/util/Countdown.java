package me.ollie.games.util;

import lombok.Setter;
import me.ollie.games.Games;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Countdown {

    private final String message;

    private final AtomicInteger counter = new AtomicInteger(0);

    private int taskId;

    private final GameBossBar bossBar;

    private final Collection<Player> audience;

    private final int seconds;

    private final Runnable onFinish;

    public Countdown(String message, Collection<Player> audience, int seconds) {
        this(message, audience, seconds, () -> {});
    }

    public Countdown(String message, Collection<Player> audience, int seconds, Runnable onFinish) {
        this.message = message;
        this.audience = audience;
        this.seconds = seconds;
        this.bossBar = buildBossBar(message, seconds);
        this.onFinish = onFinish;
    }

    public void addPlayer(Player player) {
        audience.add(player);
        bossBar.showTo(player);
    }

    public void removePlayer(Player player) {
        audience.remove(player);
        bossBar.hideFrom(player);
    }

    public Countdown start() {
        audience.forEach(bossBar::showTo);
        bossBar.run();
        taskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Games.getInstance(), new Task(), 0L, 20L);
        return this;
    }

    public void interrupt() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public void destroy() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    private GameBossBar buildBossBar(String message, int seconds) {
        return new GameBossBar(1, TimeUnit.SECONDS,
                IntStream.rangeClosed(2, seconds)
                        .boxed()
                        .map(i -> BossBar.bossBar(Component.text(formatCountdown(message, seconds - i)), 1.0F - ((float) i / (float) seconds), BossBar.Color.PURPLE, BossBar.Overlay.PROGRESS)).collect(Collectors.toList()));
    }

    private class Task extends BukkitRunnable {

        private boolean cancelled = false;

        @Override
        public void run() {

            if (cancelled) {
                onFinish.run();
                destroy();
                return;
            }

            int secondsRemaining = seconds - counter.get();

            if (secondsRemaining == 15) {
                audience.forEach(p -> p.sendTitle("", formatCountdown(message, secondsRemaining)));
            }

            if (secondsRemaining < 6) {
                audience.forEach(p -> {
                    p.sendMessage(formatCountdown(message, secondsRemaining));
                    p.playSound(p.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
                });
            }

            if (secondsRemaining == 0) {
                cancelled = true;
            }
            counter.getAndIncrement();
        }
    }

    private static String formatCountdown(String message, int timeRemaining) {
        return ChatColor.GRAY + message + " " + ChatColor.AQUA + toMinutesAndSeconds(timeRemaining);
    }

    private static String toMinutesAndSeconds(int timeRemaining) {
        return timeRemaining < 60 ? getSeconds(timeRemaining) : getMinutes(timeRemaining / 60) + " " + getSeconds(timeRemaining % 60);
    }

    private static String getSeconds(int time) {
        return time + (time == 1 ? "second" : "seconds");
    }

    private static String getMinutes(int time) {
        return time + (time == 1 ? "minute" : "minutes");
    }
}
