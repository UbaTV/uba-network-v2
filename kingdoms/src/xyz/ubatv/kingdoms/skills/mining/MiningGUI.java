package xyz.ubatv.kingdoms.skills.mining;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.kingdoms.Main;

public class MiningGUI implements InventoryHolder {

    private Main main = Main.getInstance();

    public final Inventory mining;
    private Player player;

    public MiningGUI(Player player){
        this.player = player;
        this.mining = Bukkit.createInventory(player, 9*6, "§8Mining");
    }

    public void createGUI(){
        ItemStack inDev = main.itemAPI.item(Material.BEDROCK, "§aSkill in development", "§7Come back later.");
        ItemStack back = main.itemAPI.item(Material.ARROW, "§aBack", "§7To the Skills Menu");
        ItemStack close = main.itemAPI.item(Material.BARRIER, "§cClose", "");

        mining.setItem(22, inDev);
        mining.setItem(48, back);
        mining.setItem(49, close);
    }

    public void openInventory(Player player){
        createGUI();
        player.openInventory(mining);
    }

    @Override
    public Inventory getInventory() {
        return mining;
    }
}
