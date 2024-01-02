package pl.tenfajnybartek.spawnplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.tenfajnybartek.spawnplugin.config.Config;
import pl.tenfajnybartek.spawnplugin.managers.TeleportManager;
import pl.tenfajnybartek.spawnplugin.utils.Utils;

public class SpawnCommand implements CommandExecutor {

    private final Config config;

    public SpawnCommand(Config config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda tylko dla graczy!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(config.getSpawnPermission())) {
            player.sendMessage(Utils.colorize(config.getNoPermissionMessage()));
            return true;
        }

        if (TeleportManager.isPlayerInTeleportQueue(player)) {
            player.sendMessage(Utils.colorize(config.getAlreadyTeleporting()));
            return true;
        }

        int teleportDelay = config.getTeleportDelaySeconds();
        TeleportManager.executeTeleport(player, config, teleportDelay);

        return true;
    }
}