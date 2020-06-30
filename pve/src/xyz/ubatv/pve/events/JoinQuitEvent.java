package xyz.ubatv.pve.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
        player.getInventory().clear();
        player.setHealth(20);
        player.setFoodLevel(20);

        if(main.gameManager.gameStatus.equals(GameStatus.WAITING) || main.gameManager.gameStatus.equals(GameStatus.STARTING)){
            main.gameManager.waiting.add(uuid);
            player.setGameMode(GameMode.ADVENTURE);
            player.teleport(main.gameManager.lobby);
        }else{
            main.playerHandler.spectateGame(uuid);
            player.teleport(main.gameManager.game);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        event.setQuitMessage("§8[§c§l-§8] §5" + player.getName());

        main.gameManager.alive.remove(uuid);
        main.gameManager.waiting.remove(uuid);
        main.gameManager.dead.remove(uuid);
        main.gameManager.spectating.remove(uuid);
    }
}