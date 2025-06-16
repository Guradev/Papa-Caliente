package net.gura.papaCaliente.game;

import net.gura.papaCaliente.PapaCaliente;
import net.gura.papaCaliente.utils.CustomItems;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class GamePlayer implements Listener {

    GameManager gm = PapaCaliente.getPlugin().getGameManager();

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
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Player target)) return;

        Player click = e.getPlayer();

        if (click.equals(gm.getCurrentHolder()) && CustomItems.isPapaCaliente(click.getInventory().getItemInMainHand())) {
            gm.passPotato(click,target);
            gm.setCurrentHolder(target);

            click.sendMessage("¡Has pasado la Papa Caliente a " + target.getName() + " !");
            target.sendMessage("¡Te ha pasado la Papa Caliente " + click.getName() + " !");

        }
    }
    // Event for preventing people from offhanding
    @EventHandler
    public void PreventOffhand(PlayerSwapHandItemsEvent e) {
        Player player = e.getPlayer();
        ItemStack main = player.getInventory().getItemInMainHand();

        if (CustomItems.isPapaCaliente(main)) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        if (event.getSlotType() == InventoryType.SlotType.QUICKBAR && event.getHotbarButton() == 40) { // 40 is offhand
            if (CustomItems.isPapaCaliente(item)) {
                event.setCancelled(true);
            }
        }

        if (event.getSlot() == 40 && CustomItems.isPapaCaliente(item)) { // Prevent direct placing in offhand
            event.setCancelled(true);
        }
    }

}