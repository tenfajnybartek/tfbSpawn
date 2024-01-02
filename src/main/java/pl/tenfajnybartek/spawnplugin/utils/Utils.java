package pl.tenfajnybartek.spawnplugin.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendViewOptionMessage(Player player, String viewOption, String message) {
        viewOption = viewOption.toUpperCase();
        switch (viewOption) {
            case "ACTIONBAR":
                sendActionBarMessage(player, message);
                break;
            case "TITLE":
                sendTitleMessage(player, message);
                break;
            case "CHAT":
            default:
                player.sendMessage(colorize(message));
                break;
        }
    }

    public static void sendTitleMessage(Player player, String message) {
        player.sendTitle(
                "",
                ChatColor.translateAlternateColorCodes('&', message),
                10, 40, 10 // fadeIn, stay, fadeOut
        );
    }

    public static void sendActionBarMessage(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

}