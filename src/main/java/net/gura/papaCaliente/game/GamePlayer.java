package net.gura.papaCaliente.game;

import net.gura.papaCaliente.PapaCaliente;
import net.gura.papaCaliente.nms.Respawn;
import net.gura.papaCaliente.utils.CustomItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

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

    @EventHandler
    public void preventPotatoEating(PlayerItemConsumeEvent e) {
        ItemStack item = e.getItem();
        if (item.getType() != Material.BAKED_POTATO) return;
        if (CustomItems.isPapaCaliente(item)) {
            e.setCancelled(true);
        }
    }
    // End of Events for handling prevention

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Player target)) return;

        Player clicker = e.getPlayer();

        if (!gm.isInGame(clicker) || !gm.isInGame(target)) return;

        if (!clicker.equals(gm.getCurrentHolder())) return;
        if (!CustomItems.isPapaCaliente(clicker.getInventory().getItemInMainHand())) return;

        if (clicker.equals(target)) return;
        gm.passPotato(clicker, target);

        clicker.sendMessage(
                Component.text("¡Has pasado la ", NamedTextColor.GREEN)
                        .append(Component.text("papa", NamedTextColor.RED))
                        .append(Component.text("!", NamedTextColor.GREEN))
        );

        target.sendMessage(
                Component.text("¡Te han pasado la ", NamedTextColor.RED)
                        .append(Component.text("papa", NamedTextColor.GOLD))
                        .append(Component.text("!", NamedTextColor.RED))
        );
    }

    @EventHandler
    public void onPlayerHitPlayer(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player clicker)) return;
        if (!(e.getEntity() instanceof Player target)) return;

        if (!gm.isInGame(clicker) || !gm.isInGame(target)) return;

        if (!clicker.equals(gm.getCurrentHolder())) return;
        if (!CustomItems.isPapaCaliente(clicker.getInventory().getItemInMainHand())) return;
        if (clicker.equals(target)) return;

        gm.passPotato(clicker, target);
        target.setGlowing(true);

        clicker.sendMessage(
                Component.text("¡Has pasado la ", NamedTextColor.GREEN)
                        .append(Component.text("papa", NamedTextColor.RED))
                        .append(Component.text(" a " + target.getName() + "!", NamedTextColor.GREEN))
        );

        target.sendMessage(
                Component.text("¡Te ha pasado la ", NamedTextColor.RED)
                        .append(Component.text("papa", NamedTextColor.GOLD))
                        .append(Component.text(" " + clicker.getName() + "!", NamedTextColor.RED))
        );

        e.setCancelled(true);
    }

    public static void spawnExplosionFirework(Player player) {
        if (player == null || !player.isOnline()) return;

        Location loc = player.getLocation().add(0, 1, 0);
        Firework firework = player.getWorld().spawn(loc, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();

        meta.addEffect(FireworkEffect.builder()
                .with(FireworkEffect.Type.BALL_LARGE)
                .withColor(Color.RED)
                .withFade(Color.ORANGE)
                .trail(false)
                .flicker(true)
                .build());
        firework.setFireworkMeta(meta);

        Bukkit.getScheduler().runTaskLater(plugin, firework::detonate, 1L);
    }


    @EventHandler
    public void playerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (gm.isRunning()) {
            player.setGlowing(false);

            e.getDrops().removeIf(CustomItems::isPapaCaliente);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                spawnExplosionFirework(player);
                Respawn.respawnPlayer(player);
            }, 1L);

            e.deathMessage(Component.empty());
        }
    }
}