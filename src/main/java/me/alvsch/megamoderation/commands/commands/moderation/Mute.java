package me.alvsch.megamoderation.commands.commands.moderation;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.MuteEntry;
import me.alvsch.megamoderation.User;
import me.alvsch.megamoderation.commands.Command;
import me.alvsch.megamoderation.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class Mute extends Command {
    public Mute(MegaModeration plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "mute";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if(!(args.length >= 1)) {
            return false;
        }

        UUID uuid = plugin.usermap.getUUID(args[0]);
        if(uuid == null) {
            return false;
        }
        User user = plugin.usermap.getUser(uuid);

        if(user.getMute() != null) {
            plugin.getPhrase("already-muted");
            return true;
        }

        UUID source = null;
        if(sender instanceof Player) {
            source = ((Player) sender).getUniqueId();
        }

        String reason = plugin.getPhrase("no-reason");

        if(args.length == 1) {
            user.setMute(new MuteEntry(
                    user.getUniqueId(),
                    reason,
                    null,
                    source)
            );
            return true;
        }

        long time = Utils.stringToMilli(args[1]);
        if(time < 0) {
            List<String> argsList = new ArrayList<>(Arrays.asList(args));
            argsList.remove(0);
            reason = String.join("", argsList);

            user.setMute(new MuteEntry(
                    user.getUniqueId(),
                    reason,
                    null,
                    source)
            );

            return true;
        }
        time += new Date().getTime();

        if(args.length >= 3) {
            List<String> argsList = new ArrayList<>(Arrays.asList(args));
            argsList.remove(0);
            argsList.remove(0);
            reason = String.join("", argsList);
        }

        user.setMute(new MuteEntry(
                user.getUniqueId(),
                reason,
                new Date(time),
                source)
        );

        sender.sendMessage(Utils.formatString(plugin.getPhrase("muted"), args[0]));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
