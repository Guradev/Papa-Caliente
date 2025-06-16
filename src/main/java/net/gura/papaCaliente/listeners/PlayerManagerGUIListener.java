package net.gura.papaCaliente.listeners;

import net.gura.papaCaliente.PapaCaliente;
import net.gura.papaCaliente.game.GameManager;
import net.gura.papaCaliente.gui.AdminGUI;
import net.gura.papaCaliente.gui.PlayerManagerGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerManagerGUIListener implements Listener {
    @EventHandler
    public void ClickItem(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (event.getView().title().equals(PlayerManagerGUI.TITLE)) {
            event.setCancelled(true);

            if (event.isShiftClick()) {
                event.setCancelled(true);
            }

            if (event.getClickedInventory() != event.getInventory()) {
                return;
            }

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            Material material = clickedItem.getType();

            switch (material) {
                case ARROW -> {
                    AdminGUI.openGUI(player);
                }
                case BARRIER -> {
                    player.closeInventory();
                }
            }
        }
    }
}
