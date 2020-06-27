package xyz.ubatv.pve.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.ubatv.pve.Main;
import xyz.ubatv.pve.game.GameStatus;

import java.util.UUID;

public class JoinQuitEvent implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        event.setJoinMessage("§8[§a§l+§8] §5" + player.getName());
        if(main.gameManager.gameStatus.equals(GameStatus.WAITING)){
            main.playerHandler.joinGame(uuid);
        }else{
            main.playerHandler.spectateGame(uuid);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        event.setQuitMessage("§8[§c§l-§8] §5" + player.getName());
        main.playerHandler.removeFromLists(uuid);
    }
}
