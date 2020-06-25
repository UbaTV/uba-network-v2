package xyz.ubatv.hub.rankSystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.ubatv.hub.Main;

import java.util.UUID;

public class ChatFormatter implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Ranks rank = main.playerDataManager.getRank(uuid);
        event.setFormat(main.rankManager.rankName(rank) + " §5" + player.getName() + "§8§l: §7" + event.getMessage());
    }
}
