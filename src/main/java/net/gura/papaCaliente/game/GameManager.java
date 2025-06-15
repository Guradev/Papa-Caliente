package net.gura.papaCaliente.game;

import org.bukkit.entity.Player;

import java.util.*;

public class GameManager {
    private GameState gameState = GameState.WAITING;
    private final Set<Player> players = new HashSet<>();
    private Player currentHolder = null;

    public GameState getGameState() {
        return gameState;
    }

    public boolean isRunning() {
        return gameState == GameState.RUNNING;
    }

    public void addPlayer() {

    }

    public void removePlayer() {

    }

    public void startGame() {
        if (players.size() < 2) {
            return;
        }

        gameState = GameState.RUNNING;

        //Elegir a una persona random de la lista para darle la papa caliente
        List<Player> listaPlayers = new ArrayList<>(players);
        Collections.shuffle(listaPlayers);
        currentHolder = listaPlayers.getFirst();

        givePotato(currentHolder);

        //Falta implementar logica del juego en si
    }

    public void stopGame() {
        gameState = GameState.ENDED;
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

    public void passPotato(Player de, Player a) {
        if (!players.contains(a)) return;

        removePotato(de);
        givePotato(a);
        currentHolder = a;

        // Falta implementar la logica del countdown
    }

    private void givePotato(Player player) {
        // Logic to give the potato to the player, possible show particles or play sound
    }

    private void removePotato(Player player) {
        // Logic for removing the potato from the player
    }

}
