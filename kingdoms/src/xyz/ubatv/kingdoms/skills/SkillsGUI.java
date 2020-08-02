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
        ItemStack mining = main.itemAPI.item(Material.STONE_PICKAXE, "§aMining", "§7Spend some time on the Mines", "§7to gain Mining XP");
        ItemStack combat = main.itemAPI.item(Material.STONE_SWORD, "§aCombat", "§7Fight monsters and players to", "§7gain Combat XP");
        ItemStack farming = main.itemAPI.item(Material.WHEAT_SEEDS, "§aFarming", "§7Harvest crops and kill", "§7animals to gain Farming XP");
        //ItemStack back = main.itemAPI.item(Material.ARROW, "§aBack", "§7To the Skills Menu");
        ItemStack close = main.itemAPI.item(Material.BARRIER, "§cClose", "");

        skills.setItem(21, mining);
        skills.setItem(22, combat);
        skills.setItem(23, farming);
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
