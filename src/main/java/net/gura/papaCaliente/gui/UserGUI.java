package net.gura.papaCaliente.gui;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class UserGUI implements Listener {
    private Inventory inventory;

    public UserGUI() {
        inventory = Bukkit.createInventory(null, 9, "User GUI");


    }

}
