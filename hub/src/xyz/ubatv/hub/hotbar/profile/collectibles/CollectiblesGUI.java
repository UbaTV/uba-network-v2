package xyz.ubatv.hub.hotbar.profile.collectibles;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;

public class CollectiblesGUI implements InventoryHolder {

    private Main main = Main.getInstance();

    public final Inventory collectibles;
    private Player player;

    public CollectiblesGUI(Player player){
        this.player = player;
        this.collectibles = Bukkit.createInventory(player, 9*6, "§5§lCollectibles");
    }

    public void createGUI(){
        ItemStack hats = main.itemAPI.item(Material.GOLDEN_HELMET, "§5Hats", "§7Click to open §5Hats");
        ItemStack trails = main.itemAPI.item(Material.NETHER_STAR, "§5Trails", "§7Click to open §5Trails");
        ItemStack wardrobe = main.itemAPI.item(Material.DIAMOND_LEGGINGS, "§5Wardrobe", "§cIN DEVELOPMENT");
        ItemStack cages = main.itemAPI.item(Material.PURPLE_STAINED_GLASS, "§5Cages", "§cIN DEVELOPMENT");
        ItemStack chat = main.itemAPI.item(Material.PAPER, "§5Chat", "§cIN DEVELOPMENT");
        ItemStack pets = main.itemAPI.item(Material.BONE, "§5Pets", "§cIN DEVELOPMENT");
        ItemStack info = main.itemAPI.skull(player, "§5Your Info", "§7Rank§8: §5" + main.playerDataManager.getRank(player.getUniqueId()), "§5Server §7Coins§8: §5" + main.playerBankManager.getServerCoins(player.getUniqueId()));
        ItemStack back = main.itemAPI.item(Material.ARROW, "§cBack", "§7Click to return to §5Profile");

        collectibles.setItem(12, hats);
        collectibles.setItem(14, trails);
        collectibles.setItem(20, wardrobe);
        collectibles.setItem(24, chat);
        collectibles.setItem(30, cages);
        collectibles.setItem(32, pets);
        collectibles.setItem(49, info);
        collectibles.setItem(50, back);
    }

    public void openInventory(Player player){
        createGUI();
        player.openInventory(collectibles);
    }

    @Override
    public Inventory getInventory() {
        return collectibles;
    }
}
