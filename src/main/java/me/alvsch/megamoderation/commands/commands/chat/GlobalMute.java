package me.alvsch.megamoderation.commands.commands.chat;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.commands.Command;
import me.alvsch.megamoderation.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

public class GlobalMute extends Command {
    public GlobalMute(MegaModeration plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "globalmute";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        // Toggle globalmute
        plugin.globalmute = !plugin.globalmute;

        if(plugin.globalmute){
            // Globalmute on

            if(args.length >= 1 && args[0].contains("-s")) {
                Bukkit.broadcast(plugin.getPhrase("silent") +
                                Utils.formatString(plugin.getPhrase("muted-chat"), sender.getName()),
                        "megamoderation.silent.see");

            } else {
                Bukkit.broadcastMessage(Utils.formatString(plugin.getPhrase("muted-chat"), sender.getName()));
            }

        } else {
            // Globalmute off

            if(args.length >= 1 && args[0].contains("-s")) {

                Bukkit.broadcast(plugin.getPhrase("silent") +
                                Utils.formatString(plugin.getPhrase("unmuted-chat"), sender.getName()),
                        "megamoderation.silent.see");

            } else {
                Bukkit.broadcastMessage(Utils.formatString(plugin.getPhrase("unmuted-chat"), sender.getName()));
            }

        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
