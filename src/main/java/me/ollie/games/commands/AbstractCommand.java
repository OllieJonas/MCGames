package me.ollie.games.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCommand implements CommandExecutor {

    private final Map<String, SubCommand> subCommandsMap = new HashMap<>();

    public AbstractCommand() {
        addSubCommands();
    }

    public abstract void addSubCommands();

    public void addSubCommand(SubCommand subCommand) {
        subCommandsMap.put(subCommand.getName(), subCommand);
        Arrays.stream(subCommand.getAliases()).forEach(s -> subCommandsMap.put(s, subCommand));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player))
            return notPlayer(sender);

        Player player = (Player) sender;

        if (args.length == 0)
            return noArguments(sender);

        String subCommandKey = args[0];

        SubCommand subCommand = subCommandsMap.get(subCommandKey);

        if (subCommand == null)
            return invalidSubCommand(sender);

        if (subCommand.isRequiresOp() && !player.isOp())
            return notOp(player);

        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);

        subCommand.run(player, newArgs);
        return false;
    }

    protected boolean noArguments(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "No arguments specified!");
        return false;
    }

    private boolean invalidSubCommand(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Invalid subcommand!");
        return false;
    }

    private boolean notPlayer(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "You're not a player! :(");
        return false;
    }

    private boolean notOp(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "You're not op!");
        return false;
    }
}
