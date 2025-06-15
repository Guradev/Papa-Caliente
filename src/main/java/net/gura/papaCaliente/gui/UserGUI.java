package net.gura.papaCaliente.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class UserGUI implements Listener {
    public static final String TITLE = "Papa Caliente";


    public static void openGUI(Player usuario) {
        Inventory inv = Bukkit.createInventory(null, 9, TITLE);

    }

}
