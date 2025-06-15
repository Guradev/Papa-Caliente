package net.gura.papaCaliente.game;

import net.gura.papaCaliente.utils.CustomItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class GamePlayer implements Listener {

    //Events for handling prevention of Drops and Pickups for the "Papa Caliente"
    @EventHandler
    public void PreventPotatoMove(InventoryDragEvent e) {
        ItemStack item = e.getCursor();
        if (CustomItems.isPapaCaliente(item)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void PreventDropEvent(PlayerDropItemEvent e) {
        ItemStack item = e.getItemDrop().getItemStack();
        if (CustomItems.isPapaCaliente(item)) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void ItemPickup(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player player)) return;
        ItemStack item = e.getItem().getItemStack();
        if (CustomItems.isPapaCaliente(item)) {
            e.setCancelled(true);
        }
    }
    // End of Events for handling prevention

}
