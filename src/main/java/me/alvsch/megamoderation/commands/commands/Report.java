package me.alvsch.megamoderation.commands.commands;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.commands.Command;
import me.alvsch.megamoderation.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Report extends Command {

    public Report(MegaModeration plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "report";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if(!(args.length >= 2)) {
            return false;
        }

        // Get target
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            return false;
        }

        // Reason
        List<String> list = Arrays.asList(args);
        list.remove(0);
        String reason = list.toString();

        // Send report to online moderators
        Bukkit.broadcast(Utils.formatReport(sender, target, reason), "megamoderation.report.see");

        sender.sendMessage(plugin.getPhrase("report-response"));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
