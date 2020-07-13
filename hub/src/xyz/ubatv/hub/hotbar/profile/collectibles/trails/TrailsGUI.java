package xyz.ubatv.hub.hotbar.profile.collectibles.trails;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;

import java.util.UUID;

public class TrailsGUI implements InventoryHolder {

    private Main main = Main.getInstance();

    public final Inventory trails;
    private Player player;

    public TrailsGUI(Player player){
        this.player = player;
        this.trails = Bukkit.createInventory(player, 9*6, "§5§lCollectibles §8| §5Trails");
    }

    public void createGUI(){
        UUID uuid = player.getUniqueId();
        ItemStack flames = main.itemAPI.item(Material.BLAZE_POWDER, "§5Flames", "§7Owned: " + (main.collectiblesTable.getTrailsFlame(uuid) ? "§aYes" : "§cNo"), main.collectiblesTable.getTrail(uuid) == 1 ? "§5§lSELECTED" : "");
        ItemStack hearths = main.itemAPI.item(Material.RED_DYE, "§5Hearths", "§7Owned: " + (main.collectiblesTable.getTrailsHearths(uuid) ? "§aYes" : "§cNo"), main.collectiblesTable.getTrail(uuid) == 2 ? "§5§lSELECTED" : "");
        ItemStack magic = main.itemAPI.item(Material.CAULDRON, "§5Magic", "§7Owned: " + (main.collectiblesTable.getTrailsMagic(uuid) ? "§aYes" : "§cNo"), main.collectiblesTable.getTrail(uuid) == 3 ? "§5§lSELECTED" : "");
        ItemStack smoke = main.itemAPI.item(Material.SMOKER, "§5Smoke", "§7Owned: " + (main.collectiblesTable.getTrailsSmoke(uuid) ? "§aYes" : "§cNo"), main.collectiblesTable.getTrail(uuid) == 4 ? "§5§lSELECTED" : "");
        ItemStack angry = main.itemAPI.item(Material.SPIDER_EYE, "§5Angry", "§7Owned: " + (main.collectiblesTable.getTrailsAngry(uuid) ? "§aYes" : "§cNo"), main.collectiblesTable.getTrail(uuid) == 5 ? "§5§lSELECTED" : "");
        ItemStack info = main.itemAPI.skull(player, "§5Your Info", "§7Rank§8: §5" + main.playerDataManager.getRank(player.getUniqueId()), "§5Server §7Coins§8: §5" + main.playerBankManager.getServerCoins(player.getUniqueId()));
        ItemStack back = main.itemAPI.item(Material.ARROW, "§cBack", "§7Click to return to §5Profile");

        trails.setItem(20, flames);
        trails.setItem(21, hearths);
        trails.setItem(22, magic);
        trails.setItem(23, smoke);
        trails.setItem(24, angry);
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
