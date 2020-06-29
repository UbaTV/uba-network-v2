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
        if(rank.equals(Ranks.DEFAULT)){
            event.setFormat("§7" + player.getName() + "§8§l: §7" + event.getMessage());
        }else{
            if(player.getName().equalsIgnoreCase("andreubita")){
                event.setFormat(main.rankManager.rankName(rank) + " §7§n" + player.getName() + "§8§l: §7" + event.getMessage());
            }else{
                event.setFormat(main.rankManager.rankName(rank) + " §7" + player.getName() + "§8§l: §7" + event.getMessage());
            }
        }
    }
}
