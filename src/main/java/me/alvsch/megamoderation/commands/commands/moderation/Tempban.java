package me.alvsch.megamoderation.commands.commands.moderation;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.commands.Command;
import me.alvsch.megamoderation.utils.Utils;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Tempban extends Command {
    public Tempban(MegaModeration plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "tempban";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if(!(args.length >= 2)) {
            return false;
        }

        // Get target
        Player target = Bukkit.getPlayerExact(args[0]);
        if(target == null) {
            return false;
        }

        // Target banned
        BanList banList = Bukkit.getBanList(BanList.Type.NAME);
        if (banList.isBanned(target.getUniqueId().toString())) {
            plugin.getPhrase("already-banned");
            return true;
        }

        // Reason
        String reason = plugin.getPhrase("no-reason");
        if(args.length >= 3) {
            List<String> argsList = new ArrayList<>(Arrays.asList(args));
            argsList.remove(0);
            argsList.remove(0);
            reason = String.join("", argsList);
        }

        // Convert string to milliseconds
        long time = Utils.stringToMilli(args[1]);
        time += new Date().getTime();

        // Ban and Kick target
        BanEntry banEntry = banList.addBan(target.getName(), reason, new Date(time), sender.getName());
        if(banEntry == null) {
            target.kickPlayer("You got banned!");
        } else {
            target.kickPlayer(Utils.formatBanReason(banEntry));
        }

        sender.sendMessage(Utils.formatString(plugin.getPhrase("banned"), target.getName()));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
