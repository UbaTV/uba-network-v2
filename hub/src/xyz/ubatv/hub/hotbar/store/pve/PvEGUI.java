package xyz.ubatv.hub.hotbar.store.pve;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;

public class PvEGUI implements InventoryHolder, Listener {

    private int selfRevivePrice = 100;
    private int teamRevivePrice = 500;

    private Main main = Main.getInstance();

    public final Inventory pveStore;
    private Player player;

    public PvEGUI(Player player){
        this.player = player;
        this.pveStore = Bukkit.createInventory(player, 9*6, "§5§lPvE §7Store");
    }

    public void createGUI(){
        ItemStack selfRevive = main.itemAPI.item(Material.MILK_BUCKET, "§5Self Revive", "§7Price: §5" + selfRevivePrice + " §5PvE §7Coins", "§7Click to §nbuy");
        ItemStack teamRevive = main.itemAPI.item(Material.TOTEM_OF_UNDYING, "§5Team Revive", "§7Price: §5" + teamRevivePrice + " §5PvE §7Coins", "§7Click to §nbuy");
        ItemStack profile = main.itemAPI.skull(player, "§5§lCoins", "§5Network §7Coins§8: §5" + main.playerBankManager.getServerCoins(player.getUniqueId()), "§5PvE §7Coins§8: §5" + main.playerBankManager.getPvECoins(player.getUniqueId()));

        pveStore.setItem(20, selfRevive);
        pveStore.setItem(24, teamRevive);
        pveStore.setItem(49, profile);
    }

    public void openInventory(Player player){
        createGUI();
        player.openInventory(pveStore);
    }

    @Override
    public Inventory getInventory() {
        return pveStore;
    }
}
