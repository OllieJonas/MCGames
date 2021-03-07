package me.ollie.games.commands.test;

import me.ollie.games.commands.SubCommand;
import me.ollie.games.util.potion.Potion;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class PotionsCommand extends SubCommand {


    public PotionsCommand() {
        super("potions", true, NO_ALIASES);
    }

    @Override
    public void run(Player sender, String[] args) {
        sender.getInventory().addItem(Potion.builder().isSplash(true).amount(3).amplitude(1).duration(5).color(Color.GREEN).effect(PotionEffectType.BLINDNESS).effect(PotionEffectType.CONFUSION).build().toItemStack());
        sender.getInventory().addItem(Potion.builder().isSplash(false).amount(1).amplitude(1).duration(5).effect(PotionEffectType.INCREASE_DAMAGE).build().toItemStack());
    }
}
