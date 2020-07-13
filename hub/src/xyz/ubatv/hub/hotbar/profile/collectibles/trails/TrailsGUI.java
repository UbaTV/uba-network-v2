package xyz.ubatv.hub.hotbar.profile.collectibles.trails;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;

public class TrailsGUI implements InventoryHolder {

    private Main main = Main.getInstance();

    public final Inventory trails;
    private Player player;

    public TrailsGUI(Player player){
        this.player = player;
        this.trails = Bukkit.createInventory(player, 9*6, "§5§lCollectibles §8| §5Trails");
    }

    public void createGUI(){
        ItemStack flames = main.itemAPI.item(Material.ARROW, "§5Flames", "§7Owned: ");
        ItemStack info = main.itemAPI.skull(player, "§5Your Info", "§7Rank§8: §5" + main.playerDataManager.getRank(player.getUniqueId()), "§5Server §7Coins§8: §5" + main.playerBankManager.getServerCoins(player.getUniqueId()));
        ItemStack back = main.itemAPI.item(Material.ARROW, "§cBack", "§7Click to return to §5Profile");

        trails.setItem(49, info);
        trails.setItem(50, back);
    }

    public void openInventory(Player player){
        createGUI();
        player.openInventory(trails);
    }

    @Override
    public Inventory getInventory() {
        return trails;
    }
}
