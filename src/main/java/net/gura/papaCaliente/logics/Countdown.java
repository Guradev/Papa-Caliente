package net.gura.papaCaliente.logics;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class Countdown {

    private final Plugin plugin;
    private int secondsLeft;
    private final int totalSeconds;
    private final Consumer<Integer> onTick;
    private final Runnable onFinish;

    private BukkitTask task;
    private boolean isPaused = false;

    public Countdown(Plugin plugin, int totalSeconds, Consumer<Integer> onTick, Runnable onFinish) {
        this.plugin = plugin;
        this.totalSeconds = totalSeconds;
        this.secondsLeft = totalSeconds;
        this.onTick = onTick;
        this.onFinish = onFinish;
    }


    public void start() {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
        this.task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (isPaused) return;

            if (secondsLeft <= 0) {
                cancel();
                onFinish.run();
                return;
            }
            onTick.accept(secondsLeft);
            secondsLeft--;
        },0,20L);
    }

    public void cancel() {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    public void reset() {
        cancel();
        this.secondsLeft = totalSeconds;
        start();
    }

    public void setTime(int seconds) {
        this.secondsLeft = seconds;
    }

    public int getTimeLeft() {
        return secondsLeft;
    }

    public boolean isRunning() {
        return task != null && !task.isCancelled();
    }
    public boolean isPaused() {
        return isPaused;
    }
}