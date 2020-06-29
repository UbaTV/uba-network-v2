package xyz.ubatv.pve.events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamage implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getEntityType().equals(EntityType.PLAYER)
        && event.getDamager().getType().equals(EntityType.PLAYER)){
            event.setCancelled(true);
        }
    }
}
