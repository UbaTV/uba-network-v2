package xyz.ubatv.kingdoms.skills.farming;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.kingdoms.Main;

public class FarmingGUI implements InventoryHolder {

    private Main main = Main.getInstance();

    public final Inventory farming;
    private Player player;

    public FarmingGUI(Player player){
        this.player = player;
        this.farming = Bukkit.createInventory(player, 9*6, "§8Farming");
    }

    public void createGUI(){
        ItemStack inDev = main.itemAPI.item(Material.BEDROCK, "§aSkill in development", "§7Come back later.");
        ItemStack back = main.itemAPI.item(Material.ARROW, "§aBack", "§7To the Skills Menu");
        ItemStack close = main.itemAPI.item(Material.BARRIER, "§cClose", "");

        farming.setItem(22, inDev);
        farming.setItem(48, back);
        farming.setItem(49, close);
    }

    public void openInventory(Player player){
        createGUI();
        player.openInventory(farming);
    }

    @Override
    public Inventory getInventory() {
        return farming;
    }

}
