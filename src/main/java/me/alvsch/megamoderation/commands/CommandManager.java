package me.alvsch.megamoderation.commands;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    private final ArrayList<me.alvsch.megamoderation.commands.Command> commands = new ArrayList<>();
    MegaModeration plugin;

    public CommandManager(MegaModeration plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("ConstantConditions")
    public void init() {
        // Load all commands
        for(me.alvsch.megamoderation.commands.Command command : commands) {
            plugin.getCommand(command.getName()).setExecutor(this);
            plugin.getCommand(command.getName()).setTabCompleter(this);
        }
    }
    public void addCommand(me.alvsch.megamoderation.commands.Command command) {
        commands.add(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Get command
        me.alvsch.megamoderation.commands.Command cmd = this.get(command.getName());

        // Meets requirements
        ArrayList<Requirement> requirements = cmd.getRequirements();
        if(!meetsRequirements(requirements, sender)) {
            return true;
        }

        // Check for subcommands
        if(cmd.getSubCommands().size() != 0) {
            if(args.length > 0) {
                // Get subcommand
                SubCommand subcmd = this.getSub(args[0], cmd);
                if(subcmd == null) {
                    sender.sendMessage(this.syntaxMsg(cmd));
                    return true;
                }

                // Subcommand checks
                if(!onSubCommand(sender, subcmd, args)) {
                    sender.sendMessage(this.syntaxMsg(cmd));
                }
                return true;
            }
        }

        // Run command
        if(!cmd.onCommand(sender, args)) {
            sender.sendMessage(this.syntaxMsg(cmd));
        }

        return true;
    }
    public boolean onSubCommand(CommandSender sender, SubCommand subCommand, String[] args) {
        // Meets requirements
        ArrayList<Requirement> requirements = subCommand.getRequirements();
        if(!meetsRequirements(requirements, sender)) return true;
        if(!Utils.hasPermission(sender, subCommand.getPermission())) {
            sender.sendMessage(plugin.getPhrase("permission-message"));
            return true;
        }

        // Remove subcommand from args
        List<String> list = new ArrayList<>(Arrays.asList(args));
        list.remove(0);
        args = list.toArray(new String[0]);

        // Run subcommand
        if(!subCommand.onCommand(sender, args)) {
            sender.sendMessage(this.syntaxMsg(subCommand));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // Get tabcompletion
        return this.get(command.getName()).onTabComplete(sender, args);
    }

    public me.alvsch.megamoderation.commands.Command get(String command) {
        for(me.alvsch.megamoderation.commands.Command c : this.commands) {
            if(c.getName().equalsIgnoreCase(command)) return c;
        }
        return null;
    }
    public me.alvsch.megamoderation.commands.SubCommand getSub(String subcommand, me.alvsch.megamoderation.commands.Command cmd) {
        for(me.alvsch.megamoderation.commands.SubCommand sc : cmd.getSubCommands()) {
            if(sc.getName().equalsIgnoreCase(subcommand)) return sc;

            if(sc.getAliases() == null) continue;

            for(String a : sc.getAliases()) {
                if(a.equalsIgnoreCase(subcommand)) return sc;
            }
        }
        return null;
    }

    private String syntaxMsg(me.alvsch.megamoderation.commands.Command command) {
        StringBuilder sb = new StringBuilder();

        sb.append("----- ").append(Utils.title(command.getName())).append(" -----").append("\n");
        sb.append("<> = required, [] = optional").append("\n");
        sb.append("Usage: ").append(command.getUsage()).append("\n");
        for(SubCommand sc : command.getSubCommands()) {
            sb.append(sc.getName()).append(" - ").append(Utils.nullToEmpty(sc.getDescription())).append("\n");
        }
        sb.append("---------------");

        return Utils.color(plugin.getPhrase("primary-color") + sb);

    }
    private String syntaxMsg(me.alvsch.megamoderation.commands.SubCommand command) {
        String s = "----- " + Utils.title(command.getCommand().getName()) + " -----\n" +
                "/" + command.getCommand().getName() + " " + Utils.nullToEmpty(command.getUsage()) + "\n" +
                    "---------------";

        return Utils.color(plugin.getPhrase("primary-color") + s);

    }

    private boolean meetsRequirements(ArrayList<Requirement> requirements, CommandSender sender) {
        // Only players
        if(requirements.contains(Requirement.PLAYER)) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(plugin.getPhrase("only-player"));
                return false;
            }
        }

        // Only console
        if(requirements.contains(Requirement.CONSOLE)) {
            if(sender instanceof Player){
                sender.sendMessage(plugin.getPhrase("only-console"));
                return false;
            }
        }

        return true;
    }


    public enum Requirement {
        PLAYER, CONSOLE
    }

}
