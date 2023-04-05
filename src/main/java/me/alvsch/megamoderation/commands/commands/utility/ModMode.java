package me.alvsch.megamoderation.commands.commands.utility;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.commands.Command;
import me.alvsch.megamoderation.commands.CommandManager;
import me.alvsch.megamoderation.utils.InventoryUtils;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class ModMode extends Command {
    public ModMode(MegaModeration plugin) {
        super(plugin);

        addRequirement(CommandManager.Requirement.PLAYER);
    }

    @Override
    public String getName() {
        return "modmode";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        // Toggle
        if (plugin.modmode.containsKey(player)) {
            // Exit modmode
            player.getInventory().setContents(plugin.modmode.remove(player));

            // Toggle gamemode
            if(!player.hasPermission("megamoderation.modmode.keepgamemode")) {
                player.setGameMode(GameMode.SURVIVAL);
            }

            // Toggle flight
            if(!player.hasPermission("megamoderation.modmode.keepflight")) {
                player.setAllowFlight(false);
                player.setFlying(false);
            }

            // Toggle vanish
            String[] a = {"off"};
            plugin.commandManager.get("vanish").onCommand(sender, a);

            player.sendMessage(plugin.getPhrase("modmode-off"));

        } else {
            // Enter Modmode
            Inventory inv = player.getInventory();

            // Save and clear inventory
            plugin.modmode.put(player, inv.getContents());
            inv.clear();

            // Give modmode items
            InventoryUtils.giveModModeItems(inv, plugin);

            // Toggle gamemode
            player.setGameMode(GameMode.CREATIVE);

            // Toggle flight
            player.setAllowFlight(true);

            // Toggle vanish
            String[] a = {"on"};
            plugin.commandManager.get("vanish").onCommand(sender, a);

            player.sendMessage(plugin.getPhrase("modmode-on"));

        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
