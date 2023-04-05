package me.alvsch.megamoderation.commands.commands.moderation;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.commands.Command;
import me.alvsch.megamoderation.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Kick extends Command {
    public Kick(MegaModeration plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if(!(args.length >= 1)) {
            return false;
        }

        // Get target
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null || !target.isOnline()) {
            return false;
        }

        // Reason
        String reason = plugin.getPhrase("no-reason");
        if(args.length >= 2) {
            List<String> argsList = new ArrayList<>(Arrays.asList(args));
            argsList.remove(0);
            reason = String.join("", argsList);
        }

        // Kick target
        String kickMessage = "You got kicked for: " + reason;
        target.kickPlayer(kickMessage);

        sender.sendMessage(Utils.formatString(plugin.getPhrase("kicked"), target.getName()));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
