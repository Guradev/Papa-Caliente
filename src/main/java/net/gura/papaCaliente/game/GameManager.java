package net.gura.papaCaliente.game;

import net.gura.papaCaliente.logics.Countdown;
import net.gura.papaCaliente.ui.BossBarHandler;
import net.gura.papaCaliente.utils.CustomItems;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static net.gura.papaCaliente.PapaCaliente.plugin;

public class GameManager {
    private Countdown countdown;
    private GameState gameState = GameState.ESPERANDO;
    private final Set<Player> players = new HashSet<>();
    private Player currentHolder = null;
    BossBarHandler bossbar = new BossBarHandler("Papa Caliente", 1f, BossBar.Color.YELLOW, BossBar.Overlay.PROGRESS);

    public GameState getGameState() {
        return gameState;
    }

    public boolean isRunning() {
        return gameState == GameState.CORRIENDO;
    }

    public void addPlayer() {

    }

    public void removePlayer(Player player) {

        players.remove(player);

        if (player.equals(currentHolder)) {
            removePotato(player);
            currentHolder = null; // O elegir nuevo holder si hay jugadores suficientes
        }
        if (players.size() <= 1) {
            stopGame();
        }
    }

    public void startGame() {
        if (players.size() < 2) {
            return;
        }

        gameState = GameState.CORRIENDO;

        //Elegir a una persona random de la lista para darle la papa caliente
        List<Player> listaPlayers = new ArrayList<>(players);
        Collections.shuffle(listaPlayers);
        currentHolder = listaPlayers.getFirst();

        givePotato(currentHolder);

        // Falta el código para tener una bossbar dinámica (se utilizará adventureapi)
        countdown = new Countdown(plugin, 10,
                secondsLeft -> {
                    bossbar.show()
                    currentHolder.sendMessage("§6¡La papa explotará en §f" + secondsLeft + "s§e!");
                },
                () -> {
                    // Explotó la papa
                    removePotato(currentHolder);
                    currentHolder.sendMessage("§c¡La papa caliente te explotó!");
                    currentHolder.getWorld().createExplosion(currentHolder.getLocation(), 3F, false, false);
                    removePlayer(currentHolder);

                    if (players.size() < 2) {
                        stopGame();
                    } else {
                        startGame(); // Repetimos la lógica con otro jugador
                    }
                }
        );

        countdown.start();
    }

    public void stopGame() {
        gameState = GameState.TERMINADO;

        // Limpiamos los players del set y quitamos el current holder
        players.clear();
        currentHolder = null;

        //Falta añadir logica que se termine el juego de forma correcta, con titulos, etc.
    }

    public Set<Player> getPlayers() {
        return Collections.unmodifiableSet(players);
    }

    public Player getCurrentHolder() {
        return currentHolder;
    }

    public void setCurrentHolder(Player player) {
        currentHolder = player;
    }

    public void passPotato(Player de, Player a) {
        if (!players.contains(a)) return;

        removePotato(de);
        givePotato(a);
        currentHolder = a;

        // Falta implementar la logica del countdown
    }

    private void givePotato(Player player) {
        ItemStack papa = CustomItems.PapaCaliente(player, 100);
        player.getInventory().setItem(1, papa);
        // Testing required for checking if the updateInventory is required
    }

    private void removePotato(Player player) {
        // Logic for removing the potato from the player
        ItemStack item = player.getInventory().getItem(1);
        if (CustomItems.isPapaCaliente(item)) {
            player.getInventory().setItem(4, null);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1F, 1F);
            player.sendMessage("§aHas pasado la papa caliente.");
        }
    }
}
