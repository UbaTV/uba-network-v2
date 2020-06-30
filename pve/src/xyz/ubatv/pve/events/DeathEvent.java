package xyz.ubatv.pve.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import xyz.ubatv.pve.Main;
import xyz.ubatv.pve.game.GameStatus;

import java.util.UUID;

public class DeathEvent implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        UUID uuid = player.getUniqueId();

        main.gameManager.alive.remove(uuid);
        main.gameManager.dead.add(uuid);
        event.setDeathMessage(null);
        event.setDeathMessage("§4☠ §7" + player.getName());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();

        player.setGameMode(GameMode.SPECTATOR);
        
        player.teleport(main.gameManager.game);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            if(!(main.gameManager.gameStatus.equals(GameStatus.ROUND_NIGHT)
              || main.gameManager.gameStatus.equals(GameStatus.ROUND_DAY)))
                event.setCancelled(true);
        }
    }
}
