package net.gura.papaCaliente.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ModGUI {

    public static final String TITLE = ChatColor.BLUE + "Moderación Evento";

    public static void openGUI(Player mod) {
        Inventory inv = Bukkit.createInventory(null, 9, TITLE);

    }

}
