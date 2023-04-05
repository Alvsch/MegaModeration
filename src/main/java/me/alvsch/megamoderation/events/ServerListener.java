package me.alvsch.megamoderation.events;

import me.alvsch.megamoderation.MegaModeration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class ServerListener implements Listener {

    MegaModeration plugin;

    public ServerListener(MegaModeration plugin) {
        this.plugin = plugin;
    }

    // EntityPickupItemEvent
    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
        if(plugin.disablepickup.contains(e.getEntity().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    // EntityDamageByEntityEvent
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player) {
            if(plugin.freeze.contains((Player) e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }

}
