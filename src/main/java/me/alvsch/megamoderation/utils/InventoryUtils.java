package me.alvsch.megamoderation.utils;

import me.alvsch.megamoderation.MegaModeration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class InventoryUtils {

    public static Inventory createInventory(InventoryHolder owner, int rows, String name) {
        return Bukkit.createInventory(owner, rows * 9, Utils.color(name));
    }

    public static void fillRows(ItemStack item, Inventory inv, int start, int end) {
        for(int i = start - 1; i < end; i++) {
            inv.setItem(i, item);
        }

    }

    public static void addItems(Collection<ItemStack> items, Inventory inv) {

        for(ItemStack i : items) {
            inv.addItem(i);
        }

    }

    public static void giveModModeItems(Inventory inv, MegaModeration plugin) {
        inv.setItem(0, plugin.itemHandler.player_tracker);
        inv.setItem(1, plugin.itemHandler.freeze_staff);
        inv.setItem(2, plugin.itemHandler.vanish_on);
        inv.setItem(3, plugin.itemHandler.inspect_tool);
    }

}
