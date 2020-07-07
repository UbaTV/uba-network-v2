package xyz.ubatv.hub.hotbar.store;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;

public class StoreGUI implements InventoryHolder, Listener {

    private Main main = Main.getInstance();

    public final Inventory store;
    private Player player;

    public StoreGUI(Player player){
        this.player = player;
        this.store = Bukkit.createInventory(player, 9*6, "§5§lStore");
    }

    public void createGUI(){
        ItemStack pve = main.itemAPI.item(Material.DIAMOND_SWORD, "§5§lPvE", "§7Click open pve store.");
        ItemStack profile = main.itemAPI.skull(player, "§5§lCoins", "§5Network §7Coins§8: §5" + main.playerBankManager.getServerCoins(player.getUniqueId()), "§5PvE §7Coins§8: §5" + main.playerBankManager.getPvECoins(player.getUniqueId()));

        store.setItem(22, pve);
        store.setItem(49, profile);
    }

    public void openInventory(Player player){
        createGUI();
        player.openInventory(store);
    }

    @Override
    public Inventory getInventory() {
        return store;
    }
}
