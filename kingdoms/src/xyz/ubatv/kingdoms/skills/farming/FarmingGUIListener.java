package xyz.ubatv.kingdoms.skills.farming;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.kingdoms.skills.SkillsGUI;

public class FarmingGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§8Farming")) return;
        event.setCancelled(true);
        if(event.getClick().equals(ClickType.NUMBER_KEY)) return;

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        if(slot == 48){
            SkillsGUI skillsGUI = new SkillsGUI(player);
            skillsGUI.openInventory(player);
        }

        if(slot == 49){
            player.closeInventory();
        }
    }
}
