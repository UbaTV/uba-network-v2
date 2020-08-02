package xyz.ubatv.kingdoms.skills.combat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.kingdoms.Main;

public class CombatGUI implements InventoryHolder {

    private Main main = Main.getInstance();

    public final Inventory combat;
    private Player player;

    public CombatGUI(Player player){
        this.player = player;
        this.combat = Bukkit.createInventory(player, 9*6, "§8Combat");
    }

    public void createGUI(){
        ItemStack inDev = main.itemAPI.item(Material.BEDROCK, "§aSkill in development", "§7Come back later.");
        ItemStack back = main.itemAPI.item(Material.ARROW, "§aBack", "§7To the Skills Menu");
        ItemStack close = main.itemAPI.item(Material.BARRIER, "§cClose", "");

        combat.setItem(22, inDev);
        combat.setItem(48, back);
        combat.setItem(49, close);
    }

    public void openInventory(Player player){
        createGUI();
        player.openInventory(combat);
    }

    @Override
    public Inventory getInventory() {
        return combat;
    }
}
