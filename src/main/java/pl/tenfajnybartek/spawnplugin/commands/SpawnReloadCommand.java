package pl.tenfajnybartek.spawnplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.tenfajnybartek.spawnplugin.config.Config;
import pl.tenfajnybartek.spawnplugin.utils.Utils;

public class SpawnReloadCommand implements CommandExecutor {

    private final Config config;

    public SpawnReloadCommand(Config config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(config.getReloadPermission())) {
            config.reloadConfig();
            sender.sendMessage(Utils.colorize(config.getReloadMessage()));
            return true;
        } else {
            sender.sendMessage(Utils.colorize(config.getNoPermissionMessage()));
            return false;
        }
    }
}
