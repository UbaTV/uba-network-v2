package xyz.ubatv.hub.hotbar;

import org.bukkit.entity.Player;

public class ServerInfo {

    public static void sendServerInfo(Player player){
        player.sendMessage(" ");
        player.sendMessage("   Website: https://ubatv.xyz");
        player.sendMessage("   Discord: https://discord.gg/AJxFu2C");
        player.sendMessage("   Twitter: https://twitter.com/ubatvoficial");
        player.sendMessage(" ");
    }
}
