package net.gura.papaCaliente.logics;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class Countdown {

    private final Plugin plugin;
    private final int totalSeconds;
    private int secondsLeft;
    private final Consumer<Integer> onTick;
    private final Runnable onFinish;

    private BukkitTask task;

    public Countdown(Plugin plugin, int totalSeconds, Consumer<Integer> onTick, Runnable onFinish) {
        this.plugin = plugin;
        this.totalSeconds = totalSeconds;
        this.secondsLeft = totalSeconds;
        this.onTick = onTick;
        this.onFinish = onFinish;
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }


    public void start() {
        this.task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (secondsLeft <= 0) {
                task.cancel();
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
}