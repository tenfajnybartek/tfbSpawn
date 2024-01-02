package pl.tenfajnybartek.spawnplugin.base;

import org.bukkit.plugin.java.JavaPlugin;
import pl.tenfajnybartek.spawnplugin.commands.SetSpawnCommand;
import pl.tenfajnybartek.spawnplugin.commands.SpawnCommand;
import pl.tenfajnybartek.spawnplugin.commands.SpawnReloadCommand;
import pl.tenfajnybartek.spawnplugin.config.Config;
import pl.tenfajnybartek.spawnplugin.listeners.OnFirstJoinListener;
import pl.tenfajnybartek.spawnplugin.listeners.PlayerMoveListener;
import pl.tenfajnybartek.spawnplugin.managers.TeleportManager;

public final class SpawnPlugin extends JavaPlugin {

    private Config config;
    private SetSpawnCommand setSpawnCommand;
    private SpawnCommand spawnCommand;
    private SpawnReloadCommand spawnReloadCommand;

    private static SpawnPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        config = new Config(this);
        config.reloadConfig();
        setSpawnCommand = new SetSpawnCommand(config);
        spawnCommand = new SpawnCommand(config);
        spawnReloadCommand = new SpawnReloadCommand(config);
        registerListeners();
        registerCommands();
        registerManagers();
    }

    @Override
    public void onDisable() {
        config.saveConfig();
    }

    public void registerCommands() {
        getCommand("setspawn").setExecutor(setSpawnCommand);
        getCommand("spawn").setExecutor(spawnCommand);
        getCommand("spawnreload").setExecutor(spawnReloadCommand);
    }
    public void registerListeners() {
        new PlayerMoveListener(this, config);
        new OnFirstJoinListener(this, config);
    }
    public void registerManagers() {
        new TeleportManager();
    }
    public static SpawnPlugin getInstance() {
        return instance;
    }
}