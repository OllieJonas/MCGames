package me.ollie.games.commands.test;

import me.ollie.games.Games;
import me.ollie.games.commands.SubCommand;
import me.ollie.games.core.AbstractGameMap;
import me.ollie.games.games.MapCollectionFactory;
import me.ollie.games.games.survivalgames.SGMap;
import me.ollie.games.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TestSpawnLocations extends SubCommand {

    private List<Location> maps;
    private AtomicInteger counter;
    private int mapSize;
    private Player target;
    private boolean locked;
    private int taskId;

    public TestSpawnLocations() {
        super("spawnlocations", false, new String[]{"sl"});
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void run(Player sender, String[] args) {
        if (!locked) {

            if (args.length == 0 || args.length > 2) {
                sender.sendMessage(ChatColor.RED + "Invalid arguments! Arguments: /test spawnlocations <world> <player>");
                return;
            }
            AbstractGameMap abstractMap;

            abstractMap = MapCollectionFactory.getMapsFor("Survival Games").stream().filter(m -> m.getName().replace(' ', '_').equalsIgnoreCase(args[0])).findFirst().orElse(null);

            if (abstractMap == null) {
                sender.sendMessage(ChatColor.RED + "Unable to find map! :(");
                return;
            }

            if (!(abstractMap instanceof SGMap)) {
                sender.sendMessage(ChatColor.RED + "Map isn't a survival games map! (Shouldn't see this)");
                return;
            }

            SGMap map = (SGMap) abstractMap;

            target = args.length == 2 ? Bukkit.getPlayer(args[1]) : sender;

            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Couldn't find player!");
                return;
            }

            maps = new ArrayList<>(map.getSpawnLocations());
            MessageUtil.broadcast(maps.stream().map(m -> "X: " + m.getX() +  " Z: " + m.getZ()).collect(Collectors.joining(", ", "[", "]")));
            counter = new AtomicInteger(0);
            mapSize = maps.size();
            locked = true;
            taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Games.getInstance(), new Task(), 0L, 60L);
        } else {
            sender.sendMessage(ChatColor.RED + "Command is currently locked! Only one person can use it at a time :(");
        }
    }

    private void destroyTask(int task) {
        Bukkit.getScheduler().cancelTask(task);
    }

    private class Task implements Runnable {
        boolean interrupted = false;

        @Override
        public void run() {

            if (mapSize < counter.get() - 1) {
                interrupted = true;
                locked = false;
                destroyTask(taskId);
                target.sendMessage(ChatColor.GREEN + "Completed teleporting!");
                return;
            }

            if (!interrupted) {
                target.sendMessage(ChatColor.GRAY + "Sending you to spawn " + counter.get() + " / " + mapSize);
                Location targetLocation = maps.get(counter.get());
                target.teleport(targetLocation);
                counter.getAndIncrement();
            }
        }
    }
}
