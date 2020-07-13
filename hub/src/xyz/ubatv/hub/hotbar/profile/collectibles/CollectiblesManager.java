package xyz.ubatv.hub.hotbar.profile.collectibles;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.hotbar.profile.ProfileGUI;
import xyz.ubatv.hub.hotbar.profile.collectibles.trails.TrailsGUI;

public class CollectiblesManager implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§5§lCollectibles")) return;
        event.setCancelled(true);
        if(event.getClick().equals(ClickType.NUMBER_KEY)) return;

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        if(slot == 12){
            // Open Hats
        }

        if(slot == 14){
            TrailsGUI trailsGUI = new TrailsGUI(player);
            trailsGUI.openInventory(player);
        }

        if(slot == 20){
            // Open Wardrobe
        }

        if(slot == 50){
            ProfileGUI profileGUI = new ProfileGUI(player);
            profileGUI.openInventory(player);
        }
    }
}
