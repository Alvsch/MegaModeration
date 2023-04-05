package me.alvsch.megamoderation.commands.commands.moderation;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.commands.Command;
import me.alvsch.megamoderation.utils.Utils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Unban extends Command {
    public Unban(MegaModeration plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "unban";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if(!(args.length >= 1)) {
            return false;
        }

        // Target not banned
        BanList banList = Bukkit.getBanList(BanList.Type.NAME);
        if(!banList.isBanned(args[0])) {
            sender.sendMessage(plugin.getPhrase("not-banned"));
            return true;
        }

        // Unban target
        banList.pardon(args[0]);
        sender.sendMessage(Utils.formatString(plugin.getPhrase("unbanned"), args[0]));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
