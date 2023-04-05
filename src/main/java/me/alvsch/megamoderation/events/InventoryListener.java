package me.alvsch.megamoderation.events;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener {

    MegaModeration plugin;

    public InventoryListener(MegaModeration plugin) {
        this.plugin = plugin;
    }

    // InventoryClickEvent
    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        User user = plugin.usermap.getUser(player);

        if(user.isInvsee()) {
            if(!player.hasPermission("megamoderation.invsee.modify")) {
                e.setCancelled(true);
            }
        }
    }

    // InventoryCloseEvent
    @EventHandler
    public void inventoryClose(InventoryCloseEvent e) {
        User user = plugin.usermap.getUser((Player)e.getPlayer());
        if(user.isInvsee()) {
            user.setInvsee(false);
        }
    }
}
