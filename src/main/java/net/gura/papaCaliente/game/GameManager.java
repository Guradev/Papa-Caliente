package net.gura.papaCaliente.game;

import net.gura.papaCaliente.logics.Countdown;
import net.gura.papaCaliente.ui.BossBarHandler;
import net.gura.papaCaliente.utils.CustomItems;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.*;

import static net.gura.papaCaliente.PapaCaliente.plugin;

public class GameManager {
    private Countdown countdown;
    private GameState gameState = GameState.ESPERANDO;
    private final Set<Player> players = new HashSet<>();
    private Player currentHolder = null;
    BossBarHandler bossbar = new BossBarHandler("Papa Caliente", 1f, BossBar.Color.YELLOW, BossBar.Overlay.PROGRESS);

    private final Boolean isTesting = true; // For testing purposes only (disable in production)

    public GameState getGameState() {
        return gameState;
    }

    public boolean isRunning() {
        return gameState == GameState.CORRIENDO;
    }

    public boolean getIsTesting() {
        return isTesting;
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.sendMessage(Component.text("¡Fuiste agregado al evento de papa caliente!").color(NamedTextColor.GOLD));
    }

    public void removePlayer(Player player) {

        players.remove(player);

        if (player.equals(currentHolder)) {
            removePotato(player);
            currentHolder = null;
        }
        if (players.size() <= 1) {
            stopGame();
        }
    }

    public boolean isInGame(Player player) {
        return players.contains(player);
    }

    public void startGame() {
        if (players.size() < 2 && !isTesting) {
            return;
        }

        List<Player> listaPlayers = new ArrayList<>(players);
        if (listaPlayers.isEmpty()) {
            return;
        }

        gameState = GameState.CORRIENDO;

        //Elige a una persona random de la lista para darle la papa caliente
        Collections.shuffle(listaPlayers);
        currentHolder = listaPlayers.get(0);

        givePotato(currentHolder);

        countdown = new Countdown(plugin, 10,
                secondsLeft -> {
                    bossbar.ShowToAll(players);

                    float progress = Math.max(0f, secondsLeft / 10f);
                    bossbar.updateProgress(progress);
                    bossbar.updateTitle("⏳ ¡" + secondsLeft + "s para explotar!");

                    currentHolder.sendMessage(
                            Component.text("¡La papa explotará en ", NamedTextColor.GOLD)
                                    .append(Component.text(secondsLeft + "s", NamedTextColor.WHITE))
                                    .append(Component.text("!", NamedTextColor.GOLD))
                    );
                },
                () -> {
                    removePotato(currentHolder);
                    currentHolder.sendMessage(Component.text("¡La papa te explotó!").color(NamedTextColor.RED));
                    currentHolder.getWorld().createExplosion(currentHolder.getLocation(), 3F, false, false);
                    removePlayer(currentHolder);

                    bossbar.HideToAll(players);

                    if (players.size() < 2) {
                        stopGame();
                    } else {
                        startGame();
                    }
                }
        );
        countdown.start();
    }

    public void stopGame() {
        gameState = GameState.TERMINADO;
        bossbar.HideToAll(players);

        if (countdown != null) {
            countdown.cancel();
            countdown = null;
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            ItemStack[] contents = player.getInventory().getContents();
            for (int i = 0; i < contents.length; i++) {
                ItemStack item = contents[i];
                if (CustomItems.isPapaCaliente(item)) {
                    contents[i] = null;
                }
            }
            player.getInventory().setContents(contents);
        }

        Player winner = players.stream().findFirst().orElse(null);

        if (winner == null) return;

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 3F, 1F);
            Title title1 = Title.title(
                    Component.text("🔥 ¡Final épico! 🔥").color(NamedTextColor.GOLD),
                    Component.text("La papa ha explotado...").color(NamedTextColor.RED),
                    Title.Times.times(Duration.ofMillis(500), Duration.ofSeconds(1), Duration.ofMillis(500))
            );
            player.showTitle(title1);
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 3F, 1F);
                player.showTitle(Title.title(
                        Component.text("🏆 Ganador: ").color(NamedTextColor.YELLOW)
                                .append(Component.text(winner.getName(), NamedTextColor.GOLD)),
                        Component.text("Gracias por jugar").color(NamedTextColor.GRAY),
                        Title.Times.times(Duration.ofMillis(300), Duration.ofSeconds(2), Duration.ofMillis(300))
                ));
            }
        }, 40L);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Title title3 = Title.title(
                        Component.text("¡Gracias por jugar!").color(NamedTextColor.AQUA),
                        Component.text("¿Estás listo para la próxima ronda?").color(NamedTextColor.GRAY),
                        Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1))
                );
                player.showTitle(title3);
            }
        }, 100L);


        players.clear();
        currentHolder = null;
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
    }

    private void givePotato(Player player) {
        ItemStack papa = CustomItems.PapaCaliente(player);
        player.getInventory().setItem(0, papa);
        Bukkit.getScheduler().runTaskLater(plugin, player::updateInventory, 1L);
    }

    private void removePotato(Player player) {
        ItemStack item = player.getInventory().getItem(0);
        if (CustomItems.isPapaCaliente(item)) {
            player.getInventory().setItem(4, null);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10F, 1F);
            player.sendMessage(Component.text("¡Has pasado la papa caliente!").color(NamedTextColor.GREEN));
        }
    }
}
