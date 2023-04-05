package me.alvsch.megamoderation.commands.main;


import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.commands.Command;
import me.alvsch.megamoderation.commands.SubCommand;
import me.alvsch.megamoderation.commands.main.subcommands.Author;
import me.alvsch.megamoderation.commands.main.subcommands.Reload;
import me.alvsch.megamoderation.utils.Utils;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Main extends Command {


    public Main(MegaModeration plugin) {
        super(plugin);

        addSubCommand(new Reload(plugin, this));
        addSubCommand(new Author(plugin, this));
    }

    @Override
    public String getName() {
        return "megamoderation";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> completion = new ArrayList<>();

        if(args.length > 0) {
            if(this.getSubCommands() != null) {
                for(SubCommand subCommand : this.getSubCommands()) {
                    if(Utils.hasPermission(sender, subCommand.getPermission())) {
                        completion.add(subCommand.getName());
                    }
                }
            }
        }

        return completion;
    }
}
