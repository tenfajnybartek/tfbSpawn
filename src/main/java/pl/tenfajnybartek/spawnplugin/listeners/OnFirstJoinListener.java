package pl.tenfajnybartek.spawnplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.tenfajnybartek.spawnplugin.base.SpawnPlugin;
import pl.tenfajnybartek.spawnplugin.config.Config;
import pl.tenfajnybartek.spawnplugin.utils.Utils;

public class OnFirstJoinListener implements Listener {

    private final SpawnPlugin plugin;
    private final Config config;

    public OnFirstJoinListener(SpawnPlugin plugin, Config config) {
        this.plugin = plugin;
        this.config = config;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            teleportPlayerToSpawn(event.getPlayer());
        }
    }

    private void teleportPlayerToSpawn(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.teleport(config.getSpawnLocation());
                player.sendMessage(Utils.colorize(config.getFirstJoinTeleportMessage()));
            }
        }.runTaskLater(plugin, 20L);
    }
}
