package xyz.ubatv.hub.events;

import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class MobSpawn implements Listener {

    @EventHandler
    public void onSpawn(EntitySpawnEvent event){
        if(event.getEntity() instanceof Monster){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAttack(EntityDamageEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event){
        event.getDrops().clear();
    }
}
