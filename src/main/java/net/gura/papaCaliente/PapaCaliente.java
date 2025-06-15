package net.gura.papaCaliente;

import net.gura.papaCaliente.commands.AdminCommand;
import net.gura.papaCaliente.commands.PapaCalienteCommand;
import net.gura.papaCaliente.game.GameManager;
import net.gura.papaCaliente.listeners.AdminGUIListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PapaCaliente extends JavaPlugin {

    public static PapaCaliente plugin;
    private GameManager gameManager;

    String version = Bukkit.getVersion();

    @Override
    public void onEnable() {
        plugin = this;
        gameManager = new GameManager();
        getServer().getConsoleSender().sendMessage("Evento Papa Caliente Habilitado " + "Versión " + version);
        getServer().getConsoleSender().sendMessage("Made by Gura1");

        // Registramos los eventos del plugin
        getServer().getPluginManager().registerEvents(new AdminGUIListener(), this);

    }

    public static PapaCaliente getPlugin() {
        return plugin;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    @Override
    public void onDisable() {

        getServer().getConsoleSender().sendMessage("Evento Papa Caliente Deshabilitado " + "Versión " + version);
        getServer().getConsoleSender().sendMessage("Made by Gura1");
    }

    public void registerCommands() {
        //Crear un handler para que maneje el comando /papacaliente
        getCommand("papacaliente").setExecutor(new PapaCalienteCommand());
    }
}
