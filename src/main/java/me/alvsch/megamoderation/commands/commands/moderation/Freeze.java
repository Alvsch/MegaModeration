package me.alvsch.megamoderation.commands.commands.moderation;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.commands.Command;
import me.alvsch.megamoderation.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Freeze extends Command {
    public Freeze(MegaModeration plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "freeze";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if(!(args.length >= 1)) {
            return false;
        }

        // Get target
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            return false;
        }

        // Target frozen
        if(!plugin.freeze.contains(target)) {
            // Freeze target
            plugin.freeze.add(target);

            sender.sendMessage(Utils.formatString(plugin.getPhrase("frozen-other"), target.getName()));
            target.sendMessage(plugin.getPhrase("frozen"));

        } else {
            // Unfreeze target
            plugin.freeze.remove(target);

            sender.sendMessage(Utils.formatString(plugin.getPhrase("unfrozen-other"), target.getName()));
            target.sendMessage(plugin.getPhrase("unfrozen"));

        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
