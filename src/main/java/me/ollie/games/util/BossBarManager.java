package me.ollie.games.util;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class BossBarManager {

    @Getter
    private static final BossBarManager instance = new BossBarManager();

    private final Set<GameBossBar> bossBars;

    public BossBarManager() {
        this.bossBars = new HashSet<>();
    }

    public void showBossBar(GameBossBar bossBar, Player player) {
        bossBars.add(bossBar);
        bossBar.showTo(player);
    }

    public void destroy() {
        bossBars.forEach(GameBossBar::destroy);
    }
}
