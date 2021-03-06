package me.ollie.games.util.potion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Objects;

public class PotionEvents implements Listener {

    @EventHandler
    public void onSplash(PotionSplashEvent event) {
        ItemStack item = event.getPotion().getItem();
        if (!item.hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS))
            return;

        try {
            PotionContext context = PotionContext.fromItem(item);
            event.getAffectedEntities().forEach(e -> e.addPotionEffect(new PotionEffect(context.getPotionEffectType(), context.getDuration(), context.getAmplitude())));
        } catch (IllegalStateException ignored) {
        }
    }

    public void onConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.POTION)
            return;

        ItemStack item = event.getItem();

        if (!item.hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS))
            return;

        try {
            PotionContext context = PotionContext.fromItem(item);
            event.getPlayer().addPotionEffect(new PotionEffect(context.getPotionEffectType(), context.getDuration(), context.getAmplitude()));
        } catch (IllegalStateException e) {
            event.getPlayer().sendMessage(ChatColor.RED + "Potion doesn't exist rip");
        }
    }

    @Getter
    @AllArgsConstructor
    private static class PotionContext {
        private final PotionEffectType potionEffectType;
        private final int duration;
        private final int amplitude;

        public static PotionContext fromItem(ItemStack item) {
            return fromLore(Objects.requireNonNull(item.getLore()));
        }

        public static PotionContext fromLore(List<String> lore) {

            if (lore == null || lore.size() != 3)
                throw new IllegalStateException("Lore not correct");

            String potionEffectTypeName = ChatColor.stripColor(lore.get(0).split(" ")[1]);
            PotionEffectType potionEffectType = PotionEffectType.getByName(potionEffectTypeName);

            if (potionEffectType == null)
                throw new IllegalStateException("Potion is null");

            int duration = Integer.parseInt(lore.get(1).split(" ")[1]) * 20;
            int amplitude = Integer.parseInt(lore.get(2).split(" ")[1]) - 1;

            return new PotionContext(potionEffectType, duration, amplitude);
        }
    }
}
