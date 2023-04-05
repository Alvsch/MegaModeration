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
import java.util.List;

public class Ban extends Command {
    public Ban(MegaModeration plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if(!(args.length >= 1)) {
            return false;
        }

        // Get player
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
        if(args.length >= 2) {
            List<String> argsList = new ArrayList<>(Arrays.asList(args));
            argsList.remove(0);
            reason = String.join("", argsList);
        }

        // Ban and Kick player
        BanEntry banEntry = banList.addBan(target.getName(), reason, null, sender.getName());
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
