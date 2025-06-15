package net.gura.papaCaliente.listeners;

import net.gura.papaCaliente.PapaCaliente;
import net.gura.papaCaliente.game.GameManager;
import net.gura.papaCaliente.gui.AdminGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class AdminGUIListener implements Listener {
    @EventHandler
    public void ClickItem(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getView().getTitle().equals(AdminGUI.TITLE)) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            Material material = clickedItem.getType();
            GameManager gm = PapaCaliente.getPlugin().getGameManager();

            switch (material) {
                case LIME_WOOL -> {
                    gm.startGame();
                    player.sendMessage("§aEvento iniciado correctamente!");
                }
                case RED_WOOL -> {
                    gm.stopGame();
                    player.sendMessage("§cEvento parado correctamente.");
                }
                case CLOCK -> {
                    player.sendMessage("Contador reseteado correctamente");
                }
                case TNT -> {
                    if (gm.getCurrentHolder() != null) {
                        gm.getCurrentHolder().getWorld().createExplosion(gm.getCurrentHolder().getLocation(), 2F);
                        player.sendMessage("§cExplotado correctamente :)");
                    }
                }
                case PLAYER_HEAD -> {
                    player.sendMessage("§bAbriendo menu de gestión de jugadores...");
                }
                case BARRIER -> {
                    player.closeInventory();
                }
            }

        }
    }
}
