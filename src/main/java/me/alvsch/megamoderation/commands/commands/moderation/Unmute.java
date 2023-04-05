package me.alvsch.megamoderation.commands.commands.moderation;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.User;
import me.alvsch.megamoderation.commands.Command;
import me.alvsch.megamoderation.utils.Utils;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.UUID;

public class Unmute extends Command {
    public Unmute(MegaModeration plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "unmute";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if(!(args.length >= 1)) {
            return false;
        }

        UUID target = plugin.usermap.getUUID(args[0]);
        if(target == null) {
            return false;
        }
        User user = plugin.usermap.getUser(target);

        if(user.getMute() == null) {
            sender.sendMessage(plugin.getPhrase("not-muted"));
            return true;
        }

        user.setMute(null);

        sender.sendMessage(Utils.formatString(plugin.getPhrase("unmuted"), args[0]));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
