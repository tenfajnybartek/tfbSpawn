package pl.tenfajnybartek.spawnplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.tenfajnybartek.spawnplugin.config.Config;
import pl.tenfajnybartek.spawnplugin.utils.Utils;

public class SetSpawnCommand implements CommandExecutor {

    private final Config config;

    public SetSpawnCommand(Config config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda tylko dla graczy!");
            return true;
        }

        Player player = (Player) sender;

        // Sprawdź uprawnienia gracza
        if (!player.hasPermission(config.getSetSpawnPermission())) {
            player.sendMessage(Utils.colorize(config.getNoPermissionMessage()));
            return true;
        }

        config.setSpawnLocation(player.getLocation());
        player.sendMessage(Utils.colorize(config.getSetSpawnMessage()));
        return true;
    }
}
