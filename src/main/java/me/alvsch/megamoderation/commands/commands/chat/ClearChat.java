package me.alvsch.megamoderation.commands.commands.chat;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.commands.Command;
import me.alvsch.megamoderation.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ClearChat extends Command {

    public ClearChat(MegaModeration plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "clearchat";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p.hasPermission("megamoderation.clearchat.bypass")) {
                continue;
            }

            for(int i = 0; i < 100; i++){
                p.sendMessage("");
            }

        }
        // Silent
        if(args.length >= 1 && args[0].contains("-s")) {
            Bukkit.broadcast(plugin.getPhrase("silent") +
                            Utils.formatString(plugin.getPhrase("cleared-chat"), sender.getName()),
                    "megamoderation.silent.see");
        } else {
            Bukkit.broadcastMessage(Utils.formatString(plugin.getPhrase("cleared-chat"), sender.getName()));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
