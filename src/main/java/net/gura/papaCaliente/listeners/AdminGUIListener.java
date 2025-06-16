package net.gura.papaCaliente.listeners;

import net.gura.papaCaliente.PapaCaliente;
import net.gura.papaCaliente.game.GameManager;
import net.gura.papaCaliente.gui.AdminGUI;
import net.gura.papaCaliente.gui.PlayerManagerGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class AdminGUIListener implements Listener {

    @EventHandler
    public void ClickItem(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (event.getView().title().equals(AdminGUI.TITLE)) {
            event.setCancelled(true);

            // Removes the possibility of item pickup on shift click
            if (event.isShiftClick()) {
                event.setCancelled(true);
            }

            if (event.getClickedInventory() != event.getInventory()) {
                return;
            }

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            Material material = clickedItem.getType();
            GameManager gm = PapaCaliente.getPlugin().getGameManager();

            switch (material) {
                case LIME_WOOL -> {
                    player.performCommand("papacaliente start");
                }
                case RED_WOOL -> {
                    player.performCommand("papacaliente stop");
                }
                case CLOCK -> {
                    if (!gm.isRunning()) {
                        player.sendMessage(Component.text("¡El evento aún no ha iniciado!").color(NamedTextColor.RED));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10F, 1F);
                    }
                    // Logica para resetar contador
                    player.sendMessage("§fContador reseteado correctamente");
                }
                case TNT -> {
                    if (!gm.isRunning()) {
                        player.sendMessage(Component.text("¡El evento aún no ha iniciado!").color(NamedTextColor.RED));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10F, 1F);
                    }
                    if (gm.getCurrentHolder() != null) {
                        gm.getCurrentHolder().sendMessage("§c¡La papa caliente te explotó!");
                        gm.getCurrentHolder().getWorld().createExplosion(gm.getCurrentHolder().getLocation(), 3F, false, false);
                        gm.removePlayer(gm.getCurrentHolder());
                        player.sendMessage("§cExplotado correctamente!");
                    }
                }
                case PLAYER_HEAD -> {
                    if (!gm.isRunning()) {
                        player.sendMessage(Component.text("¡El evento aún no ha iniciado!").color(NamedTextColor.RED));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10F, 1F);
                    }
                    PlayerManagerGUI.openGUI(player);
                    player.sendMessage("§bAbriendo menu de gestión de jugadores...");
                }
                case BARRIER -> {
                    player.closeInventory();
                }
            }
        }
    }
}
