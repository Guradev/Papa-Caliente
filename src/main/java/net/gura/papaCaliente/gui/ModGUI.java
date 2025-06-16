package net.gura.papaCaliente.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ModGUI {

    public static final String TITLE = ChatColor.BLUE + "ᴍᴏᴅᴇʀᴀᴄɪóɴ ᴇᴠᴇɴᴛᴏ";

    public static void openGUI(Player mod) {
        Inventory inv = Bukkit.createInventory(null, 54, TITLE);


    }

}
