package me.alvsch.megamoderation.commands.commands.utility;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.commands.Command;
import me.alvsch.megamoderation.commands.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Vanish extends Command {
    public Vanish(MegaModeration plugin) {
        super(plugin);

        addRequirement(CommandManager.Requirement.PLAYER);
    }

    @Override
    public String getName() {
        return "vanish";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        // Check for on/off args
        if(args.length >= 1) {
            if(args[0].equalsIgnoreCase("on")) {
                vanish(player);
            } else if(args[0].equalsIgnoreCase("off")) {
                unvanish(player);
            }
            return true;
        }

        // Toggle vanish
        if(plugin.vanish.contains(player)) {
            unvanish(player);
        } else {
            vanish(player);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> completion = new ArrayList<>();

        if(args.length == 1)  {
            completion.add("on");
            completion.add("off");
        }

        return completion;
    }

    private void vanish(Player player){
        plugin.vanish.add(player);
        if (plugin.getConfig().getBoolean("vanish.silent-container")) {
            plugin.silentcontainer.add(player.getUniqueId());
        }
        if (plugin.getConfig().getBoolean("vanish.toggle-pickup")) {
            plugin.disablepickup.add(player.getUniqueId());
        }
        player.sendMessage(plugin.getPhrase("vanish-on"));

        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p.hasPermission("megamoderation.vanish.see")) {
                continue;
            }
            p.hidePlayer(plugin, player);
        }
    }
    private void unvanish(Player player) {
        plugin.vanish.remove(player);
        if(plugin.getConfig().getBoolean("vanish.silent-container")) {
            plugin.silentcontainer.remove(player.getUniqueId());
        }
        if (plugin.getConfig().getBoolean("vanish.toggle-pickup")) {
            plugin.disablepickup.remove(player.getUniqueId());
        }
        player.sendMessage(plugin.getPhrase("vanish-off"));

        for(Player p : Bukkit.getOnlinePlayers()) {
            p.showPlayer(plugin, player);
        }
    }
}
