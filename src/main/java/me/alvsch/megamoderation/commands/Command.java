package me.alvsch.megamoderation.commands;

import me.alvsch.megamoderation.MegaModeration;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {

    protected MegaModeration plugin;

    private final ArrayList<CommandManager.Requirement> requirements = new ArrayList<>();
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();
    private final org.bukkit.command.Command command;

    public Command(MegaModeration plugin) {
        this.plugin = plugin;
        this.command = plugin.getCommand(getName());
    }

    public abstract String getName();
    public abstract boolean onCommand(CommandSender sender, String[] args);
    public abstract List<String> onTabComplete(CommandSender sender, String[] args);

    public void addSubCommand(SubCommand sc) {
        this.subCommands.add(sc);
    }
    public ArrayList<SubCommand> getSubCommands() {
        return this.subCommands;
    }

    public void addRequirement(CommandManager.Requirement requirement) {
        this.requirements.add(requirement);
    }
    public ArrayList<CommandManager.Requirement> getRequirements() {
        return requirements;
    }

    public String getUsage() {
        return command.getUsage();
    }
    public List<String> getAliases() {
        return command.getAliases();
    }
    public String getDescription() {
        return command.getDescription();
    }


}
