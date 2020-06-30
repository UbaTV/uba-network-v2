package xyz.ubatv.pve.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import xyz.ubatv.pve.Main;

public class MobSpawning implements Listener {

    private Main main = Main.getInstance();

    // TODO Mob spawning

    @EventHandler
    public void onMobKill(EntityDeathEvent event){
        if(event.getEntity() instanceof Monster){
            Monster entity = (Monster) event.getEntity();
            if(entity.getKiller() != null){
                if(main.gameManager.gameStatus.equals(GameStatus.ROUND_NIGHT)){
                    main.gameManager.mobsToKill--;
                    Bukkit.broadcastMessage(main.gameManager.mobsToKill + "");
                }
            }
        }
    }

    @EventHandler
    public void onMobSpawn(EntitySpawnEvent event){
        if(!main.gameManager.gameStatus.equals(GameStatus.ROUND_NIGHT)){
            if(event.getEntity() instanceof Monster){
                event.setCancelled(true);
            }
        }
    }

    public static int mobsAtRound(int round){
        return round ^ 2 * 50;
    }
}
