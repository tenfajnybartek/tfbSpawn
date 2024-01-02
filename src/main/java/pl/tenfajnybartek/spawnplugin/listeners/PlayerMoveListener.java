package pl.tenfajnybartek.spawnplugin.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.tenfajnybartek.spawnplugin.base.SpawnPlugin;
import pl.tenfajnybartek.spawnplugin.config.Config;
import pl.tenfajnybartek.spawnplugin.managers.TeleportManager;
import pl.tenfajnybartek.spawnplugin.utils.Utils;

public class PlayerMoveListener implements Listener {

    private final SpawnPlugin plugin;
    private final Config config;

    public PlayerMoveListener(SpawnPlugin plugin, Config config) {
        this.plugin = plugin;
        this.config = config;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (TeleportManager.isPlayerInTeleportQueue(player)) {
            if (hasPlayerMoved(event.getFrom(), event.getTo())) {
                TeleportManager.cancelTeleport(player);
                String cancelMessage = config.getTeleportCancelMessage();
                Utils.sendViewOptionMessage(player, config.getViewOption(), cancelMessage);
            }
        }
    }

    private boolean hasPlayerMoved(Location from, Location to) {
        double threshold = 0.001; // wartość progowa, możesz dostosować

        return Math.abs(from.getX() - to.getX()) > threshold
                || Math.abs(from.getY() - to.getY()) > threshold
                || Math.abs(from.getZ() - to.getZ()) > threshold;
    }
}
