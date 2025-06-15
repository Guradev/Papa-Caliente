package net.gura.papaCaliente;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PapaCaliente extends JavaPlugin {

    String version = Bukkit.getVersion();

    @Override
    public void onEnable() {

        getServer().getConsoleSender().sendMessage("Evento Papa Caliente Habilitado " + "Versión " + version);
        getServer().getConsoleSender().sendMessage("Made by Gura1");

    }

    @Override
    public void onDisable() {

        getServer().getConsoleSender().sendMessage("Evento Papa Caliente Deshabilitado " + "Versión " + version);
        getServer().getConsoleSender().sendMessage("Made by Gura1");
    }
}
