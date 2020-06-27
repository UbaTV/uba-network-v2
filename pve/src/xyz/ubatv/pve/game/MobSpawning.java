package xyz.ubatv.pve.game;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import xyz.ubatv.pve.Main;

public class MobSpawning implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onMobKill(EntityDeathEvent event){
        if(event.getEntity() instanceof Monster){
            Monster entity = (Monster) event.getEntity();
            if(entity.getKiller() != null){
                Player player = entity.getKiller();
                if(main.gameManager.gameStatus.equals(GameStatus.ROUND_NIGHT)){
                    main.gameManager.mobsToKill--;
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
        return 10; // For testing
        //return round ^ 2 * 50;
    }
}
