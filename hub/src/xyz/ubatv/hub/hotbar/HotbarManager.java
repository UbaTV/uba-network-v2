package xyz.ubatv.hub.hotbar;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;

public class HotbarManager implements Listener {

    private Main main = Main.getInstance();

    private SelectorGUI selectorGUI = new SelectorGUI();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        player.getInventory().clear();

        ItemStack gameSelector = main.itemAPI.item(Material.COMPASS, "§5§lGame §7Selector", "§7Right-click to open this menu.");
        player.getInventory().setItem(4, gameSelector);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if(event.getItem() != null && event.getItem().getType() == Material.COMPASS){
            Player player = event.getPlayer();
            selectorGUI.openInventory(player);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        ItemStack item = event.getItemDrop().getItemStack();
        if(item.getType() == Material.COMPASS) event.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        player.getInventory().clear();
    }
}
