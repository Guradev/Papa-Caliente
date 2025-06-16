package net.gura.papaCaliente.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Messenger {
    public static void broadcast(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }
}
