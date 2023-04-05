package me.alvsch.megamoderation.commands.commands.utility;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.commands.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TogglePickup extends Command {

    public TogglePickup(MegaModeration plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "togglepickup";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        // Toggle pickup
        if (plugin.disablepickup.contains(player.getUniqueId())) {
            // Pickup off
            plugin.disablepickup.remove(player.getUniqueId());
            player.sendMessage(plugin.getPhrase("togglepickup-on"));

        } else {
            // Pickup on
            plugin.disablepickup.add(player.getUniqueId());
            player.sendMessage(plugin.getPhrase("togglepickup-off"));

        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
