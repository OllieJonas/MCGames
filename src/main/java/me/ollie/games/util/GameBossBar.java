package me.ollie.games.util;

import com.google.common.collect.Sets;
import lombok.Getter;
import me.ollie.games.Games;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class GameBossBar {


    private final AtomicInteger currentStep;

    private final long time;

    private final TimeUnit timeUnit;

    @Getter
    private final List<BossBar> bossBar;

    private final Set<Player> audience;

    private final boolean repeating;

    private int taskId;

    @Getter
    private AtomicReference<BossBar> currentBar;

    public GameBossBar(BossBar... bars) {
        this(Sets.newConcurrentHashSet(), 1, TimeUnit.SECONDS, true, bars);
    }

    public GameBossBar(boolean repeating, BossBar... bars) {
        this(Sets.newConcurrentHashSet(), 1, TimeUnit.SECONDS, repeating, bars);
    }

    public GameBossBar(long time, TimeUnit timeUnit, BossBar... bars) {
        this(Sets.newConcurrentHashSet(), time, timeUnit, false, bars);
    }

    public GameBossBar(long time, TimeUnit timeUnit, List<BossBar> bars) {
        this(Sets.newConcurrentHashSet(), time, timeUnit, false, bars);
    }

    public GameBossBar(Set<Player> audience, long time, TimeUnit timeUnit, boolean repeating, BossBar... bars) {
        this(audience, time, timeUnit, repeating, Arrays.asList(bars));
    }

    public GameBossBar(Set<Player> audience, long time, TimeUnit timeUnit, boolean repeating, List<BossBar> bars) {
        this.audience = audience;
        this.currentStep = new AtomicInteger(0);
        this.time = time;
        this.timeUnit = timeUnit;
        this.repeating = repeating;
        this.bossBar = bars;
    }

    public GameBossBar run() {
        currentBar.set(bossBar.get(0));
        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Games.getInstance(), new Task(), 0L, TimeUnitToTicks.calculate(time, timeUnit));
        return this;
    }

    public void showTo(Player player) {
        audience.add(player);
    }

    public void hideFrom(Player player) {
        audience.remove(player);
        hideCurrent(player);
    }

    public void hideCurrent(Player player) {
        player.hideBossBar(currentBar.get());
    }

    public void destroy() {
        Bukkit.getScheduler().cancelTask(taskId);
        audience.forEach(p -> p.hideBossBar(currentBar.get()));
    }

    private class Task implements Runnable {

        private boolean cancelled = false;

        @Override
        public void run() {
            if (cancelled || currentStep.get() >= bossBar.size()) {
                destroy();
                return;
            }

            BossBar next = bossBar.get(currentStep.get());
            audience.forEach(p -> {
                p.hideBossBar(currentBar.get());
                p.showBossBar(next);
            });
            currentBar.set(next);
            currentStep.getAndIncrement();

            if ((currentStep.get() - 1) != 0 && currentStep.get() % bossBar.size() == 0) {
                // ie. end of cycle
                if (repeating)
                    currentStep.set(0);
                else
                    cancelled = true;

            }
        }
    }
}
