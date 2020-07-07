package xyz.ubatv.pve.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceBreakEvent implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        if(event.getBlock().getLocation().getBlockY() >= 120){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if(event.getBlock().getLocation().getBlockY() >= 120){
            event.setCancelled(true);
        }
    }
}
