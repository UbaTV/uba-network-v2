package xyz.ubatv.pve.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class TextUtils {

    public String serverName = "§5§lUba§7Network";
    public String website = "ubatv.xyz";
    public String serverIp = "play.ubatv.xyz";

    public String right = "§a✓ §7";
    public String warning = "§e◬ §7";
    public String error = "§c◉ §7";

    public char coinsSymbol = '⛃';

    public String playerOnly = "Only players can execute this command";
    public String noPerms = error + "You don't have permission to execute this command.";

    public static void sendActionBarMessage(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }
}
