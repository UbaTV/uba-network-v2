package xyz.ubatv.hub.hotbar.profile;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;

public class ProfileGUI implements InventoryHolder, Listener {

    private Main main = Main.getInstance();

    public final Inventory profile;
    private Player player;

    public ProfileGUI(Player player){
        this.player = player;
        this.profile = Bukkit.createInventory(player, 9*6, "§5§lProfile");
    }

    public void createGUI(){
        ItemStack stats = main.itemAPI.item(Material.BOOK, "§5§lStats", "§cIN DEVELOPMENT");
        ItemStack collectibles = main.itemAPI.item(Material.CHEST, "§5§lCollectibles", "§7Click to see your collectibles.");
        ItemStack settings = main.itemAPI.item(Material.REPEATER, "§5§lSettings", "§cIN DEVELOPMENT");
        ItemStack info = main.itemAPI.skull(player, "§5Your Info", "§7Rank§8: §5" + main.playerDataManager.getRank(player.getUniqueId()), "§5Server §7Coins§8: §5" + main.playerBankManager.getServerCoins(player.getUniqueId()));

        profile.setItem(13, stats);
        profile.setItem(29, collectibles);
        profile.setItem(33, settings);
        profile.setItem(49, info);
    }

    public void openInventory(Player player){
        createGUI();
        player.openInventory(profile);
    }

    @Override
    public Inventory getInventory() {
        return profile;
    }
}
