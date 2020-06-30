package xyz.ubatv.hub.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceBreakBlock implements Listener {

    // TODO Build Mode

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        event.setCancelled(true);
    }
}
