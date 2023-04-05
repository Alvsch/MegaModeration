package me.alvsch.megamoderation.commands.commands.utility;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.commands.Command;
import me.alvsch.megamoderation.commands.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class EnderSee extends Command {
    public EnderSee(MegaModeration plugin) {
        super(plugin);

        addRequirement(CommandManager.Requirement.PLAYER);
    }

    @Override
    public String getName() {
        return "endersee";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if(!(args.length >= 1)) {
            return false;
        }

        // Get target
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null || !target.isOnline()) {
            return false;
        }
        Player player = (Player) sender;

        // Open targets enderchest to player
        player.openInventory(target.getEnderChest());
        plugin.usermap.getUser(player).setInvsee(true);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
