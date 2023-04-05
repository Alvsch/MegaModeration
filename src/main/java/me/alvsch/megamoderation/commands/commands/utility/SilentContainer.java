package me.alvsch.megamoderation.commands.commands.utility;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.commands.Command;
import me.alvsch.megamoderation.commands.CommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SilentContainer extends Command {
    public SilentContainer(MegaModeration plugin) {
        super(plugin);

        addRequirement(CommandManager.Requirement.PLAYER);
    }

    @Override
    public String getName() {
        return "silentcontainer";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        // Toggle silentcontainer
        if (plugin.silentcontainer.contains(player.getUniqueId())) {
            // Silentcontainer off
            plugin.silentcontainer.remove(player.getUniqueId());
            player.sendMessage(plugin.getPhrase("silentcontainer-off"));

        } else {
            // Silentcontainer on
            plugin.silentcontainer.add(player.getUniqueId());
            player.sendMessage(plugin.getPhrase("silentcontainer-on"));

        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
