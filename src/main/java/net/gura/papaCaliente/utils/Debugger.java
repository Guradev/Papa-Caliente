package net.gura.papaCaliente.utils;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Debugger {
    private final JavaPlugin plugin;
    private static boolean debug = false;

    public Debugger(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void setDebugValue(boolean value) {
        debug = value;
    }

    public static boolean isDebugEnabled() {
        return debug;
    }

    public static void sendDebug(Player player, String message) {
        if(debug) {
            player.sendMessage(NamedTextColor.YELLOW + "[EVENTO DEBUG] " + message);
        }
    }
    public void log(String message) {
        if(debug) {
            plugin.getLogger().info(NamedTextColor.YELLOW + "[PAPA CALIENTE DEBUG] " + message);
        }
    }
}
