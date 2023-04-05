package me.alvsch.megamoderation.commands.commands.chat;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.commands.Command;
import me.alvsch.megamoderation.commands.CommandManager;
import me.alvsch.megamoderation.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class StaffChat extends Command {
    public StaffChat(MegaModeration plugin) {
        super(plugin);

        addRequirement(CommandManager.Requirement.PLAYER);
    }

    @Override
    public String getName() {
        return "staffchat";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if(args.length >= 1){
            // Send message to staffchat
            Utils.staffchat(Utils.mergeList(args), player);
            return true;
        }

        // Toggle staffchat
        if(plugin.staffchat.contains(player.getUniqueId())){
            // Staffchat off

            plugin.staffchat.remove(player.getUniqueId());
            player.sendMessage(plugin.getPhrase("staffchat-off"));

        } else {
            // Staffchat on

            plugin.staffchat.add(player.getUniqueId());
            player.sendMessage(plugin.getPhrase("staffchat-on"));

        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
