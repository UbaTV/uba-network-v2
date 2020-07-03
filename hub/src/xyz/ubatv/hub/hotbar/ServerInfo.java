package xyz.ubatv.hub.hotbar;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;

public class ServerInfo {

    private Main main = Main.getInstance();

    public int hotbarSlot = 1;

    public ItemStack hotbarItem(){
        return main.itemAPI.item(Material.BOOK, "§5§lServer §7Info", "§7Right-click to show the server info.");
    }

    public void sendServerInfo(Player player){
        player.sendMessage(" ");
        main.textUtils.sendCenteredMessage(player, main.textUtils.serverName);
        player.sendMessage(" ");
        player.sendMessage("   §7Website: §5https://ubatv.xyz");
        player.sendMessage("   §7Discord: §5https://discord.gg/AJxFu2C");
        player.sendMessage("   §7Twitter: §5https://twitter.com/ubatvoficial");
        player.sendMessage(" ");
    }
}
