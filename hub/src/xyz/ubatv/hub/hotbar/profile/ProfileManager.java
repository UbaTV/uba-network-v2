package xyz.ubatv.hub.hotbar.profile;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;
import xyz.ubatv.hub.hotbar.profile.collectibles.CollectiblesGUI;

public class ProfileManager implements Listener {

    private Main main = Main.getInstance();

    public int hotbarSlot = 4;

    public ItemStack hotbarItem(Player player){
        return main.itemAPI.skull(player, "§5§lProfile", "§7Right-click to open your profile");
    }

    public void openGUI(Player player){
        ProfileGUI profileGUI = new ProfileGUI(player);
        profileGUI.openInventory(player);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§5§lProfile")) return;
        event.setCancelled(true);
        if(event.getClick().equals(ClickType.NUMBER_KEY)) return;

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        if(slot == 29){
            CollectiblesGUI collectiblesGUI = new CollectiblesGUI(player);
            collectiblesGUI.openInventory(player);
        }
    }
}
