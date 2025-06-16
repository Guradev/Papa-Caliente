package net.gura.papaCaliente.game;

import net.gura.papaCaliente.PapaCaliente;
import net.gura.papaCaliente.utils.CustomItems;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
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

import static net.gura.papaCaliente.PapaCaliente.plugin;

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
    // Event for preventing people from offhanding or moving the hot potato
    @EventHandler
    public void PreventOffhand(PlayerSwapHandItemsEvent e) {
        Player player = e.getPlayer();
        ItemStack main = player.getInventory().getItemInMainHand();

        if (CustomItems.isPapaCaliente(main)) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) return;

        ItemStack current = e.getCurrentItem();
        ItemStack cursor = e.getCursor();

        if (CustomItems.isPapaCaliente(current) || CustomItems.isPapaCaliente(cursor)) {
            e.setCancelled(true);
            Bukkit.getScheduler().runTaskLater(plugin, player::updateInventory, 1L);
        }

    }
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) return;
        ItemStack item = e.getOldCursor();

        if (CustomItems.isPapaCaliente(item)) {
            e.setCancelled(true);
            Bukkit.getScheduler().runTaskLater(plugin, player::updateInventory, 1L);
        }
    }
}