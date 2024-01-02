package pl.tenfajnybartek.spawnplugin.config;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.tenfajnybartek.spawnplugin.base.SpawnPlugin;
import pl.tenfajnybartek.spawnplugin.utils.Utils;

import java.io.File;
import java.io.IOException;

public class Config {

    private final SpawnPlugin plugin;
    private File configFile;
    private FileConfiguration config;

    public Config(SpawnPlugin plugin) {
        this.plugin = plugin;
        configFile = new File(plugin.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void setSpawnLocation(Location location) {
        config.set("spawn.x", location.getX());
        config.set("spawn.y", location.getY());
        config.set("spawn.z", location.getZ());
        config.set("spawn.world", location.getWorld().getName());
        saveConfig();
    }

    public Location getSpawnLocation() {
        double x = config.getDouble("spawn.x");
        double y = config.getDouble("spawn.y");
        double z = config.getDouble("spawn.z");
        String worldName = config.getString("spawn.world");

        return new Location(plugin.getServer().getWorld(worldName), x, y, z);
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
        plugin.getLogger().info("Config.yml został ponownie wczytany.");
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMessage(String path) {
        return config.getString(path, "");
    }

    public String getReloadMessage() {
        return Utils.colorize(getMessage("messages.reloadmessage"));
    }

    public String getTeleportCancelMessage() {
        return Utils.colorize(getMessage("messages.teleportcancel"));
    }

    public String getFirstJoinTeleportMessage() {
        return Utils.colorize(getMessage("messages.firstjointeleport"));
    }

    public String getAlreadyTeleporting() {
        return Utils.colorize(getMessage("messages.alreadyteleporting"));
    }
    public String getReloadPermission() {
        return config.getString("permissions.reload");
    }
    public String getViewOption() {
        return config.getString("viewOption", "CHAT").toUpperCase();
    }
    public String getSetSpawnMessage() {
        return Utils.colorize(getMessage("messages.setspawn"));
    }

    public String getSpawnMessage() {
        return Utils.colorize(getMessage("messages.spawn"));
    }

    public String getTeleportingMessage() {
        return Utils.colorize(getMessage("messages.teleporting"));
    }

    public String getNoPermissionMessage() {
        return Utils.colorize(getMessage("messages.nopermission"));
    }

    public String getSetSpawnPermission() {
        return config.getString("permissions.setspawn");
    }

    public String getSpawnPermission() {
        return config.getString("permissions.spawn");
    }

    public int getTeleportDelaySeconds() {
        return config.getInt("teleportdelay");
    }
}