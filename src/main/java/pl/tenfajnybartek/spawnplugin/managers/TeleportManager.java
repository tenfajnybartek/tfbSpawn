package pl.tenfajnybartek.spawnplugin.managers;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.tenfajnybartek.spawnplugin.base.SpawnPlugin;
import pl.tenfajnybartek.spawnplugin.config.Config;
import pl.tenfajnybartek.spawnplugin.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class TeleportManager {

    private static final Map<Player, BukkitRunnable> teleportQueue = new HashMap<>();

    public static void addToTeleportQueue(Player player, BukkitRunnable teleportTask) {
        teleportQueue.put(player, teleportTask);
    }

    public static boolean isPlayerInTeleportQueue(Player player) {
        return teleportQueue.containsKey(player);
    }

    public static void cancelTeleport(Player player) {
        BukkitRunnable teleportTask = teleportQueue.get(player);
        if (teleportTask != null) {
            teleportTask.cancel();
            teleportQueue.remove(player);
        }
    }

    public static void executeTeleport(Player player, Config config, int delaySeconds) {
        BukkitRunnable teleportTask = new BukkitRunnable() {
            int countdown = delaySeconds;

            @Override
            public void run() {
                if (countdown > 0) {
                    String message = Utils.colorize(config.getTeleportingMessage().replace("%time%", String.valueOf(countdown)));
                    Utils.sendViewOptionMessage(player, config.getViewOption(), message);
                    countdown--;
                } else {
                    player.teleport(config.getSpawnLocation());
                    String message = Utils.colorize(config.getSpawnMessage());
                    Utils.sendViewOptionMessage(player, config.getViewOption(), message);
                    cancelTeleport(player);
                    cancel();
                }
            }
        };

        addToTeleportQueue(player, teleportTask);
        teleportTask.runTaskTimer(SpawnPlugin.getInstance(), 0L, 20L);
    }
}