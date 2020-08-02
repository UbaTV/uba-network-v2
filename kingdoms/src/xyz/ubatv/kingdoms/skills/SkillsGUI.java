package xyz.ubatv.kingdoms.skills;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.kingdoms.Main;

public class SkillsGUI implements InventoryHolder {

    private Main main = Main.getInstance();

    public final Inventory skills;
    private Player player;

    public SkillsGUI(Player player){
        this.player = player;
        this.skills = Bukkit.createInventory(player, 9*6, "§8Your Skills");
    }

    public void createGUI(){
        ItemStack menuItem = main.itemAPI.item(Material.BOOK, "§5§lStats", "§cIN DEVELOPMENT");
        ItemStack collectibles = main.itemAPI.item(Material.CHEST, "§5§lCollectibles", "§7Click to see your collectibles.");
        //ItemStack back = main.itemAPI.item(Material.ARROW, "§aBack", "§7To the Skills Menu");
        ItemStack close = main.itemAPI.item(Material.BARRIER, "§cClose", "");

        skills.setItem(13, stats);
        skills.setItem(29, collectibles);
        skills.setItem(49, close);
    }

    public void openInventory(Player player){
        createGUI();
        player.openInventory(skills);
    }

    @Override
    public Inventory getInventory() {
        return skills;
    }
}
